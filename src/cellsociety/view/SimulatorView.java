package cellsociety.view;

import cellsociety.controller.SimulatorController;
import cellsociety.error.GenerateError;
import cellsociety.games.Game;
import cellsociety.view.cell.Cell;
import cellsociety.view.factories.buttonFactory.SimulatorButtonFactory;
import cellsociety.view.cell.HexagonCell;
import cellsociety.view.cell.SquareCell;
import cellsociety.view.cell.TriangleCell;
import cellsociety.view.factories.sliderFactory.SliderFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.*;
import java.lang.invoke.SwitchPoint;
import java.util.*;

public class SimulatorView {

    private Timeline myAnimation;
    private double animationSpeed;
    private GridPane myGridView;
    private int myGridWidth;
    private int myGridHeight;
    private SimulatorController mySimulatorController;
    private String myCSSFile;
    private ResourceBundle myLanguageResources;
    private String INVALID_CSS_ERROR = "InvalidCSSFile";
    private String INVALID_SIM_FILE = "InvalidSimFile";
    private String AboutSimulationLabel = "AboutSimulationLabel";
    private Game myGame;
    private Scene myScene;
    private SimulatorButtonFactory mySimulatorButtonFactory;
    private HBox simulationBox = new HBox();
    private Stage myStage;
    private final String LANG_KEY = "language";
    private SliderFactory mySliderFactory;
    private double sliderValue = 1.0;
    private double sliderMin = 0.1;
    private double sliderMax = 5.0;
    private String simulatorBoxId = "simulator-root";
    private int cellType;



    public SimulatorView(Game game, String cssFile, ResourceBundle resourceBundle, SimulatorController simulatorController, int cellType){
        mySimulatorController = simulatorController;
        myGame = game;
        animationSpeed = 0.3;
        myAnimation = new Timeline();
        myGridWidth = myGame.getNumCols();
        myGridHeight = myGame.getNumRows();
        myCSSFile = cssFile;
        myLanguageResources = resourceBundle;
        mySimulatorButtonFactory = new SimulatorButtonFactory(this, mySimulatorController, myLanguageResources);
        mySliderFactory = new SliderFactory(sliderValue);
        myGridView = new GridPane();
        switch (cellType) {
            case 0 -> setDefaultGrid(myGridWidth, myGridHeight, myGridView);
            case 1 -> setDefaultTriangleGrid(myGridWidth, myGridHeight, myGridView);
            case 2 -> setDefaultHexagonGrid(myGridWidth, myGridHeight, myGridView);
        }

        initializeSimulationScene();
    }

    //TODO: may have to move update to simulator controller

    private void initializeSimulationScene(){
        myStage = new Stage();
        updateSimulation(myGame, myGridView);
        simulationBox.getChildren().add(generateSimulationVBox(myGridView));
        myScene = new Scene(simulationBox);
        applyCSS(myScene, myCSSFile);
        myStage.setScene(myScene);
        myStage.show();
        playAnimation();
    }

    public void stopSimulation(){
        myStage.close();
    }

    public void step(){
            myGame.update();
            updateSimulation(myGame, myGridView);
    }

    // Start new animation to show search algorithm's steps
    public void playAnimation () {
        assert myAnimation != null;
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(animationSpeed), e -> step()));
        myAnimation.play();
    }

    public void pause(){
        myAnimation.pause();
    }

    public void play(){
        myAnimation.play();
    }

    public void setAnimationSpeed(double speed){
        myAnimation.setRate(speed);
    }

    public void showSimulationInfo() {
        try{
            Scanner file = new Scanner(new File(mySimulatorController.getSimFilePath()));
            StringBuilder simInfo = new StringBuilder();
            while(file.hasNextLine()) simInfo.append(file.nextLine()).append("\n");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            setAlertContent(alert,myLanguageResources.getString(AboutSimulationLabel), String.valueOf(simInfo));
            alert.showAndWait();
        }catch(FileNotFoundException e){
            new GenerateError(myLanguageResources.getString(LANG_KEY), INVALID_SIM_FILE);
        }
    }

    private void setAlertContent(Alert alert, String text, String info){
        alert.setHeaderText(text);
        alert.setContentText(String.valueOf(info));
    }

    // fills the grid with squareCells of defaultColor
    private void setDefaultGrid(int gridWidth, int gridHeight, GridPane gamePane){
        for(int i = 0; i < gridWidth; i ++){
            for(int j = 0; j < gridHeight; j++){
                SquareCell cell = new SquareCell(mySimulatorController, i, j);
                cell.setId("default-cell");
                gamePane.add(cell, i, j);
            }
        }
    }
    // fills the grid with triangles of defaultColor
    private void setDefaultTriangleGrid(int gridWidth, int gridHeight, GridPane gamePane){
        int counter = 0;
        for(int i = 0; i < gridHeight; i ++){
            for(int j = 0; j < gridWidth; j++){
                TriangleCell cell = new TriangleCell(mySimulatorController, counter % 2, j, i);
                cell.setId("default-cell");
                gamePane.getChildren().add(cell);
                setCellLocation(cell,j*25,  i*43.30125);
                counter += 1;
            }
        }
        setPaneSize(gamePane, (gridWidth+1)*25, gridHeight*43.30125);
    }

    // fills the grid with hexagons of defaultColor
    private void setDefaultHexagonGrid(int gridWidth, int gridHeight, GridPane gamePane){
        int counter = 0;
        for(int i = 0; i < gridHeight; i ++){
            for(int j = 0; j < gridWidth; j++){
                HexagonCell cell = new HexagonCell(mySimulatorController, j, i);
                cell.setId("default-cell");
                gamePane.getChildren().add(cell);
                setCellLocation(cell, j*30.1, i*34.64 + 17.321*(counter%2));
                counter += 1;
            }
            counter = 0;
        }
        setPaneSize(gamePane,(gridWidth+1)*30,(gridHeight+1)*34.64);
    }

    private void setPaneSize(Pane gamePane, double xSize, double ySize){
        gamePane.setPrefWidth(xSize);
        gamePane.setPrefHeight(ySize);
    }

    private void setCellLocation(Polygon cell, double xLocation, double yLocation){
        cell.setTranslateX(xLocation);
        cell.setTranslateY(yLocation);
    }

    /**
     * updates the cells based on the values in Map
     * dead cells are colored with deadColor, alive cells are colored with aliveColor
     * @param game
     * @return scene with updated cell status
     */
    public void updateSimulation(Game game, GridPane gamePane){
        for(Node node : gamePane.getChildren()){
            Cell cell = (Cell) node;
            int cellStatus = game.getCellStatus(cell.getPoint());
            updateCell(gamePane, cell.getPoint(), cellStatus);
        }
    }

    // updates cell status
    private void updateCell(GridPane gamePane, Point point, int cellStatus){
        int cellNumber = (point.x) * myGridHeight + point.y;
        Node currNode = gamePane.getChildren().get(cellNumber);
        currNode.setId(cellStatus+"-cell");
    }

    /**
     * Returns a scene of the simulation with the control buttons
     * @return VBox containing gridpane of the simulation and control buttons
     */
    private VBox generateSimulationVBox(GridPane gameGrid){
        VBox simulationBox = new VBox();
        simulationBox.setId(simulatorBoxId);
        simulationBox.getChildren().addAll(gameGrid, mySimulatorButtonFactory.generateButtonPanel(), mySliderFactory.makeSlider(sliderMin, sliderMax,
                (obs, oldVal, newVal) -> setAnimationSpeed((double)newVal)));
        return simulationBox;
    }

    private void applyCSS(Scene scene, String cssFile) {
        try{
            File styleFile = new File(cssFile);
            scene.getStylesheets().add(styleFile.toURI().toURL().toString());
        }catch(Exception e){
            new GenerateError(myLanguageResources.getString(LANG_KEY), INVALID_CSS_ERROR);
        }
    }

    public void updateCSS(String cssFile){
        applyCSS(myScene, cssFile);
    }
}
