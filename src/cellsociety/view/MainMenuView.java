package cellsociety.view;

import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.controller.SimulatorController;
import cellsociety.error.GenerateError;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainMenuView {
    private static ResourceBundle myLanguageResources;
    private Stage window;
    private FileManager myFileManager;
    private Pane homePageRoot;
    private MainController myMainController;
    private final String DEFAULT_MODEL = "Game of Life";
    private ArrayList<String> modelOptions = new ArrayList<>();;
    private Map<String, EventHandler<ActionEvent>> mainMenuButtonMap = new LinkedHashMap<>();
    private String SIM_TYPE_BUTTON_LABEL = "ChooseSimulationTypeLabel";
    private String FILE_BUTTON_LABEL = "LoadFileLabel";
    private String NEW_SIM_BUTTON_LABEL = "CreateNewSimulationLabel";
    private String CHOOSE_COLOR_BUTTON_LABEL = "ChooseColorScheme";
    private String INVALID_CSS_ERROR = "InvalidCSSFile";
    private String DEFAULT_CSS_FILE_LABEL = "Duke";
    private String mainMenuButtonID = "main-menu-button";
    private String homePageRootID = "home-page-root";


    private int myModelType;
    private ArrayList<String> cssFileOptions = new ArrayList<>();

    public MainMenuView(ResourceBundle resourceBundle){
        myLanguageResources = resourceBundle;
        myFileManager = new FileManager();
    }

    /**
     * creates mainMenu and returns the scene
     * @param stage primary stage
     * @return scene of main menu
     */
    public Scene setMenuDisplay(Stage stage, MainController mainController, int width, int height) {
        myMainController = mainController;

        populateModelOptions();
        populateCSSFileOptions();

        window = stage;
        setLabel("Cell Society", "title");

        populateMainMenuButtonMap();
        initializeHomePageRoot();
        Scene scene = new Scene(homePageRoot, width, height);
        return scene;
    }

    private void populateModelOptions(){
        modelOptions.add(myLanguageResources.getString("GameOfLife"));
        modelOptions.add(myLanguageResources.getString("SpreadingOfFire"));
        modelOptions.add(myLanguageResources.getString("Schelling's"));
        modelOptions.add(myLanguageResources.getString("Wa-TorWorld"));
        modelOptions.add(myLanguageResources.getString("Percolation"));
    }


    private void populateCSSFileOptions(){
        cssFileOptions.add(myLanguageResources.getString("LightLabel"));
        cssFileOptions.add(myLanguageResources.getString("DarkLabel"));
        cssFileOptions.add(myLanguageResources.getString("DukeLabel"));
        cssFileOptions.add(myLanguageResources.getString("UNCLabel"));

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
        mainMenuButtonMap.put(myLanguageResources.getString(SIM_TYPE_BUTTON_LABEL), event -> generateChoiceDialogBox(DEFAULT_MODEL,
                modelOptions, "modelType", myLanguageResources.getString("ModelContent")));
        mainMenuButtonMap.put(myLanguageResources.getString(FILE_BUTTON_LABEL), event -> myFileManager.chooseFile());
        mainMenuButtonMap.put(myLanguageResources.getString(CHOOSE_COLOR_BUTTON_LABEL), event-> generateChoiceDialogBox(DEFAULT_CSS_FILE_LABEL,
                cssFileOptions, "cssFile", myLanguageResources.getString("ThemeContent")));
        mainMenuButtonMap.put(myLanguageResources.getString(NEW_SIM_BUTTON_LABEL), event ->
                myMainController.generateNewSimulation(myModelType, myFileManager.getCurrentTextFile()));

    }

    // want to add a way to update button label with choice so you know you have set it
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

    // use reflection to get rid of cases
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

