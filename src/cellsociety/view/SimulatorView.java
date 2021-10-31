package cellsociety.view;

import cellsociety.controller.SimulatorController;
import cellsociety.error.GenerateError;
import cellsociety.games.Game;
import cellsociety.view.buttonFactory.SimulatorButtonFactory;
import cellsociety.view.cell.HexagonCell;
import cellsociety.view.cell.SquareCell;
import cellsociety.view.cell.TriangleCell;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Cell;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.*;

public class SimulatorView {

    private Timeline myAnimation;
    private double animationSpeed;
    private GridPane myGridView = new GridPane();
    private int myGridWidth;
    private int myGridHeight;
    private SimulatorController mySimulatorController;
    private String myCSSFile;
    private ResourceBundle myLanguageResources;
    private String INVALID_CSS_ERROR = "InvalidCSSFile";
    private Game myGame;
    private Scene myScene;
    private SimulatorButtonFactory mySimulatorButtonFactory;
    private HBox simulationBox = new HBox();
    private Stage myStage;
    private final String LANG_KEY = "language";
    private String INVALID_SAVE = "InvalidSaveFile";



    public SimulatorView(Game game, String cssFile, ResourceBundle resourceBundle, SimulatorController simulatorController){
        mySimulatorController = simulatorController;
        myGame = game;
        animationSpeed = 0.3;
        myAnimation = new Timeline();
        myGridWidth = myGame.getNumCols();
        myGridHeight = myGame.getNumRows();
        myCSSFile = cssFile;
        myLanguageResources = resourceBundle;
        mySimulatorButtonFactory = new SimulatorButtonFactory(this, mySimulatorController, myLanguageResources);
        setDefaultTriangleGrid(myGridWidth, myGridHeight, myGridView);
        initializeSimulationScene();
    }

    public void saveCSVFile(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Enter File Name");
        try{
            String fileName = dialog.showAndWait().get(); //TODO: check for ispresent
            myGame.saveCSVFile(fileName, myLanguageResources);
        }catch(Exception e){
            new GenerateError(myLanguageResources.getString(LANG_KEY), INVALID_SAVE);
        }
    }



    private void initializeSimulationScene(){
        myStage = new Stage();
        updateSimulation(myGame, myGridView);
        simulationBox.getChildren().add(generateSimulationVBox(myGridView));
        myScene = new Scene(simulationBox);
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
        if (myAnimation != null) {
            myAnimation.stop();
        }
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

    public void showAbout() throws FileNotFoundException {
        Scanner file = new Scanner(new File(mySimulatorController.getSimFilePath()));
        StringBuilder simInfo = new StringBuilder();
        while(file.hasNextLine()) simInfo.append(file.nextLine()).append("\n");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("About This Simulation");
        alert.setContentText(String.valueOf(simInfo));
        alert.showAndWait();
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
        for (int x = 0; x < game.getNumCols(); x++) {
            for (int y = 0; y < game.getNumRows(); y++) {
                int gridNumber = x * myGridHeight + y;
                int cellStatus = game.getCellStatus(x, y);
                updateCell(game, gamePane, gridNumber, cellStatus);
            }
        }
    }

    // updates cell status
    private void updateCell(Game game, GridPane gamePane, int cellNumber, int cellStatus){
        Node currNode = gamePane.getChildren().get(cellNumber);
        currNode.setId(cellStatus+"-cell");
    }

    /**
     * Returns a scene of the simulation with the control buttons
     * @return VBox containing gridpane of the simulation and control buttons
     */
    private VBox generateSimulationVBox(GridPane gameGrid){
        VBox simulationBox = new VBox();
        simulationBox.getChildren().addAll(gameGrid, mySimulatorButtonFactory.generateButtonPanel());
        applyCSS(simulationBox, myCSSFile);
        return simulationBox;
    }

    private void applyCSS(VBox scene, String cssFile) {
        try{
            File styleFile = new File(cssFile);
            scene.getStylesheets().add(styleFile.toURI().toURL().toString());
        }catch(Exception e){
            new GenerateError(myLanguageResources.getString(LANG_KEY), INVALID_CSS_ERROR);
        }
    }
}
