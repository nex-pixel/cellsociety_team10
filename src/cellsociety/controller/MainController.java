package cellsociety.controller;

import cellsociety.view.MainMenuView;
import cellsociety.view.factories.cssFactory.CSSFactory;
import javafx.stage.Stage;

import java.io.File;
import java.util.ResourceBundle;

public class MainController {

    private static final String RESOURCE_PACKAGE = "cellsociety.resources.languages.";
    private Stage myStage;
    private static ResourceBundle myLanguageResources;
    private String DEFAULT_LANGUAGE = "English";
    private SimulatorController simulatorController;
    private String cssFile;
    private MainMenuView mainMenu;
    private String DEFAULT_CSS_FILE_LABEL = "Duke";
    private static final int MAIN_SCREEN_SIZE = 500;
    private String myLanguage;
    private int myCellType;
    private int myNeighborMode;
    private int myEdgePolicy;
    private FileManager myFileManager;
    private String modelType;
    private CSSFactory myCSSFactory;

    public MainController(Stage stage, String language){
        myLanguage = language;
        myStage = stage;
        myLanguageResources = initializeResourceBundle(myLanguage);
        myCSSFactory = new CSSFactory(myLanguageResources);
    }

    public void startMainMenu() {
        mainMenu  = new MainMenuView(myLanguageResources);
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
     * @param result modelType
     */
    public void updateModelType(String result, FileManager fileManager){
        modelType = result;
        myFileManager = fileManager;
    }

    public void setCellType(int cellType){
        myCellType = cellType;
    }

    public void setMyNeighborMode(int modeType){
        myNeighborMode = modeType;
    }

    public void setMyEdgePolicy(int edgePolicy){
        myEdgePolicy = edgePolicy;
    }

    public void updateCSS(String result) {
        cssFile = myLanguageResources.getString(result);
        myCSSFactory.applyCSS(myStage.getScene(), cssFile);
    }

    public void generateNewSimulation(File csvFile){
        simulatorController = new SimulatorController(this, myFileManager, cssFile, myLanguageResources,
                myCellType, myNeighborMode, myEdgePolicy);
        simulatorController.updateModelType(modelType);
        simulatorController.updateMyCSSFile(cssFile);
        simulatorController.createNewSimulation(csvFile);
    }


    private ResourceBundle initializeResourceBundle(String name) {
        try {
            return generateResourceBundle(name);
        } catch (Exception e) {
            return generateResourceBundle(DEFAULT_LANGUAGE);
        }
    }

    private ResourceBundle generateResourceBundle(String name) {
        return ResourceBundle.getBundle(RESOURCE_PACKAGE + name);
    }

    public double getSegregationThreshold(){
        return mainMenu.getSegregationThreshold();
    }


}
