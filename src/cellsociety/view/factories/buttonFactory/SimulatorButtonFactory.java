package cellsociety.view.factories.buttonFactory;

import cellsociety.controller.SimulatorController;
import cellsociety.error.GenerateError;
import cellsociety.view.SimulatorView;
import cellsociety.view.factories.choiceDialogBoxFactory.SimulatorChoiceDialogBoxFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * SimulatorButtonFactory generates buttons and delegates button actions for the simulator view
 *
 * @author Ryleigh Byrne, Young Jun
 */
public class SimulatorButtonFactory extends ButtonFactory {
    private static final String SIMULATOR_ACTION_EVENTS = "SimulatorActionEvents";
    private static final String SIMULATOR_BUTTON_ID = "simulator-button";

    private SimulatorController mySimulatorController;
    private SimulatorView mySimulatorView;
    private SimulatorChoiceDialogBoxFactory mySimulatorChoiceDialogBoxFactory;
    private String simulatorButtonClassName = "SimulatorButtonFactory";

    /**
     * Constructor for simulator button factory. Initializes necessary paths
     * @param simulatorView SimulatorView that buttons will be displayed on
     * @param simulatorController SimulatorController that delegates actions based on buttons
     * @param langResourceBundle language resource bundle currently being used
     */
    public SimulatorButtonFactory(SimulatorView simulatorView, SimulatorController simulatorController, ResourceBundle langResourceBundle) {
        super();
        myActionEventsResources = ResourceBundle.getBundle(initializePaths(actionsNamePath, SIMULATOR_ACTION_EVENTS));
        buttonClassPath = initializePaths(buttonClassPath, simulatorButtonClassName);
        buttonID = SIMULATOR_BUTTON_ID;
        mySimulatorView = simulatorView;
        mySimulatorController = simulatorController;
        myLanguageResources = langResourceBundle;

        mySimulatorChoiceDialogBoxFactory = new SimulatorChoiceDialogBoxFactory(mySimulatorController, myLanguageResources);

        populateOptions(cssFileOptions, labelOptionsBundle, CSS_FILE_LABEL_KEY);
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
                EventHandler<ActionEvent> buttonEvent = (EventHandler<ActionEvent>) myReflectionHandler.handleMethod(myActionEventsResources.getString(key), buttonClassPath).invoke(SimulatorButtonFactory.this);
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

    // play simulation
    private EventHandler<ActionEvent> generatePlayEvent() {
        return event -> mySimulatorView.play();
    }

    // pause simulation
    private EventHandler<ActionEvent> generatePauseEvent() {
        return event -> mySimulatorView.pause();
    }

    // step through simulation
    private EventHandler<ActionEvent> generateStepEvent() {
        return event -> mySimulatorView.step();
    }

    // save current simulation
    private EventHandler<ActionEvent> generateSaveEvent() {
        return event -> mySimulatorController.saveCSVFile();
    }

    // load a new simulation
    private EventHandler<ActionEvent> generateLoadEvent() {
        return event -> mySimulatorController.loadNewCSV();
    }

    // replace current simulation
    private EventHandler<ActionEvent> generateReplaceEvent() {
        return event -> mySimulatorController.replaceWithNewCSV();
    }

    // display about simulation
    private EventHandler<ActionEvent> generateAboutEvent() {
        return event -> mySimulatorView.displaySimulationInfo();
    }

    // generate choice dialog box with appropriate options
    private EventHandler<ActionEvent> generateNewCSSEvent() {
        return event -> mySimulatorChoiceDialogBoxFactory.generateChoiceDialogBox(cssFileOptions.get(0),
                cssFileOptions, CSS_FILE, myLanguageResources.getString(CSS_FILE));
    }


}
