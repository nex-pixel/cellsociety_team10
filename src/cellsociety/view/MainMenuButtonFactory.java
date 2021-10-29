package cellsociety.view;

import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.error.GenerateError;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class MainMenuButtonFactory extends ButtonFactory {
    private static final String ERROR_CHOOSE_ALL_OPTIONS = "SelectAllOptionsMessage";
    private ArrayList<String> modelOptions = new ArrayList<>();;
    private Map<String, EventHandler<ActionEvent>> mainMenuButtonMap = new LinkedHashMap<>();
    private String[] buttonLabelOptions = {"ChooseSimulationTypeLabel", "LoadFileLabel", "ChooseColorScheme", "CreateNewSimulationLabel"};
    private ArrayList<EventHandler<ActionEvent>> buttonEventLists = new ArrayList<>();
    private String[] cssFileLabelOptions = {"DukeLabel", "UNCLabel", "LightLabel", "DarkLabel"};
    private String[] modelLabelOptions = {"GameOfLife", "SpreadingOfFire", "Schelling's", "Wa-TorWorld", "Percolation"};
    private ResourceBundle myActionEventsResources;
    private ArrayList<String> cssFileOptions = new ArrayList<>();
    private ResourceBundle myLanguageResources;
    private FileManager myFileManager;
    private MainMenuView myMainMenuView;
    private MainController myMainMenuController;

    public MainMenuButtonFactory(MainMenuView menuView, MainController mainMenuController, ResourceBundle langResourceBundle, ResourceBundle actionResourceBundle, FileManager fileManager){
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

    @Override
    protected void populateButtonEvents(){
        try{
            for(String key : buttonLabelOptions){
                EventHandler<ActionEvent> buttonEvent = (EventHandler<ActionEvent>) handleMethod(myActionEventsResources.getString(key)).invoke(MainMenuButtonFactory.this);
                mainMenuButtonMap.put(myLanguageResources.getString(key), buttonEvent);
            }
        }catch(IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
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

    private EventHandler<ActionEvent> generateNewSimEvent(){
        return event -> {
            try {
                myMainMenuController.generateNewSimulation(myFileManager.getCurrentTextFile());
            } catch (Exception e) {
                new GenerateError(myLanguageResources, ERROR_CHOOSE_ALL_OPTIONS);
            }
        };
    }

    @Override
    protected Method handleMethod(String name) {
        try{
            Method m = MainMenuButtonFactory.class.getDeclaredMethod(name);
            return m;
        }catch(NoSuchMethodException e){
            //TODO: FIX THIS
            e.printStackTrace();
        }
        //TODO: BAD
        return null;
    }

    public Node generateMainMenuPanel(){
        VBox panel = new VBox();
        mainMenuButtonMap.forEach((key,value) -> addButtonToPanel(key,value,panel));
        return panel;
    }

    private void populateOptions(ArrayList<String> optionsList, String[] labelList){
        for(String key: labelList){
            optionsList.add(myLanguageResources.getString(key));
        }
    }

}
