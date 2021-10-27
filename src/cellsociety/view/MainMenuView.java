package cellsociety.view;

import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.error.GenerateError;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private String[] buttonLabelOptions = {"ChooseSimulationTypeLabel", "LoadFileLabel", "ChooseColorScheme", "CreateNewSimulationLabel"};
    private ArrayList<EventHandler<ActionEvent>> buttonEventLists = new ArrayList<>();
    private String SIM_TYPE_BUTTON_LABEL = "ChooseSimulationTypeLabel";
    private String FILE_BUTTON_LABEL = "LoadFileLabel";
    private String NEW_SIM_BUTTON_LABEL = "CreateNewSimulationLabel";
    private String CHOOSE_COLOR_BUTTON_LABEL = "ChooseColorScheme";
    private String INVALID_CSS_ERROR = "InvalidCSSFile";
    private String mainMenuButtonID = "main-menu-button";
    private String homePageRootID = "home-page-root";
    private String[] cssFileLabelOptions = {"DukeLabel", "UNCLabel", "LightLabel", "DarkLabel"};
    private String[] modelLabelOptions = {"GameOfLife", "SpreadingOfFire", "Schelling's", "Wa-TorWorld", "Percolation"};


    private int myModelType;
    private ArrayList<String> cssFileOptions = new ArrayList<>();

    public MainMenuView(ResourceBundle resourceBundle){
        myLanguageResources = resourceBundle;
        myFileManager = new FileManager(myLanguageResources);

    }

    /**
     * creates mainMenu and returns the scene
     * @param stage primary stage
     * @return scene of main menu
     */
    public Scene setMenuDisplay(Stage stage, MainController mainController, int width, int height) {
        myMainController = mainController;

        populateOptions(modelOptions, modelLabelOptions);
        populateOptions(cssFileOptions, cssFileLabelOptions);
        populateMainMenuButtonMap();
        setLabel("Cell Society", "title");
        populateMainMenuButtonMap();
        initializeHomePageRoot();
        Scene scene = new Scene(homePageRoot, width, height);
        return scene;
    }

    private void populateOptions(ArrayList<String> optionsList, String[] labelList){
        for(String key: labelList){
            optionsList.add(myLanguageResources.getString(key));
        }
    }

    private void populateButtonEvents(){
        buttonEventLists.add(event -> generateChoiceDialogBox(myLanguageResources.getString(modelLabelOptions[0]),
                modelOptions, "modelType", myLanguageResources.getString("ModelContent")));
        buttonEventLists.add(event -> myFileManager.chooseFile());
        buttonEventLists.add(event-> generateChoiceDialogBox(myLanguageResources.getString(cssFileLabelOptions[0]),
                cssFileOptions, "cssFile", myLanguageResources.getString("ThemeContent")));
        buttonEventLists.add(event ->
                myMainController.generateNewSimulation(myModelType, myFileManager.getCurrentTextFile(), myFileManager));

    }

    private void initializeHomePageRoot(){
        homePageRoot = new TilePane();
        homePageRoot.getChildren().add(generateMainMenuPanel());
        homePageRoot.setId(homePageRootID);
    }

    private void setLabel(String label, String id){
        Label titleLabel = new Label(label);
        titleLabel.setId(id);
    }

    // maybe clean this up
    private void populateMainMenuButtonMap(){
        populateButtonEvents();
        int i = 0;
        for(String buttonLabel : buttonLabelOptions){
            mainMenuButtonMap.put(myLanguageResources.getString(buttonLabel), buttonEventLists.get(i));
            i++;
        }
    }

    private Node generateMainMenuPanel(){
        VBox panel = new VBox();
        mainMenuButtonMap.forEach((key,value) -> addButtonToPanel(key,value,panel));
        return panel;
    }

    private ChoiceDialog<String> generateChoiceDialogBox(String defaultChoice, ArrayList<String> options, String resultType, String content){
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(defaultChoice);
        addItemsToOptionsList(options, choiceDialog);
        choiceDialog.setContentText(content);
        showAndWaitForChoiceDialogResult(choiceDialog, resultType);
        return choiceDialog;
    }

    //TODO: use reflection to make this easier
    private void showAndWaitForChoiceDialogResult(ChoiceDialog<String> choiceDialog, String resultType){
        choiceDialog.showAndWait();
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

    private void addButtonToPanel(String label, EventHandler<ActionEvent> event, VBox panel){
        Button button = generateButton(label,
                event);
        button.setId(mainMenuButtonID);
        panel.getChildren().add(button);
    }

    private Button generateButton(String label, EventHandler<ActionEvent> event) {
        Button button = new Button();
        button.setText(label);
        button.setOnAction(event);
        return button;
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

