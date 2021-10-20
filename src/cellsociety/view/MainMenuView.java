package cellsociety.view;

import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.controller.SimulatorController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainMenuView {
    private static final String DEFAULT_RESOURCE_PATH = "cellsociety/resources/";
    private static ResourceBundle myLanguageResources;
    private Stage window;
    private Map<Integer[], Integer> sampleCellStatus; // for testing SimulatorView TODO: Delete.
    private SimulatorController mySimulatorController;
    private FileManager myFileManager;
    private Pane homePageRoot;
    private MainController myMainController;
    private final String DEFAULT_LANG = "English";
    private String[] languageOptions = {"English", "Spanish", "Gibberish"};
    private final String DEFAULT_MODEL = "Game of Life";
    private String[] modelOptions = {"Game of Life", "Spreading of Fire", "Schelling's", "Wa-Tor World", "Percolation"};
    private Map<String, EventHandler<ActionEvent>> mainMenuButtonMap = new LinkedHashMap<String, EventHandler<ActionEvent>>();
    private String LANG_BUTTON_LABEL = "ChooseLanguageLabel";
    private String SIM_TYPE_BUTTON_LABEL = "ChooseSimulationTypeLabel";
    private String FILE_BUTTON_LABEL = "LoadFileLabel";
    private String NEW_SIM_BUTTON_LABEL = "CreateNewSimulationLabel";
    private String CHOOSE_COLOR_BUTTON_LABEL = "ChooseColorScheme";
    private String DEFAULT_CSS_FILE = "src/cellsociety/resources/GameStyleSheet.css";
    private String currentCSSFile;
    private String[] cssFileOptions = {"Light", "Dark", "Duke", "UNC (ew)"};

    // Constructor of MainMenuView
    public MainMenuView(){
        mySimulatorController = new SimulatorController(500, 500, Color.CORAL,
                Color.BEIGE, Color.BROWN);
        currentCSSFile = DEFAULT_CSS_FILE;
        myFileManager = new FileManager();
        populateMainMenuButtonMap();
    }

    /**
     * creates mainMenu and returns the scene
     * @param stage primary stage
     * @return scene of main menu
     */
    public Scene setMenuDisplay(Stage stage, MainController mainController, int width, int height) throws MalformedURLException {
        myMainController = mainController;
        myLanguageResources = myMainController.getResourceBundle();
        window = stage;
        Label titleLabel = new Label("Cell Society");
        titleLabel.setId("title");

        homePageRoot = new Pane();
        homePageRoot.getChildren().add(generateMainMenuPanel());
        homePageRoot.setId("home-page-root");

        Scene scene =  new Scene(homePageRoot, width, height);
        return scene;
    }

    // maybe clean this up
    private void populateMainMenuButtonMap(){
        mainMenuButtonMap.put(myLanguageResources.getString(LANG_BUTTON_LABEL),event -> generateChoiceDialogBox(DEFAULT_LANG, languageOptions,
                "language"));
        mainMenuButtonMap.put(myLanguageResources.getString(SIM_TYPE_BUTTON_LABEL), event -> generateChoiceDialogBox(DEFAULT_MODEL,
                modelOptions, "modelType"));
        mainMenuButtonMap.put(myLanguageResources.getString(FILE_BUTTON_LABEL), event -> myFileManager.chooseFile());
        mainMenuButtonMap.put(myLanguageResources.getString(NEW_SIM_BUTTON_LABEL), event -> mySimulatorController.createNewSimulation(window,
                myFileManager.getCurrentTextFile()));
        mainMenuButtonMap.put(myLanguageResources.getString(CHOOSE_COLOR_BUTTON_LABEL), event-> generateChoiceDialogBox(DEFAULT_CSS_FILE,
                cssFileOptions, "cssFile"));

    }

    // want to add a way to update button label with choice so you know you have set it
    private Node generateMainMenuPanel(){
        VBox panel = new VBox();
        mainMenuButtonMap.forEach((key,value) -> addButtonToPanel(key,value,panel));
        return panel;
    }

    private ChoiceDialog<String> generateChoiceDialogBox(String defaultChoice, String[] options, String resultType){
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(defaultChoice);
        addItemsToOptionsList(options, choiceDialog);
        showAndWaitForChoiceDialogResult(choiceDialog, resultType);
        return choiceDialog;
    }

    // use reflection to get rid of cases
    private void showAndWaitForChoiceDialogResult(ChoiceDialog<String> choiceDialog, String resultType){
        choiceDialog.showAndWait();

        if(resultType.equals("language")){
            myMainController.updateLanguage(choiceDialog.getSelectedItem());
        }
        if(resultType.equals("modelType")){
            myMainController.updateModelType(choiceDialog.getSelectedItem());
        }
        if(resultType.equals("cssFile")){
            myMainController.updateCSS(choiceDialog.getSelectedItem());
        }
    }

    private void addItemsToOptionsList(String[] options, ChoiceDialog<String> choiceDialog){
        for(String s : options){
            choiceDialog.getItems().add(s);
        }
    }

    private void addButtonToPanel(String label, EventHandler<ActionEvent> event, VBox panel){
        Button button = generateButton(label,
                event);
        button.setId("main-menu-button");
        panel.getChildren().add(button);
    }

    private Button generateButton(String label, EventHandler<ActionEvent> event) {
        Button button = new Button();
        button.setText(label);
        button.setOnAction(event);
        return button;
    }

    public void applyCSS(Scene scene) {
        try{
            File styleFile = new File(currentCSSFile);
            scene.getStylesheets().add(styleFile.toURI().toURL().toString());
        }catch(Exception e){

        }

    }

}
