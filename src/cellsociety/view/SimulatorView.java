package cellsociety.view;

import cellsociety.Main;
import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.controller.SimulatorController;
import cellsociety.error.GenerateError;
import cellsociety.games.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.awt.Point;
import cellsociety.components.Cell;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SimulatorView {

    private Timeline myAnimation;
    private double animationSpeed;
    private GridPane myGridView;
    private int myGridWidth;
    private int myGridHeight;
    private SimulatorController mySimulatorController;
    private String myCSSFile;
    private Map<String, EventHandler<ActionEvent>> simulatorButtonMap = new LinkedHashMap<>();
    private String simulatorButtonID = "simulator-button";
    private ResourceBundle myLanguageResources;
    private String PLAY_LABEL = "PlayLabel";
    private String PAUSE_LABEL = "PauseLabel";
    private String STEP_LABEL = "StepLabel";
    private String SAVE_LABEL = "SaveLabel";
    private String LOAD_LABEL = "LoadLabel";
    private String ADD_SIM_LABEL = "AddLabel";
    private String INVALID_CSS_ERROR = "InvalidCSSFile";
    private Game myGame;
    private Scene myScene;
    private VBox simulationBox;
    private FileManager myFileManager;


    public SimulatorView(Game game, String cssFile, ResourceBundle resourceBundle, SimulatorController simulatorController){
        mySimulatorController = simulatorController;
        myGame = game;
        animationSpeed = 0.3;
        myAnimation = new Timeline();
        myGridView = new GridPane();
        myGridWidth = myGame.getNumRows();
        myGridHeight = myGame.getNumCols();
        myCSSFile = cssFile;
        myLanguageResources = resourceBundle;
        myFileManager = new FileManager(myLanguageResources);
        populateSimulatorButtonMap();
        setDefaultGrid();
        initializeSimulationScene();
    }

    private void initializeSimulationScene(){
        Stage stage = new Stage();
        updateSimulation(myGame);
        generateSimulationVBox();
        myScene = new Scene(simulationBox);
        stage.setScene(myScene);
        stage.show();
        playAnimation();
    }

    private void step(){
        myGame.update();
        updateSimulation(myGame);
    }

    // Start new animation to show search algorithm's steps
    public void playAnimation () {
        assert myAnimation != null;
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(animationSpeed), e -> step()));
        myAnimation.play();
    }

    private void pause(){
        myAnimation.pause();
    }

    private void play(){
        myAnimation.play();
    }

    private void setAnimationSpeed(double speed){
        animationSpeed = speed;
        myAnimation.setRate(animationSpeed);
    }

    // fills the grid with squareCells of defaultColor
    private void setDefaultGrid(){
        for(int i = 0; i < myGridWidth; i ++){
            for(int j = 0; j < myGridHeight; j++){
                SquareCell cell = new SquareCell();
                cell.setWidth(40); // TODO: Needs to change based on the size of the stage
                cell.setHeight(40); // TODO: need refactoring?
                cell.setId("default-cell");
                myGridView.add(cell, i, j);
            }
        }
    }

    /**
     * updates the cells based on the values in Map
     * dead cells are colored with deadColor, alive cells are colored with aliveColor
     * @param game
     * @return scene with updated cell status
     */
    public void updateSimulation(Game game){
        for (int x = 0; x < game.getNumCols(); x++) {
            for (int y = 0; y < game.getNumRows(); y++) {
                int gridNumber = y * myGridHeight + x;
                int cellStatus = game.getCellStatus(x, y);
                updateCell(gridNumber, cellStatus);
            }
        }
    }

    // updates cell status
    private void updateCell(int cellNumber, int cellStatus){
        Node currNode = myGridView.getChildren().get(cellNumber);
        currNode.setId(cellStatus+"-cell");
    }

    /**
     * This method creates a new grid for the new simulation to be displayed
     * @param gridWidth width of the new gridPane
     * @param gridHeight height of the new gridPane
     */
    public void updateToNewSimulation(int gridWidth, int gridHeight){
        myGridView = new GridPane();
        myGridWidth = gridWidth;
        myGridHeight = gridHeight;
        setDefaultGrid();
    }

    /**
     * getter for getMyGridView
     * @return getMyGridView
     */
    public GridPane getMyGridView(){
        return myGridView;
    }

    /**
     * Creates a scene of the simulation with the control buttons
     */
    private void generateSimulationVBox(){
        simulationBox = new VBox();
        HBox buttonBox = generateSimulatorButtonBox();
        buttonBox.getChildren().add(makeSlider(myLanguageResources.getString("SpeedLabel"), 0.1, 5.0));
        simulationBox.getChildren().addAll(myGridView, buttonBox);
        applyCSS(simulationBox, myCSSFile);
    }

    private void populateSimulatorButtonMap(){
        simulatorButtonMap.put(myLanguageResources.getString(PAUSE_LABEL), event -> pause());
        simulatorButtonMap.put(myLanguageResources.getString(PLAY_LABEL), event -> play());
        simulatorButtonMap.put(myLanguageResources.getString(STEP_LABEL), event -> step());
        simulatorButtonMap.put(myLanguageResources.getString(SAVE_LABEL), event -> mySimulatorController.saveCSVFile());
        simulatorButtonMap.put(myLanguageResources.getString(LOAD_LABEL), event -> changeSimulation());
    }

    private void changeSimulation(){
        Main newSimulationMain = new Main();
        Stage newStage = new Stage();
        newSimulationMain.start(newStage);
    }

    private HBox generateSimulatorButtonBox(){
        HBox buttonBox = new HBox();
        simulatorButtonMap.forEach((key,value) -> addButtonToPanel(key,value,buttonBox));
        return buttonBox;
    }

    private void addButtonToPanel(String label, EventHandler<ActionEvent> event, HBox panel){
        Button button = generateButton(label,
                event);
        button.setId(simulatorButtonID);
        panel.getChildren().add(button);
    }

    private Button generateButton(String label, EventHandler<ActionEvent> event) {
        javafx.scene.control.Button button = new javafx.scene.control.Button();
        button.setText(label);
        button.setOnAction(event);
        return button;
    }

    private Node makeSlider(String text, double minVal, double maxVal) {
        Slider lengthSlider = new Slider(minVal, maxVal, 1);
        lengthSlider.setShowTickMarks(true);
        lengthSlider.setShowTickLabels(true);
        lengthSlider.setMajorTickUnit(1);
        lengthSlider.setMaxWidth(100);
        lengthSlider.valueProperty().addListener((obs, oldval, newVal) ->
                setAnimationSpeed(newVal.intValue()));

        return lengthSlider;
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
