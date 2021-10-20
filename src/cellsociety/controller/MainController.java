package cellsociety.controller;

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

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController {
    public static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety/resources/";

    private Stage myStage;
    private static ResourceBundle myLanguageResources;
    private String cssFilePath;

    public MainController(Stage stage, String language, String cssFilePath){
        myStage = stage;
        myLanguageResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        this.cssFilePath = cssFilePath;
    }

    public void startMainMenu(){
        MainMenuView mainMenu  = new MainMenuView("English", "src/cellsociety/resources/GameStyleSheet.css");
        myStage.setScene(mainMenu.setMenuDisplay(300, 300));
        myStage.show();
    }

    public void display(){
        myStage.show();
    }





}
