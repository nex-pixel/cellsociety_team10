package cellsociety.view;

import cellsociety.controller.FileManager;
import cellsociety.controller.SimulatorController;
import cellsociety.controller.ViewController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuView {
    private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety/resources/";
    private static ResourceBundle myLanguageResources;
    private Stage window;
    private String cssFilePath;
    private cellsociety.view.SimulatorView simulation1;
    private Map<Integer[], Integer> sampleCellStatus; // for testing SimulatorView TODO: Delete.
    private SimulatorController mySimulatorController;
    private FileManager myFileManager;
    private ViewController myViewController;
    private Group homePageRoot;
    private final String DEFAULT_LANG = "English";
    private String[] languageOptions = {"English", "Spanish", "Gibberish"};
    private final String DEFAULT_MODEL = "Game of Life";
    private String[] modelOptions = {"Game of Life", "Spreading of Fire", "Schelling's", "Wa-Tor World", "Percolation"};


    // Constructor of MainMenuView
    public MainMenuView(String language, String cssFilePath){
        myLanguageResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        this.cssFilePath = cssFilePath;
        simulation1 = new SimulatorView(20, 20, Color.CORAL, Color.BEIGE,Color.BROWN);
        sampleCellStatus = new HashMap<>();// for testing SimulatorView TODO: Delete.
        sampleCellStatus.put(new Integer[] {2,1}, 1 );// for testing SimulatorView TODO: Delete.
        sampleCellStatus.put(new Integer[] {8,14}, 1 );// for testing SimulatorView TODO: Delete.
        sampleCellStatus.put(new Integer[] {16,9}, 0 );// for testing SimulatorView TODO: Delete.
        sampleCellStatus.put(new Integer[] {7,6}, 0 );// for testing SimulatorView TODO: Delete.

        mySimulatorController = new SimulatorController(simulation1, sampleCellStatus);
        myFileManager = new FileManager();
        myViewController = new ViewController();

    }

    /**
     * creates mainMenu and returns the scene
     * @param stage primary stage
     * @return scene of main menu
     */
    public Scene setMenuDisplay(Stage stage, int width, int height) {
        window = stage;
        Label titleLabel = new Label("Cell Society");
        titleLabel.setId("title");


        homePageRoot = new Group();
        homePageRoot.getChildren().add(generateMainMenuPanel());

        Scene scene = new Scene(homePageRoot, width, height);
        return scene;
        //return simulation1.getUpdatedGrid(sampleCellStatus, 200, 200);// for testing SimulatorView TODO: Delete.

    }

    // want to add a way to update button label with choice so you know you have set it
    // get rid of magic strings
    private Node generateMainMenuPanel(){
        VBox panel = new VBox();
        addButtonToPanel("Select a language", event -> generateChoiceDialogBox(DEFAULT_LANG, languageOptions, "language"), panel);
        addButtonToPanel("Select a type of simulation to run", event -> generateChoiceDialogBox(DEFAULT_MODEL, modelOptions, "modelType"), panel);
        addButtonToPanel("Load File", event -> myFileManager.chooseFile(), panel);
        addButtonToPanel("Create New Simulation", event -> mySimulatorController.createNewSimulation(window), panel);
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
            myViewController.updateLanguage(choiceDialog.getSelectedItem());
        }
        if(resultType.equals("modelType")){
            myViewController.updateModelType(choiceDialog.getSelectedItem());
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
        panel.getChildren().add(button);
    }

    private Button generateButton(String label, EventHandler<ActionEvent> event) {
        Button button = new Button();
        button.setText(label);
        button.setOnAction(event);
        return button;
    }


    // applies css file to the scene
    private void applyCSS(Scene scene) throws MalformedURLException {
        File styleFile = new File(cssFilePath);
        scene.getStylesheets().add(styleFile.toURI().toURL().toString());
    }
}
