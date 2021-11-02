package cellsociety.controller;

import cellsociety.ReflectionHandler;
import cellsociety.error.GenerateError;
import cellsociety.games.*; // used * because this class uses all classes in games
import cellsociety.view.SimulatorView;

import java.io.File;
import java.util.ResourceBundle;

public class SimulatorController {

    private static final String RESOURCE_ACTIONS_NAME = "cellsociety.controller.resources.actionNames.";
    private static final String INVALID_SIM_GENERATION = "InvalidSimulation";
    private static final String SIMULATOR_CLASSPATH = "cellsociety.controller.SimulatorController";
    private static final String LANG_KEY = "language";
    private static final String CSV_FILE_TYPE = "csv";
    private static final String SIM_FILE_TYPE = "sim";
    // Things to remember
    private Game myGame;
    private SimulatorView mySimulatorView;
    private FileManager myFileManager;
    private String myCSSFile;
    private ResourceBundle myLanguageResources;
    private String myModelType;
    private ResourceBundle actionNameBundle;
    private File myCSVFile;
    private MainController myMainController;
    private int myCellType;
    private int myNeighborMode;
    private int myEdgePolicy;
    private ReflectionHandler myReflectionHandler;


    public SimulatorController(MainController mainController, FileManager fileManager, String cssFile, ResourceBundle resourceBundle
            , int cellType, int neighborMode, int edgePolicy) {
        // TODO property change listener simple String Property
        myMainController = mainController;
        myCSSFile = cssFile;
        myLanguageResources = resourceBundle;
        initializeActionBundle();
        myCellType = cellType;
        myNeighborMode = neighborMode;
        myEdgePolicy = edgePolicy;
        myFileManager = fileManager;
        myReflectionHandler = new ReflectionHandler();
    }

    private void initializeActionBundle() {
        String filePath = RESOURCE_ACTIONS_NAME + myLanguageResources.getString(LANG_KEY);
        actionNameBundle = ResourceBundle.getBundle(filePath);
    }

    /**
     * Receives csvFile with the initial state of the cells and creates new SimulatorView
     * Uses reflection to make different Simulation based on user's choice
     *
     * @param csvFile file containing the initial state
     */
    public void createNewSimulation(File csvFile) {
        myCSVFile = csvFile;
        try {
            myReflectionHandler.handleMethod(actionNameBundle.getString(myModelType), SIMULATOR_CLASSPATH).invoke(SimulatorController.this);
            mySimulatorView = new SimulatorView(myGame,
                    myCSSFile, myLanguageResources, this, myCellType);
        } catch (Exception e) {
            myFileManager.checkFileValidity(csvFile);
            new GenerateError(myLanguageResources, INVALID_SIM_GENERATION);
        }
    }

    /**
     * saves current state as a CSV File
     */
    public void saveCSVFile() {
        myFileManager.saveCSVFile(myGame);
    }

    /**
     * return the path of simulation's .sim information file
     *
     * @return file path
     */
    public String getSimFilePath() {
        return myCSVFile.getAbsolutePath().replaceAll(CSV_FILE_TYPE, SIM_FILE_TYPE);
    }

    /**
     * load a new simulation of user's choice
     */
    public void loadNewCSV() {
        myMainController.loadNewGame();
    }

    /**
     * replace the current simulation with new simulation of user's choice
     */
    public void replaceWithNewCSV() {
        mySimulatorView.closeSimulation();
        myMainController.loadNewGame();
    }

    /**
     * setter of modelType
     *
     * @param modelType model type
     */
    public void updateModelType(String modelType) {
        myModelType = modelType;
    }

    /**
     * updates the CSS file to the color scheme that the user chooses
     *
     * @param result file path
     */
    public void updateCSSFile(String result) {
        String cssFile = myLanguageResources.getString(result);
        mySimulatorView.updateCSS(cssFile);
    }

    /**
     * setter method of myCSSFile
     *
     * @param cssFile file path
     */
    public void setMyCSSFile(String cssFile) {
        myCSSFile = cssFile;
    }

    /**
     * updates the cell status when the cell is clicked
     * called by the cell when it is clicked
     *
     * @param xCoordinate x coordinate value
     * @param yCoordinate y coordinate value
     */
    public void updateCellOnClick(int xCoordinate, int yCoordinate) {
        myGame.changeCellOnClick(xCoordinate, yCoordinate);
    }

    // methods called using reflection to create Game
    private void makeGameOfLife() {
        myGame = new GameOfLifeModel(myCSVFile.getAbsolutePath(), myCellType, myNeighborMode, myEdgePolicy);
    }

    private void makePercolation() {
        myGame = new PercolationModel(myCSVFile.getAbsolutePath(), myCellType, myNeighborMode, myEdgePolicy);
    }

    private void makeSegregation() {
        double threshold = myMainController.getSegregationThreshold();
        myGame = new SegregationModel(myCSVFile.getAbsolutePath(), threshold);
    }

    private void makeSpreadingFire() {
        myGame = new SpreadingFireModel(myCSVFile.getAbsolutePath(), myCellType, myNeighborMode, myEdgePolicy);
    }

    private void makeWaTorWorld() {
        myGame = new WaTorWorldModel(myCSVFile.getAbsolutePath(), myCellType, myNeighborMode, myEdgePolicy);
    }
}
