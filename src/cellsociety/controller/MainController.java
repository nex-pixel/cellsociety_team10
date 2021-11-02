package cellsociety.controller;

import cellsociety.view.MainMenuView;
import cellsociety.view.factories.cssFactory.CSSFactory;
import javafx.stage.Stage;

import java.io.File;
import java.util.ResourceBundle;

/**
 * Controller class for mainMenu. Handles updates for both the view and game
 *
 * @author Ryleigh Byrne, Young Jun
 */
public class MainController {
    private static final String DEFAULT_CSS_FILE_LABEL = "Duke";
    private static final int MAIN_SCREEN_SIZE = 500;
    private Stage myStage;
    private static ResourceBundle myLanguageResources;
    private SimulatorController simulatorController;
    private String cssFile;
    private MainMenuView mainMenu;
    private int myCellType;
    private int myNeighborMode;
    private int myEdgePolicy;
    private FileManager myFileManager;
    private String modelType;
    private CSSFactory myCSSFactory;

    /**
     * Constructor for main controller. Initializes necessary bundles and factories
     * @param stage stage the view is displayed on
     * @param langResourceBundle language resource bundle to be used
     */
    public MainController(Stage stage, ResourceBundle langResourceBundle) {
        myStage = stage;
        myLanguageResources = langResourceBundle;
        myCSSFactory = new CSSFactory(myLanguageResources);
    }

    public void startMainMenu() {
        mainMenu = new MainMenuView(myLanguageResources);
        myStage.setScene(mainMenu.setMenuDisplay(this, MAIN_SCREEN_SIZE, MAIN_SCREEN_SIZE));
        updateCSS(DEFAULT_CSS_FILE_LABEL);
        myStage.show();
    }

    public void loadNewGame() {
        myStage.close();
        startMainMenu();
    }

    /**
     * setter for modelType
     *
     * @param result modelType
     */
    public void updateModelType(String result, FileManager fileManager) {
        modelType = result;
        myFileManager = fileManager;
    }

    public void setCellType(int cellType) {
        myCellType = cellType;
    }

    public void setMyNeighborMode(int modeType) {
        myNeighborMode = modeType;
    }

    public void setMyEdgePolicy(int edgePolicy) {
        myEdgePolicy = edgePolicy;
    }

    public void updateCSS(String result) {
        cssFile = myLanguageResources.getString(result);
        myCSSFactory.applyCSS(myStage.getScene(), cssFile);
    }

    public void generateNewSimulation(File csvFile) {
        simulatorController = new SimulatorController(this, myFileManager, cssFile, myLanguageResources,
                myCellType, myNeighborMode, myEdgePolicy);
        simulatorController.updateModelType(modelType);
        simulatorController.setMyCSSFile(cssFile);
        simulatorController.createNewSimulation(csvFile);
    }

    public double getSegregationThreshold() {
        return mainMenu.getSegregationThreshold();
    }


}
