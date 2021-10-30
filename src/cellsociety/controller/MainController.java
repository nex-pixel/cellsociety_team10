package cellsociety.controller;

import cellsociety.error.GenerateError;
import cellsociety.view.MainMenuView;
import javafx.stage.Stage;

import java.io.File;
import java.util.ResourceBundle;

public class MainController {

    private static final String RESOURCE_PACKAGE = "cellsociety.resources.";
    public static final String RESOURCE_ACTIONS_NAME = MainController.class.getPackageName() + ".Resources.ModelNames";
    public static final String RESOURCE_ACTIONS_LABEL = MainController.class.getPackageName() + ".Resources.ModelLabel";
    private static final String ACTION_BUNDLE = "MainMenuActionEvents";

    private ResourceBundle myActionEventsResources;
    private Stage myStage;
    private static ResourceBundle myLanguageResources;
    private String DEFAULT_LANGUAGE = "English";
    private SimulatorController simulatorController;
    private String modelType;
    private String cssFile;
    private MainMenuView mainMenu;
    private String DEFAULT_CSS_FILE_LABEL = "Duke";
    private String INVALID_CSS_ERROR = "InvalidCSSFile";
    private static final int MAIN_SCREEN_SIZE = 500;
    private static ResourceBundle actionNameBundle;
    private static ResourceBundle actionLabelBundle;



    public MainController(Stage stage, String language){
        myStage = stage;
        myLanguageResources = initializeResourceBundle(language);
        actionNameBundle = ResourceBundle.getBundle(RESOURCE_ACTIONS_NAME);
        myActionEventsResources = initializeResourceBundle(ACTION_BUNDLE);
    }

    public MainController(ResourceBundle languageResourceBundle){
        myStage = new Stage();
        myLanguageResources = languageResourceBundle;
        actionNameBundle = ResourceBundle.getBundle(RESOURCE_ACTIONS_NAME);
        myActionEventsResources = initializeResourceBundle(ACTION_BUNDLE);
    }



    public void startMainMenu() {
        mainMenu  = new MainMenuView(myLanguageResources, myActionEventsResources);
        myStage.setScene(mainMenu.setMenuDisplay(this, MAIN_SCREEN_SIZE, MAIN_SCREEN_SIZE, actionNameBundle));
        updateCSS(DEFAULT_CSS_FILE_LABEL);
        myStage.show();
    }

    /**
     * setter for modelType
     * @param result modelType
     */
    public void updateModelType(String result, FileManager fileManager){
        simulatorController = new SimulatorController(fileManager, cssFile, myLanguageResources);
        simulatorController.updateModelType(result);
    }

    public void updateCSS(String result) {
        cssFile = myLanguageResources.getString(result);
        try{
            mainMenu.applyCSS(myStage.getScene(), cssFile);
        }catch(Exception e){
            new GenerateError(myLanguageResources, INVALID_CSS_ERROR);
        }
    }

    public void generateNewSimulation(File csvFile){
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

}
