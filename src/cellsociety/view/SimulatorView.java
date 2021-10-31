package cellsociety.view;

import cellsociety.controller.SimulatorController;
import cellsociety.error.GenerateError;
import cellsociety.games.Game;
import cellsociety.view.buttonFactory.SimulatorButtonFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
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
    private Game myGame;
    private Scene myScene;
    private SimulatorButtonFactory mySimulatorButtonFactory;
    private HBox simulationBox;
    private List<Game> myGameList;
    private Map<Game, GridPane> gameGridPaneMap;



    public SimulatorView(Game game, String cssFile, ResourceBundle resourceBundle, SimulatorController simulatorController){
        mySimulatorController = simulatorController;
        myGame = game;
        myGameList = new ArrayList<>();
        gameGridPaneMap = new HashMap<>();
        myGameList.add(myGame);
        animationSpeed = 0.3;
        myAnimation = new Timeline();
        myGridView = new GridPane();
        simulationBox = new HBox();
        myGridWidth = myGame.getNumCols();
        myGridHeight = myGame.getNumRows();
        myCSSFile = cssFile;
        gameGridPaneMap.put(myGame, myGridView);
        myLanguageResources = resourceBundle;
        mySimulatorButtonFactory = new SimulatorButtonFactory(this, mySimulatorController, myLanguageResources);
        setDefaultTriangleGrid(myGridWidth, myGridHeight, myGridView);
        initializeSimulationScene();
    }

    private void initializeSimulationScene(){
        Stage stage = new Stage();
        updateSimulation(myGame, myGridView);
        simulationBox.getChildren().add(generateSimulationVBox(myGridView));
        myScene = new Scene(simulationBox);
        stage.setScene(myScene);
        stage.show();
        playAnimation();
    }

    public void step(){
        for(Game game : gameGridPaneMap.keySet()){
            game.update();
            updateSimulation(game, gameGridPaneMap.get(game));
        }

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

    public void addNewSimulation(Game game){
        GridPane newGamePane = new GridPane();
        setDefaultGrid(game.getNumRows(), game.getNumCols(), newGamePane);
        updateSimulation(game, newGamePane);
        simulationBox.getChildren().add(generateSimulationVBox(newGamePane));
        gameGridPaneMap.put(game, newGamePane);

    }

    public void pause(){
        myAnimation.pause();
    }

    public void play(){
        myAnimation.play();
    }

    private void setAnimationSpeed(double speed){
        myAnimation.setRate(speed);
    }

    // fills the grid with squareCells of defaultColor
    private void setDefaultGrid(int gridWidth, int gridHeight, GridPane gamePane){
        for(int i = 0; i < gridWidth; i ++){
            for(int j = 0; j < gridHeight; j++){
                SquareCell cell = new SquareCell(mySimulatorController, i, j);
                cell.setWidth(40); // TODO: Needs to change based on the size of the stage
                cell.setHeight(40); // TODO: need refactoring?
                cell.setId("default-cell");
                gamePane.add(cell, i, j);
            }
        }
    }

    private void setDefaultTriangleGrid(int gridWidth, int gridHeight, GridPane gamePane){
        int counter = 0;
        for(int i = 0; i < gridHeight; i ++){
            for(int j = 0; j < gridWidth; j++){
                TriangleCell cell = new TriangleCell(mySimulatorController, counter % 2, j, i);
                cell.setId("default-cell");
                gamePane.getChildren().add(cell);
                cell.setTranslateX(j*25);
                cell.setTranslateY(i*43.30125);
                counter += 1;
            }
        }
        gamePane.setPrefWidth(gridWidth*25);
        gamePane.setPrefHeight(gridHeight*43.30125);
    }

    private void setDefaultHexagonGrid(int gridWidth, int gridHeight, GridPane gamePane){
        int counter = 0;
        for(int i = 0; i < gridHeight; i ++){
            for(int j = 0; j < gridWidth; j++){
                HexagonCell cell = new HexagonCell(mySimulatorController, j, i);
                cell.setId("default-cell");
                gamePane.getChildren().add(cell);
                cell.setTranslateX(j*30.1);
                cell.setTranslateY(i*34.64 + 17.321*(counter%2));
                counter += 1;
            }
            counter = 0;
        }
        gamePane.setPrefWidth((gridWidth+1)*30);
        gamePane.setPrefHeight((gridHeight+1)*34.64);
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
     * getter for getMyGridView
     * @return getMyGridView
     */
    public GridPane getMyGridView(){
        return myGridView;
    }

    /**
     * Returns a scene of the simulation with the control buttons
     * @return VBox containing gridpane of the simulation and control buttons
     */
    private VBox generateSimulationVBox(GridPane gameGrid){
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(mySimulatorButtonFactory.generateButtonPanel());
        buttonBox.getChildren().add(makeSlider(myLanguageResources.getString("SpeedLabel"), 0.1, 5.0));
        VBox simulationBox = new VBox();
        simulationBox.getChildren().addAll(gameGrid, buttonBox);
        applyCSS(simulationBox, myCSSFile);
        return simulationBox;
    }


    private Node makeSlider(String text, double minVal, double maxVal) {
        HBox sliderBox = new HBox();
        Slider lengthSlider = new Slider(minVal, maxVal, 1.0);
        lengthSlider.setShowTickMarks(true);
        lengthSlider.setShowTickLabels(true);
        lengthSlider.setMajorTickUnit(1);
        lengthSlider.setMaxWidth(100);
        lengthSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                setAnimationSpeed((double)newVal));
        sliderBox.getChildren().addAll(new Text(text), lengthSlider);
        return sliderBox;
    }

    private void applyCSS(VBox scene, String cssFile) {
        try{
            File styleFile = new File(cssFile);
            scene.getStylesheets().add(styleFile.toURI().toURL().toString());
        }catch(Exception e){
            new GenerateError(myLanguageResources, INVALID_CSS_ERROR);
        }
    }
}
