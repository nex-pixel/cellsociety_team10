package cellsociety.view;

import cellsociety.controller.SimulatorController;
import cellsociety.error.GenerateError;
import cellsociety.games.Game;
import cellsociety.view.cell.Cell;
import cellsociety.view.factories.buttonFactory.SimulatorButtonFactory;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * This class generates a view of the desired simulator and displays a grid with active cells and buttons to
 * manipulate and control the simulation
 *
 * @author Young Jun, Ryleigh Byrne
 */
public class SimulatorView {
    private static final String GRID_NAME_FILE_PATH = "cellsociety.resources.gameData.GridTypeNames";
    private static final String INVALID_SIM_GENERATION = "InvalidSimulation";
    private static final String INVALID_SIM_FILE = "InvalidSimFile";
    private static final String ABOUT_SIMULATION_LABEL = "AboutSimulationLabel";
    private static final String SIMULATOR_BOX_ID = "simulator-root";
    private static final String CELL_DEFAULT_ID = "-cell";
    private static final double DEFAULT_ANIMATION_SPEED = 0.3;
    private static final double SLIDER_VALUE = 1.0;
    private static final double SLIDER_MIN = 0.1;
    private static final double SLIDER_MAX = 5.0;

    private Timeline myAnimation;
    private GridPane myGridView;
    private int myGridWidth;
    private int myGridHeight;
    private SimulatorController mySimulatorController;
    private String myCSSFile;
    private ResourceBundle myLanguageResources;
    private Game myGame;
    private Scene myScene;
    private SimulatorButtonFactory mySimulatorButtonFactory;
    private Stage myStage;
    private SliderFactory mySliderFactory;
    private GridBuilder myGridBuilder;
    private CSSFactory myCSSFactory;

    /**
     * Constructor for SimulatorView. Initializes grid properties, UI factories, and displays a simulation scene
     * @param game current game model to be display
     * @param cssFile cssFile that dictates cell and button colors
     * @param langResourceBundle language resource bundle for UI and errors
     * @param simulatorController simulator controller to control scene
     * @param cellType type of cell (square, triangle, hexagon, etc) to be displayed in grid
     */
    public SimulatorView(Game game, String cssFile, ResourceBundle langResourceBundle, SimulatorController simulatorController, int cellType) {
        mySimulatorController = simulatorController;
        myGame = game;
        myAnimation = new Timeline();
        myCSSFile = cssFile;
        myLanguageResources = langResourceBundle;
        initializeGridProperties();
        initializeFactories();
        setInitialGrid(cellType);
        showSimulationScene();
    }

    private void initializeGridProperties() {
        myGridWidth = myGame.getNumCols();
        myGridHeight = myGame.getNumRows();
        myGridView = new GridPane();
    }

    // initializes all factory classes used
    private void initializeFactories() {
        mySimulatorButtonFactory = new SimulatorButtonFactory(this, mySimulatorController, myLanguageResources);
        mySliderFactory = new SliderFactory(SLIDER_VALUE);
        myCSSFactory = new CSSFactory(myLanguageResources);
    }

    // uses reflection to populate grid based on the cellType
    private void setInitialGrid(int cellType) {
        try {
            ResourceBundle gridTypeName = ResourceBundle.getBundle(GRID_NAME_FILE_PATH);
            Method m = this.getClass().getDeclaredMethod(gridTypeName.getString(cellType + ""));
            m.invoke(this);
        } catch (Exception e) {
            new GenerateError(myLanguageResources, INVALID_SIM_GENERATION);
        }
    }

    // method used in reflection - creates SquareGrid
    private void createSquareGrid() {
        myGridBuilder = new SquareGridBuilder();
        myGridBuilder.CreateGrid(mySimulatorController, myGridWidth, myGridHeight, myGridView);
    }

    // method used in reflection - creates TriangleGrid
    private void createTriangleGrid() {
        myGridBuilder = new TriangleGridBuilder();
        myGridBuilder.CreateGrid(mySimulatorController, myGridWidth, myGridHeight, myGridView);
    }

    // method used in reflection - creates HexagonGrid
    private void createHexagonGrid() {
        myGridBuilder = new HexagonGridBuilder();
        myGridBuilder.CreateGrid(mySimulatorController, myGridWidth, myGridHeight, myGridView);
    }

    // creates a stage for the simulation and shows it with the scene of the simulation
    private void showSimulationScene() {
        myStage = new Stage();
        updateSimulation(myGame, myGridView);
        HBox simulationBox = new HBox();
        simulationBox.getChildren().add(generateSimulationHBox(myGridView));
        myScene = new Scene(simulationBox);
        myCSSFactory.applyCSS(myScene, myCSSFile);
        myStage.setScene(myScene);
        myStage.show();
        playAnimation();
    }

    private void playAnimation() {
        assert myAnimation != null;
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(DEFAULT_ANIMATION_SPEED), e -> step()));
        myAnimation.play();
    }

    // creates and returns a HBox containing the gameGrid and buttons
    private HBox generateSimulationHBox(GridPane gameGrid) {
        HBox simulationBox = new HBox();
        simulationBox.setId(SIMULATOR_BOX_ID);
        simulationBox.getChildren().addAll(gameGrid, mySimulatorButtonFactory.generateButtonPanel(), mySliderFactory.makeSlider(SLIDER_MIN, SLIDER_MAX,
                (obs, oldVal, newVal) -> setAnimationSpeed((double) newVal)));
        return simulationBox;
    }

    // updates the gamePane based on the values from backend
    private void updateSimulation(Game game, GridPane gamePane) {
        for (Node node : gamePane.getChildren()) {
            Cell cell = (Cell) node;
            Point point = cell.getPoint();
            int cellStatus = game.getCellStatus(point.x, point.y);
            updateCell(gamePane, point, cellStatus);
        }
    }

    // updates cell's status
    private void updateCell(GridPane gamePane, Point point, int cellStatus) {
        int cellNumber = (point.x) * myGridHeight + point.y;
        Node currNode = gamePane.getChildren().get(cellNumber);
        currNode.setId(cellStatus + CELL_DEFAULT_ID);
    }

    /**
     * Create an alert with the information of the simulation
     * This method is executed when About button is clicked by the user
     */
    public void displaySimulationInfo() {
        try {
            Scanner file = new Scanner(new File(mySimulatorController.getSimFilePath()));
            StringBuilder simInfo = new StringBuilder();
            while (file.hasNextLine()) simInfo.append(file.nextLine()).append("\n");
            generateAndDisplay(Alert.AlertType.INFORMATION, myLanguageResources.getString(ABOUT_SIMULATION_LABEL), String.valueOf(simInfo));
        } catch (FileNotFoundException e) {
            new GenerateError(myLanguageResources, INVALID_SIM_FILE);
        }
    }

    // generates and displays alert given alert type and text to display
    private void generateAndDisplay(Alert.AlertType alertType, String headerText, String information){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(String.valueOf(information));
        alert.showAndWait();
    }

    /**
     * Changes the CSS file used for the simulation
     * This method is executed when user clicks on Choose a new color scheme button
     *
     * @param cssFile new cssFile to be applied
     */
    public void updateCSS(String cssFile) {
        myCSSFactory.applyCSS(myScene, cssFile);
    }

    /**
     * Updates the grid to the next step
     * This method is executed when user clicks on Step button
     */
    public void step() {
        myGame.update();
        updateSimulation(myGame, myGridView);
    }

    /**
     * Pauses the simulation animation
     * This method is executed when user clicks on Pause button
     */
    public void pause() {
        myAnimation.pause();
    }

    /**
     * Resumes the simulation animation
     * This method is executed when user clicks on Play button
     */
    public void play() {
        myAnimation.play();
    }

    /**
     * Changes the animation speed of simulation
     * This method is executed when moves the Speed slider
     *
     * @param speed rate to be applied to current animation speed
     */
    public void setAnimationSpeed(double speed) {
        myAnimation.setRate(speed);
    }

    /**
     * Closes the stage of the current simulation
     */
    public void closeSimulation() {
        myStage.close();
    }
}
