package cellsociety.controller;

import cellsociety.error.GenerateError;
import cellsociety.view.MainMenuView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import javax.swing.text.html.CSS;
import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController {
    public static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.resources.";
    public static final String RESOURCE_ACTIONS_NAME = MainController.class.getPackageName() + ".Resources.ActionName";
    public static final String RESOURCE_ACTIONS_LABEL = MainController.class.getPackageName() + ".Resources.ActionLabel";
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
        initializeResourceBundle(language);
        actionNameBundle = ResourceBundle.getBundle(RESOURCE_ACTIONS_NAME);
        actionLabelBundle = ResourceBundle.getBundle(RESOURCE_ACTIONS_LABEL);
        myActionEventsResources = ResourceBundle.getBundle(ACTION_BUNDLE);
    }

    public void startMainMenu() {
        mainMenu  = new MainMenuView(myLanguageResources);
        myStage.setScene(mainMenu.setMenuDisplay(myStage, this, MAIN_SCREEN_SIZE, MAIN_SCREEN_SIZE, actionNameBundle));
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

    public void generateNewSimulation(String modelType, File csvFile, FileManager fileManager){
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
