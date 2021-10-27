package cellsociety.view;

import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.error.GenerateError;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainMenuView {
    private static ResourceBundle myLanguageResources;
    private FileManager myFileManager;
    private Pane homePageRoot;
    private MainController myMainController;
    private ArrayList<String> modelOptions = new ArrayList<>();;
    private Map<String, EventHandler<ActionEvent>> mainMenuButtonMap = new LinkedHashMap<>();
    private String INVALID_CSS_ERROR = "InvalidCSSFile";
    private String homePageRootID = "home-page-root";
    private String[] cssFileLabelOptions = {"DukeLabel", "UNCLabel", "LightLabel", "DarkLabel"};
    private String[] modelLabelOptions = {"GameOfLife", "SpreadingOfFire", "Schelling's", "Wa-TorWorld", "Percolation"};
    public ResourceBundle myActionEventsResources;
    private MainMenuButtonView but;

    private int myModelType;
    private ArrayList<String> cssFileOptions = new ArrayList<>();

    public MainMenuView(ResourceBundle languageResourceBundle, ResourceBundle actionResourceBundle){
        myLanguageResources = languageResourceBundle;
        myFileManager = new FileManager(myLanguageResources);
        myActionEventsResources = actionResourceBundle;
    }

    /**
     * creates mainMenu and returns the scene
     * @return scene of main menu
     */
    public Scene setMenuDisplay(MainController mainController, int width, int height) {
        myMainController = mainController;
        but = new MainMenuButtonView(this, myMainController, myLanguageResources, myActionEventsResources, myFileManager);
        populateOptions(modelOptions, modelLabelOptions);
        populateOptions(cssFileOptions, cssFileLabelOptions);

        setLabel("Cell Society", "title");

        //populateMainMenuButtonMap();
        initializeHomePageRoot();
        Scene scene = new Scene(homePageRoot, width, height);
        return scene;
    }

    private void populateOptions(ArrayList<String> optionsList, String[] labelList){
        for(String key: labelList){
            optionsList.add(myLanguageResources.getString(key));
        }
    }


    private void initializeHomePageRoot(){
        homePageRoot = new TilePane();
        homePageRoot.getChildren().add(but.generateMainMenuPanel());
        homePageRoot.setId(homePageRootID);
    }

    private void setLabel(String label, String id){
        Label titleLabel = new Label(label);
        titleLabel.setId(id);
    }


    public ChoiceDialog<String> generateChoiceDialogBox(String defaultChoice, ArrayList<String> options, String resultType, String content){
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(defaultChoice);
        addItemsToOptionsList(options, choiceDialog);
        choiceDialog.setContentText(content);
        showAndWaitForChoiceDialogResult(choiceDialog, resultType);
        return choiceDialog;
    }

    //TODO: use reflection to make this easier
    private void showAndWaitForChoiceDialogResult(ChoiceDialog<String> choiceDialog, String resultType){
        choiceDialog.showAndWait();
        //TODO; MYMODELTYPE SHOULD BE UPDATED BY SIMULATION CONTROLLER
        if(resultType.equals("modelType")){
            myModelType = modelOptions.indexOf(choiceDialog.getSelectedItem());
        }
        if(resultType.equals("cssFile")){
            myMainController.updateCSS(choiceDialog.getSelectedItem());
        }
    }

    private void addItemsToOptionsList(ArrayList<String> options, ChoiceDialog<String> choiceDialog){
        for(String s : options){
            choiceDialog.getItems().add(s);
        }
    }


    public void applyCSS(Scene scene, String cssFile) {
        try{
            File styleFile = new File(cssFile);
            scene.getStylesheets().add(styleFile.toURI().toURL().toString());
        }catch(Exception e){
            new GenerateError(myLanguageResources, INVALID_CSS_ERROR);
        }
    }

}

