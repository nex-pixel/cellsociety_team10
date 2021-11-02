package cellsociety.view.factories.buttonFactory;

import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.error.GenerateError;
import cellsociety.view.factories.choiceDialogBoxFactory.MainMenuChoiceDialogBoxFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * MainMenuButtonFactory generates buttons and delegates button actions for the Main Menu
 *
 * @author Ryleigh Byrne, Young Jun
 */
public class MainMenuButtonFactory extends ButtonFactory {
    private static final String ERROR_CHOOSE_ALL_OPTIONS = "SelectAllOptionsMessage";
    private static final String MAIN_BUTTON_ID = "main-menu-button";
    private static final String NEIGHBOR_LABEL_KEY = "neighborLabelOptions";
    private static final String EDGE_POLICY_LABEL_KEY = "edgePolicyLabelOptions";
    private static final String MODEL_LABEL_KEY = "modelLabelOptions";
    private static final String MAIN_MENU_CLASS_NAME = "MainMenuButtonFactory";
    private static final String GRID_LABEL_KEY = "gridLabelOptions";
    private static final String MODEL_TYPE = "modelType";
    private static final String GRID_TYPE = "gridType";
    private static final String NEIGHBOR_TYPE = "neighborModeType";
    private static final String EDGE_TYPE = "edgePolicyType";
    private static final String MAIN_MENU_EVENTS_PATH = "MainMenuActionEvents";

    private ArrayList<String> modelOptions = new ArrayList<>();
    private ArrayList<String> gridOptions = new ArrayList<>();
    private ArrayList<String> neighborOptions = new ArrayList<>();
    private ArrayList<String> edgePolicyOptions = new ArrayList<>();

    private FileManager myFileManager;
    private MainController myMainMenuController;
    private MainMenuChoiceDialogBoxFactory myMainMenuChoiceDialogBoxFactory;

    /**
     * Constructor for MainMenuButtonFactory. Initializes necessary paths
     * @param mainMenuController main menu controller to delegate actions
     * @param langResourceBundle current language resource bundle
     * @param fileManager file manager to delegate file handling
     */

    public MainMenuButtonFactory(MainController mainMenuController, ResourceBundle langResourceBundle, FileManager fileManager) {
        super();
        myActionEventsResources = ResourceBundle.getBundle(initializePaths(actionsNamePath, MAIN_MENU_EVENTS_PATH));
        buttonClassPath = initializePaths(buttonClassPath, MAIN_MENU_CLASS_NAME);
        myLanguageResources = langResourceBundle;
        myFileManager = fileManager;
        myMainMenuController = mainMenuController;
        myMainMenuChoiceDialogBoxFactory = new MainMenuChoiceDialogBoxFactory(myMainMenuController, myFileManager, myLanguageResources);
        buttonID = MAIN_BUTTON_ID;
        populateAllOptionArrays();
        populateButtonEvents();
    }

    /*
    populate buttonMap with button labels as keys and events as values. Events are extracted from
    resource bundle that holds all of main menu buttons method calls
     */
    @Override
    protected void populateButtonEvents() {
        ArrayList<String> list = Collections.list(myActionEventsResources.getKeys());
        Collections.sort(list);
        for (String key : list) {
            try {
                EventHandler<ActionEvent> buttonEvent = (EventHandler<ActionEvent>) myReflectionHandler.handleMethod(myActionEventsResources.getString(key), buttonClassPath).invoke(MainMenuButtonFactory.this);
                buttonMap.put(myLanguageResources.getString(key), buttonEvent);
            } catch (IllegalAccessException e){
                String error = String.format("You do not have access to the method: %s you tried to invoke. Please fix this error", myActionEventsResources.getString(key));
                System.out.println(error);
                new GenerateError(myLanguageResources, INVALID_BUTTON_GENERATION);
            }catch (InvocationTargetException e) {
                String error = String.format("The method: %s failed. Please fix this error", myActionEventsResources.getString(key));
                System.out.println(error);
                new GenerateError(myLanguageResources, INVALID_BUTTON_GENERATION);
            }
        }
    }

    // generate choice dialog box with appropriate options
    private EventHandler<ActionEvent> generateModelTypeEvent() {
        return event -> myMainMenuChoiceDialogBoxFactory.generateChoiceDialogBox(modelOptions.get(0),
                modelOptions, MODEL_TYPE, myLanguageResources.getString(MODEL_TYPE));
    }

    // display file chooser
    private EventHandler<ActionEvent> generateChooseFileEvent() {
        return event -> myFileManager.chooseFile();
    }

    // generate choice dialog box with appropriate options
    private EventHandler<ActionEvent> generateCSSFileEvent() {
        return event -> myMainMenuChoiceDialogBoxFactory.generateChoiceDialogBox(cssFileOptions.get(0),
                cssFileOptions, CSS_FILE, myLanguageResources.getString(CSS_FILE));
    }

    // generate choice dialog box with appropriate options
    private EventHandler<ActionEvent> generateGridTypeEvent() {
        return event -> myMainMenuChoiceDialogBoxFactory.generateChoiceDialogBox(gridOptions.get(0), gridOptions
                , GRID_TYPE, myLanguageResources.getString(GRID_TYPE));
    }

    // generate choice dialog box with appropriate options
    private EventHandler<ActionEvent> generateNeighborModeEvent() {
        return event -> myMainMenuChoiceDialogBoxFactory.generateChoiceDialogBox(neighborOptions.get(0), neighborOptions
                , NEIGHBOR_TYPE, myLanguageResources.getString(NEIGHBOR_TYPE));
    }

    // generate choice dialog box with appropriate options
    private EventHandler<ActionEvent> generateEdgePolicyEvent() {
        return event -> myMainMenuChoiceDialogBoxFactory.generateChoiceDialogBox(edgePolicyOptions.get(0), edgePolicyOptions
                , EDGE_TYPE, myLanguageResources.getString(EDGE_TYPE));
    }

    // generate choice dialog box with appropriate options
    protected EventHandler<ActionEvent> generateNewSimEvent() {
        return event -> {
            try {
                myMainMenuController.generateNewSimulation(myFileManager.getCurrentTextFile());
            } catch (Exception e) {
                new GenerateError(myLanguageResources, ERROR_CHOOSE_ALL_OPTIONS);
            }
        };
    }

    // populate all of the option arrays needed for the main menu
    private void populateAllOptionArrays(){
        populateOptions(modelOptions, labelOptionsBundle, MODEL_LABEL_KEY);
        populateOptions(cssFileOptions, labelOptionsBundle, CSS_FILE_LABEL_KEY);
        populateOptions(gridOptions, labelOptionsBundle, GRID_LABEL_KEY);
        populateOptions(neighborOptions, labelOptionsBundle, NEIGHBOR_LABEL_KEY);
        populateOptions(edgePolicyOptions, labelOptionsBundle, EDGE_POLICY_LABEL_KEY);
    }

}
