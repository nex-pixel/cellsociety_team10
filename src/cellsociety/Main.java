package cellsociety;


import cellsociety.controller.MainController;
import cellsociety.view.MainMenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {
    private static final String RESOURCE_PACKAGE = "cellsociety.resources.languages.";
    private String DEFAULT_LANGUAGE = "English";
    public static final String PROGRAM_TITLE = "Cell Society";
    private ArrayList<String> languageOptions = new ArrayList<>();
    private String language;
    private String dialogContent = "Please select a language";
    private String DEFAULT_LANG = "English";
    private ResourceBundle myLanguageResources;
    /**
     * A method to test (and a joke :).
     */
    public double getVersion () {
        return 0.001;
    }

    /**
     * Start of the program. Starts with dialog box asking user to select a language
     */
    @Override
    public void start(Stage primaryStage) {
        populateLanguageOptions();
        generateChoiceDialogBox(DEFAULT_LANG, languageOptions, dialogContent);
        myLanguageResources = initializeResourceBundle(language);
        primaryStage.setTitle(PROGRAM_TITLE);
        MainController mainController = new MainController(primaryStage, myLanguageResources);
        mainController.startMainMenu();
    }

    private void populateLanguageOptions(){
        languageOptions.add("English");
        languageOptions.add("Spanish");
        languageOptions.add("Korean");
    }

    private ChoiceDialog<String> generateChoiceDialogBox(String defaultChoice, ArrayList<String> options, String content){
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(defaultChoice);
        choiceDialog.setContentText(content);
        addItemsToOptionsList(options, choiceDialog);
        showAndWaitForChoiceDialogResult(choiceDialog);
        return choiceDialog;
    }

    private void showAndWaitForChoiceDialogResult(ChoiceDialog<String> choiceDialog){
        choiceDialog.showAndWait();
        language = choiceDialog.getSelectedItem();
    }

    private void addItemsToOptionsList(ArrayList<String> options, ChoiceDialog<String> choiceDialog){
        for(String s : options){
            choiceDialog.getItems().add(s);
        }
    }

    private ResourceBundle initializeResourceBundle(String name) {
        try {
            return generateResourceBundle(name);
        } catch (Exception e) {
            return generateResourceBundle(DEFAULT_LANGUAGE);
        }
    }

    private ResourceBundle generateResourceBundle(String name) {
        return ResourceBundle.getBundle(RESOURCE_PACKAGE + name);
    }
}

