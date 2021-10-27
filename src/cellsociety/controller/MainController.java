package cellsociety.controller;

import cellsociety.error.GenerateError;
import cellsociety.view.MainMenuView;
import javafx.stage.Stage;

import java.io.File;
import java.util.ResourceBundle;

public class MainController {
    private static final String RESOURCE_PACKAGE = "cellsociety.resources.";
    private Stage myStage;
    private static ResourceBundle myLanguageResources;
    private String DEFAULT_LANGUAGE = "English";
    private SimulatorController simulatorController;
    private String modelType;
    private String cssFile;
    private MainMenuView mainMenu;
    private String DEFAULT_CSS_FILE_LABEL = "Duke";
    private String INVALID_CSS_ERROR = "InvalidCSSFile";
    private ResourceBundle myActionEventsResources;
    private String ACTION_BUNDLE = "MainMenuActionEvents";


    public MainController(Stage stage, String language){
        myStage = stage;
        myLanguageResources = initializeResourceBundle(language);
        myActionEventsResources = initializeResourceBundle(ACTION_BUNDLE);
    }

    public void startMainMenu() {
        mainMenu  = new MainMenuView(myLanguageResources, myActionEventsResources);
        myStage.setScene(mainMenu.setMenuDisplay(this, 500, 500));
        cssFile = DEFAULT_CSS_FILE_LABEL;
        updateCSS(cssFile);
        myStage.show();
    }

    /**
     * setter for modelType
     * @param result modelType
     */
    public void updateModelType(String result){
        modelType = result;
    }

    public void updateCSS(String result) {
        cssFile = myLanguageResources.getString(result);
        try{
            mainMenu.applyCSS(myStage.getScene(), cssFile);
        }catch(Exception e){
            new GenerateError(myLanguageResources, INVALID_CSS_ERROR);
        }
    }

    public void generateNewSimulation(int modelType, File csvFile, FileManager fileManager){
        simulatorController = new SimulatorController(fileManager, cssFile, myLanguageResources);
        simulatorController.createNewSimulation(modelType, csvFile);
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

    public ResourceBundle getMyActionEventsResources(){
        return myActionEventsResources;
    }

}
