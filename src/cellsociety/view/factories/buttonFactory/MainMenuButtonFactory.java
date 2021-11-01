package cellsociety.view.factories.buttonFactory;

import cellsociety.ReflectionHandler;
import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.error.GenerateError;
import cellsociety.view.MainMenuView;
import cellsociety.view.factories.choiceDialogBoxFactory.MainMenuChoiceDialogBoxFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class MainMenuButtonFactory extends ButtonFactory {

    private static final String ERROR_CHOOSE_ALL_OPTIONS = "SelectAllOptionsMessage";
    private ArrayList<String> modelOptions = new ArrayList<>();
    private String[] modelLabelOptions = {"GameOfLife", "SpreadingOfFire", "Schelling's", "Wa-TorWorld", "Percolation"};
    private static final String[] GRID_TYPES = {"0.Square Cell", "1.Triangle Cell", "2. Hexagon Cell"};
    private static final String[] NEIGHBOR_MODE = {"0.Complete", "1.Edge", "2. Bottom Half"};
    private static final String[] EDGE_POLICY = {"0.Finite", "1. Torus"};
    private FileManager myFileManager;
    private MainController myMainMenuController;
    private MainMenuChoiceDialogBoxFactory myMainMenuChoiceDialogBoxFactory;
    private ReflectionHandler myReflectionHandler;

    public MainMenuButtonFactory(MainController mainMenuController, ResourceBundle langResourceBundle, FileManager fileManager){
        super();
        ACTIONS_NAME_PATH += "MainMenuActionEvents";
        myActionEventsResources = ResourceBundle.getBundle(ACTIONS_NAME_PATH);
        myLanguageResources = langResourceBundle;
        myFileManager = fileManager;
        myMainMenuController = mainMenuController;
        myMainMenuChoiceDialogBoxFactory = new MainMenuChoiceDialogBoxFactory(myMainMenuController, myFileManager, myLanguageResources);
        buttonID = "main-menu-button";
        myReflectionHandler = new ReflectionHandler(myLanguageResources);
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
                EventHandler<ActionEvent> buttonEvent = (EventHandler<ActionEvent>) myReflectionHandler.handleMethod(myActionEventsResources.getString(key), "cellsociety.view.factories.buttonFactory.MainMenuButtonFactory").invoke(MainMenuButtonFactory.this);
                buttonMap.put(myLanguageResources.getString(key), buttonEvent);
            }
        }catch(IllegalAccessException | InvocationTargetException e){
            new GenerateError(myLanguageResources.getString(LANG_KEY), INVALID_BUTTON_GENERATION);
        }
    }

    private EventHandler<ActionEvent> generateModelTypeEvent(){
        return event -> myMainMenuChoiceDialogBoxFactory.generateChoiceDialogBox(myLanguageResources.getString(modelLabelOptions[0]),
                modelOptions, "modelType", myLanguageResources.getString("ModelContent"));
    }

    private EventHandler<ActionEvent> generateChooseFileEvent(){
        return event -> myFileManager.chooseFile();
    }

    private EventHandler<ActionEvent> generateCSSFileEvent(){
        return event-> myMainMenuChoiceDialogBoxFactory.generateChoiceDialogBox(myLanguageResources.getString(cssFileLabelOptions[0]),
                cssFileOptions, "cssFile", myLanguageResources.getString("ThemeContent"));
    }

    private EventHandler<ActionEvent> generateGridTypeEvent(){
        return event -> myMainMenuChoiceDialogBoxFactory.generateChoiceDialogBox(GRID_TYPES[0],new ArrayList<>(Arrays.asList(GRID_TYPES))
                , "gridType", "Cell Type"); // TODO: update language resources
    }

    private EventHandler<ActionEvent> generateNeighborModeEvent(){
        return event -> myMainMenuChoiceDialogBoxFactory.generateChoiceDialogBox(NEIGHBOR_MODE[0],new ArrayList<>(Arrays.asList(NEIGHBOR_MODE))
                , "neighborModeType", "Neighbor Mode"); // TODO: update language resources
    }

    private EventHandler<ActionEvent> generateEdgePolicyEvent(){
        return event -> myMainMenuChoiceDialogBoxFactory.generateChoiceDialogBox(EDGE_POLICY[0],new ArrayList<>(Arrays.asList(EDGE_POLICY))
                , "EdgePolicyType", "Edge Policy"); // TODO: update language resources
    }

    protected EventHandler<ActionEvent> generateNewSimEvent(){
        return event -> {
            try {
                myMainMenuController.generateNewSimulation(myFileManager.getCurrentTextFile());
            } catch (Exception e) {
                new GenerateError(myLanguageResources.getString(LANG_KEY), ERROR_CHOOSE_ALL_OPTIONS);
            }
        };
    }


}
