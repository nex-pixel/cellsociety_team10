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
    private Stage myStage;
    private static ResourceBundle myLanguageResources;
    private String DEFAULT_LANGUAGE = "English";
    private SimulatorController simulatorController;
    private String language;
    private String modelType;
    private String cssFile;
    private MainMenuView mainMenu;
    private String INVALID_CSS_ERROR = "InvalidCSSFile";


    public MainController(Stage stage){
        myStage = stage;
        initializeResourceBundle(DEFAULT_LANGUAGE);
    }

    public void startMainMenu() {
        mainMenu  = new MainMenuView();
        myStage.setScene(mainMenu.setMenuDisplay(myStage, this, 500, 500));
        myStage.show();
    }


    /**
     * setter for language;
     * @param result language
     */
    public void updateLanguage(String result){
        language = result;
        initializeResourceBundle(language);
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
            new GenerateError(myLanguageResources, myLanguageResources.getString(INVALID_CSS_ERROR));
        }
    }

    public void generateNewSimulation(File csvFile){
        simulatorController = new SimulatorController(500, 500, Color.CORAL, Color.BEIGE, Color.BROWN);
        simulatorController.createNewSimulation(myStage, csvFile);
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

    public ResourceBundle getResourceBundle() {
        return myLanguageResources;
    }


}
