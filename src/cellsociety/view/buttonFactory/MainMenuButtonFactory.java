package cellsociety.view.buttonFactory;

import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.error.GenerateError;
import cellsociety.view.MainMenuView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class MainMenuButtonFactory extends ButtonFactory {

    private static final String ERROR_CHOOSE_ALL_OPTIONS = "SelectAllOptionsMessage";
    private ArrayList<String> modelOptions = new ArrayList<>();;
    private String[] cssFileLabelOptions = {"DukeLabel", "UNCLabel", "LightLabel", "DarkLabel"};
    private String[] modelLabelOptions = {"GameOfLife", "SpreadingOfFire", "Schelling's", "Wa-TorWorld", "Percolation"};
    private ArrayList<String> cssFileOptions = new ArrayList<>();
    private FileManager myFileManager;
    private MainMenuView myMainMenuView;
    private MainController myMainMenuController;

    public MainMenuButtonFactory(MainMenuView menuView, MainController mainMenuController, ResourceBundle langResourceBundle, ResourceBundle actionResourceBundle, FileManager fileManager){
        super();
        ACTIONS_NAME_PATH += "MainMenuActionEvents.properties";
        myMainMenuView = menuView;
        myLanguageResources = langResourceBundle;
        myActionEventsResources = actionResourceBundle;
        myFileManager = fileManager;
        myMainMenuController = mainMenuController;
        buttonID = "main-menu-button";
        populateOptions(modelOptions, modelLabelOptions);
        populateOptions(cssFileOptions, cssFileLabelOptions);
        populateButtonEvents();
    }

    public MainMenuButtonFactory(ResourceBundle langResourceBundle){
        myActionEventsResources = ResourceBundle.getBundle(ACTIONS_NAME_PATH);
        myLanguageResources = langResourceBundle;
        myMainMenuView = new MainMenuView(langResourceBundle, myActionEventsResources);
        myMainMenuController = new MainController(langResourceBundle);
        myFileManager = new FileManager(langResourceBundle);
        buttonID = "main-menu-button";
        populateOptions(modelOptions, modelLabelOptions);
        populateOptions(cssFileOptions, cssFileLabelOptions);
        populateButtonEvents();
    }

    @Override
    protected void populateButtonEvents(){
        try{
            ArrayList<String> list = Collections.list(myActionEventsResources.getKeys());
            Collections.sort(list);
            for(String key : list){
                EventHandler<ActionEvent> buttonEvent = (EventHandler<ActionEvent>) handleMethod(myActionEventsResources.getString(key)).invoke(MainMenuButtonFactory.this);
                buttonMap.put(myLanguageResources.getString(key), buttonEvent);
            }
        }catch(IllegalAccessException | InvocationTargetException e){
            new GenerateError(myLanguageResources, INVALID_BUTTON_GENERATION);
        }
    }

    private EventHandler<ActionEvent> generateModelTypeEvent(){
        return event -> myMainMenuView.generateChoiceDialogBox(myLanguageResources.getString(modelLabelOptions[0]),
                modelOptions, "modelType", myLanguageResources.getString("ModelContent"));
    }

    private EventHandler<ActionEvent> generateChooseFileEvent(){
        return event -> myFileManager.chooseFile();
    }

    private EventHandler<ActionEvent> generateCSSFileEvent(){
        return event-> myMainMenuView.generateChoiceDialogBox(myLanguageResources.getString(cssFileLabelOptions[0]),
                cssFileOptions, "cssFile", myLanguageResources.getString("ThemeContent"));
    }

    protected EventHandler<ActionEvent> generateNewSimEvent(){
        return event -> {
            try {
                myMainMenuController.generateNewSimulation(myFileManager.getCurrentTextFile());
            } catch (Exception e) {
                new GenerateError(myLanguageResources, ERROR_CHOOSE_ALL_OPTIONS);
            }
        };
    }

    private void populateOptions(ArrayList<String> optionsList, String[] labelList){
        for(String key: labelList){
            optionsList.add(myLanguageResources.getString(key));
        }
    }

}
