package cellsociety.controller;

import cellsociety.error.GenerateError;
import cellsociety.view.MainMenuView;
import javafx.stage.Stage;

import java.io.File;
import java.util.ResourceBundle;

public class MainController {
    public static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.resources.";
    private Stage myStage;
    private static ResourceBundle myLanguageResources;
    private String DEFAULT_LANGUAGE = "English";
    private SimulatorController simulatorController;
    private String modelType;
    private String cssFile;
    private MainMenuView mainMenu;
    private String DEFAULT_CSS_FILE_LABEL = "Duke";
    private String INVALID_CSS_ERROR = "InvalidCSSFile";


    public MainController(Stage stage, String language){
        myStage = stage;
        initializeResourceBundle(language);
    }

    public void startMainMenu() {
        mainMenu  = new MainMenuView(myLanguageResources);
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

    private void initializeResourceBundle(String language) {
        try {
            generateResourceBundle(language);
        } catch (Exception e) {
            generateResourceBundle(DEFAULT_LANGUAGE);
        }
    }

    private void generateResourceBundle(String language) {
        myLanguageResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    }

}
