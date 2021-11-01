package cellsociety.view.factories.buttonFactory;

import cellsociety.ReflectionHandler;
import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.error.GenerateError;
import cellsociety.view.factories.choiceDialogBoxFactory.MainMenuChoiceDialogBoxFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class MainMenuButtonFactory extends ButtonFactory {
    private static final String ERROR_CHOOSE_ALL_OPTIONS = "SelectAllOptionsMessage";
    private ArrayList<String> modelOptions = new ArrayList<>();
    private ArrayList<String> gridOptions = new ArrayList<>();
    private ArrayList<String> neighborOptions = new ArrayList<>();
    private ArrayList<String> edgePolicyOptions = new ArrayList<>();
    private FileManager myFileManager;
    private MainController myMainMenuController;
    private MainMenuChoiceDialogBoxFactory myMainMenuChoiceDialogBoxFactory;
    private ReflectionHandler myReflectionHandler;
    private String neighborLabelKey = "neighborLabelOptions";
    private String edgePolicyLabelKey = "edgePolicyLabelOptions";


    public MainMenuButtonFactory(MainController mainMenuController, ResourceBundle langResourceBundle, FileManager fileManager) {
        super();
        ACTIONS_NAME_PATH += "MainMenuActionEvents";
        myActionEventsResources = ResourceBundle.getBundle(ACTIONS_NAME_PATH);
        myLanguageResources = langResourceBundle;
        myFileManager = fileManager;
        myMainMenuController = mainMenuController;
        myMainMenuChoiceDialogBoxFactory = new MainMenuChoiceDialogBoxFactory(myMainMenuController, myFileManager, myLanguageResources);
        buttonID = "main-menu-button";
        myReflectionHandler = new ReflectionHandler(myLanguageResources);
        populateOptions(modelOptions, labelOptionsBundle, modelLabelKey);
        populateOptions(cssFileOptions, labelOptionsBundle, cssFileLabelKey);
        populateOptions(gridOptions, labelOptionsBundle, gridLabelKey);
        populateOptions(neighborOptions, labelOptionsBundle, neighborLabelKey);
        populateOptions(edgePolicyOptions, labelOptionsBundle, edgePolicyLabelKey);
        populateButtonEvents();
    }

    @Override
    protected void populateButtonEvents() {
        try {
            ArrayList<String> list = Collections.list(myActionEventsResources.getKeys());
            Collections.sort(list);
            for (String key : list) {
                EventHandler<ActionEvent> buttonEvent = (EventHandler<ActionEvent>) myReflectionHandler.handleMethod(myActionEventsResources.getString(key), "cellsociety.view.factories.buttonFactory.MainMenuButtonFactory").invoke(MainMenuButtonFactory.this);
                buttonMap.put(myLanguageResources.getString(key), buttonEvent);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            new GenerateError(myLanguageResources, INVALID_BUTTON_GENERATION);
        }
    }

    private EventHandler<ActionEvent> generateModelTypeEvent() {
        return event -> myMainMenuChoiceDialogBoxFactory.generateChoiceDialogBox(modelOptions.get(0),
                modelOptions, "modelType", myLanguageResources.getString("ModelContent"));
    }

    private EventHandler<ActionEvent> generateChooseFileEvent() {
        return event -> myFileManager.chooseFile();
    }

    private EventHandler<ActionEvent> generateCSSFileEvent() {
        return event -> myMainMenuChoiceDialogBoxFactory.generateChoiceDialogBox(cssFileOptions.get(0),
                cssFileOptions, "cssFile", myLanguageResources.getString("ThemeContent"));
    }

    private EventHandler<ActionEvent> generateGridTypeEvent() {
        return event -> myMainMenuChoiceDialogBoxFactory.generateChoiceDialogBox(gridOptions.get(0), gridOptions
                , "gridType", "Cell Type"); // TODO: update language resources
    }

    private EventHandler<ActionEvent> generateNeighborModeEvent() {
        return event -> myMainMenuChoiceDialogBoxFactory.generateChoiceDialogBox(neighborOptions.get(0), neighborOptions
                , "neighborModeType", "Neighbor Mode"); // TODO: update language resources
    }

    private EventHandler<ActionEvent> generateEdgePolicyEvent() {
        return event -> myMainMenuChoiceDialogBoxFactory.generateChoiceDialogBox(edgePolicyOptions.get(0), edgePolicyOptions
                , "EdgePolicyType", "Edge Policy"); // TODO: update language resources
    }

    protected EventHandler<ActionEvent> generateNewSimEvent() {
        return event -> {
            try {
                myMainMenuController.generateNewSimulation(myFileManager.getCurrentTextFile());
            } catch (Exception e) {
                new GenerateError(myLanguageResources, ERROR_CHOOSE_ALL_OPTIONS);
            }
        };
    }


}
