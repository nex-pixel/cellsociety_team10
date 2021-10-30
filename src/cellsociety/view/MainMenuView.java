package cellsociety.view;

import cellsociety.Main;
import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.error.GenerateError;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.io.File;
import java.util.*;

public class MainMenuView {
    private static ResourceBundle myLanguageResources;
    private FileManager myFileManager;
    private Pane homePageRoot;
    private MainController myMainController;
    private String INVALID_CSS_ERROR = "InvalidCSSFile";
    private String homePageRootID = "home-page-root";
    public ResourceBundle myActionEventsResources;
    private MainMenuButtonFactory myMainMenuButtonView;
    private ResourceBundle gameNames;

    public MainMenuView(ResourceBundle languageResourceBundle, ResourceBundle actionResourceBundle){
        myLanguageResources = languageResourceBundle;
        myFileManager = new FileManager(myLanguageResources);
        myActionEventsResources = actionResourceBundle;
    }

    public MainMenuView(ResourceBundle languageResourceBundle){
        myLanguageResources = languageResourceBundle;
        myFileManager = new FileManager(myLanguageResources);
    }

    /**
     * creates mainMenu and returns the scene
     * @return scene of main menu
     */
    public Scene setMenuDisplay(MainController mainController, int width, int height, ResourceBundle gameNames) {
        myMainController = mainController;
        myMainMenuButtonView = new MainMenuButtonFactory(this, myMainController, myLanguageResources, myActionEventsResources, myFileManager);
        this.gameNames = gameNames;
        setLabel("Cell Society", "title");
        initializeHomePageRoot();
        Scene scene = new Scene(homePageRoot, width, height);
        return scene;
    }

    /**
     * creates mainMenu and returns the scene
     * @return scene of main menu
     */
    public Scene setNewGameChoiceDisplay(int width, int height, ResourceBundle gameNames) {
        myMainMenuButtonView = new NewSimulatorButtonFactory(myLanguageResources);
        this.gameNames = gameNames;
        setLabel("Cell Society", "title");
        initializeHomePageRoot();
        Scene scene = new Scene(homePageRoot, width, height);
        return scene;
    }

    private void initializeHomePageRoot(){
        homePageRoot = new TilePane();
        homePageRoot.getChildren().add(myMainMenuButtonView.generateButtonPanel());
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
        if(resultType.equals("cssFile")){
            myMainController.updateCSS(choiceDialog.getSelectedItem());
        }
        if(resultType.equals("modelType")){
            myMainController.updateModelType(choiceDialog.getSelectedItem(), myFileManager);
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

