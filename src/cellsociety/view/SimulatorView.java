package cellsociety.view;

import cellsociety.controller.SimulatorController;
import cellsociety.error.GenerateError;
import cellsociety.games.Game;
import cellsociety.view.cell.Cell;
import cellsociety.view.factories.buttonFactory.SimulatorButtonFactory;
import cellsociety.view.cell.HexagonCell;
import cellsociety.view.cell.SquareCell;
import cellsociety.view.cell.TriangleCell;
import cellsociety.view.factories.cssFactory.CSSFactory;
import cellsociety.view.factories.sliderFactory.SliderFactory;
import cellsociety.view.gridBuilder.GridBuilder;
import cellsociety.view.gridBuilder.HexagonGridBuilder;
import cellsociety.view.gridBuilder.SquareGridBuilder;
import cellsociety.view.gridBuilder.TriangleGridBuilder;
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
import java.util.*;

public class SimulatorView {

    private Timeline myAnimation;
    private double animationSpeed = 0.3;
    private GridPane myGridView;
    private int myGridWidth;
    private int myGridHeight;
    private SimulatorController mySimulatorController;
    private String myCSSFile;
    private ResourceBundle myLanguageResources;
    private String INVALID_SIM_FILE = "InvalidSimFile";
    private String AboutSimulationLabel = "AboutSimulationLabel";
    private Game myGame;
    private Scene myScene;
    private SimulatorButtonFactory mySimulatorButtonFactory;
    private HBox simulationBox = new HBox();
    private Stage myStage;
    private SliderFactory mySliderFactory;
    private double sliderValue = 1.0;
    private double sliderMin = 0.1;
    private double sliderMax = 5.0;
    private String simulatorBoxId = "simulator-root";
    private int cellType;
    private GridBuilder myGridBuilder;
    private CSSFactory myCSSFactory;



    public SimulatorView(Game game, String cssFile, ResourceBundle resourceBundle, SimulatorController simulatorController, int cellType){
        mySimulatorController = simulatorController;
        myGame = game;
        myAnimation = new Timeline();
        myCSSFile = cssFile;
        myLanguageResources = resourceBundle;
        mySimulatorButtonFactory = new SimulatorButtonFactory(this, mySimulatorController, myLanguageResources);
        mySliderFactory = new SliderFactory(sliderValue);
        myGridView = new GridPane();
        initializeGridProperties();
        initializeFactories();
        setInitialGrid(cellType);
        initializeSimulationScene();
    }

    //TODO: may have to move update to simulator controller
    private void initializeGridProperties(){
        myGridWidth = myGame.getNumCols();
        myGridHeight = myGame.getNumRows();
        myGridView = new GridPane();
    }

    private void initializeFactories(){
        mySimulatorButtonFactory = new SimulatorButtonFactory(this, mySimulatorController, myLanguageResources);
        mySliderFactory = new SliderFactory(sliderValue);
        myCSSFactory = new CSSFactory(myLanguageResources);
    }

    private void initializeSimulationScene(){
        myStage = new Stage();
        updateSimulation(myGame, myGridView);
        simulationBox.getChildren().add(generateSimulationVBox(myGridView));
        myScene = new Scene(simulationBox);
        myCSSFactory.applyCSS(myScene, myCSSFile);
        myStage.setScene(myScene);
        myStage.show();
        playAnimation();
    }

    private void setInitialGrid(int cellType){
        switch (cellType) {
            case 0 -> {
                myGridBuilder = new SquareGridBuilder();
                myGridBuilder.CreateGrid(mySimulatorController, myGridWidth, myGridHeight, myGridView);
            }
            case 1 -> {
                myGridBuilder = new TriangleGridBuilder();
                myGridBuilder.CreateGrid(mySimulatorController, myGridWidth, myGridHeight, myGridView);
            }
            case 2 -> {
                myGridBuilder = new HexagonGridBuilder();
                myGridBuilder.CreateGrid(mySimulatorController, myGridWidth, myGridHeight, myGridView);
            }
        }
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
            new GenerateError(myLanguageResources, INVALID_SIM_FILE);
        }
    }

    private void setAlertContent(Alert alert, String text, String info){
        alert.setHeaderText(text);
        alert.setContentText(String.valueOf(info));
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

    public void updateCSS(String cssFile){
        myCSSFactory.applyCSS(myScene, cssFile);
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
}
