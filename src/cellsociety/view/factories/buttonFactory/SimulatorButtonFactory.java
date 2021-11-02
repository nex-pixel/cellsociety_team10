package cellsociety.view.factories.buttonFactory;

import cellsociety.ReflectionHandler;
import cellsociety.controller.SimulatorController;
import cellsociety.error.GenerateError;
import cellsociety.view.SimulatorView;
import cellsociety.view.factories.choiceDialogBoxFactory.SimulatorChoiceDialogBoxFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SimulatorButtonFactory extends ButtonFactory {
    private static final String SIMULATOR_ACTION_EVENTS = "SimulatorActionEvents";
    private static final String SIMULATOR_BUTTON_ID = "simulator-button";

    private SimulatorController mySimulatorController;
    private SimulatorView mySimulatorView;
    private SimulatorChoiceDialogBoxFactory mySimulatorChoiceDialogBoxFactory;
    private String simulatorButtonClassName = "SimulatorButtonFactory";

    public SimulatorButtonFactory(SimulatorView simulatorView, SimulatorController simulatorController, ResourceBundle langResourceBundle) {
        super();
        ACTIONS_NAME_PATH += SIMULATOR_ACTION_EVENTS;
        myActionEventsResources = ResourceBundle.getBundle(ACTIONS_NAME_PATH);
        buttonClassPath += simulatorButtonClassName;
        buttonID = SIMULATOR_BUTTON_ID;
        mySimulatorView = simulatorView;
        mySimulatorController = simulatorController;
        myLanguageResources = langResourceBundle;

        mySimulatorChoiceDialogBoxFactory = new SimulatorChoiceDialogBoxFactory(mySimulatorController, myLanguageResources);

        populateOptions(cssFileOptions, labelOptionsBundle, CSS_FILE_LABEL_KEY);
        populateButtonEvents();
    }

    @Override
    protected void populateButtonEvents() {
        try {
            ArrayList<String> list = Collections.list(myActionEventsResources.getKeys());
            Collections.sort(list);
            for (String key : list) {
                EventHandler<ActionEvent> buttonEvent = (EventHandler<ActionEvent>) myReflectionHandler.handleMethod(myActionEventsResources.getString(key), buttonClassPath).invoke(SimulatorButtonFactory.this);
                buttonMap.put(myLanguageResources.getString(key), buttonEvent);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            new GenerateError(myLanguageResources, INVALID_BUTTON_GENERATION);
        }
    }

    private EventHandler<ActionEvent> generatePlayEvent() {
        return event -> mySimulatorView.play();
    }

    private EventHandler<ActionEvent> generatePauseEvent() {
        return event -> mySimulatorView.pause();
    }

    private EventHandler<ActionEvent> generateStepEvent() {
        return event -> mySimulatorView.step();
    }

    private EventHandler<ActionEvent> generateSaveEvent() {
        return event -> mySimulatorController.saveCSVFile();
    }

    private EventHandler<ActionEvent> generateLoadEvent() {
        return event -> mySimulatorController.loadNewCSV();
    }

    private EventHandler<ActionEvent> generateReplaceEvent() {
        return event -> mySimulatorController.replaceWithNewCSV();
    }

    private EventHandler<ActionEvent> generateAboutEvent() {
        return event -> mySimulatorView.displaySimulationInfo();
    }

    private EventHandler<ActionEvent> generateNewCSSEvent() {
        return event -> mySimulatorChoiceDialogBoxFactory.generateChoiceDialogBox(cssFileOptions.get(0),
                cssFileOptions, CSS_FILE, myLanguageResources.getString(CSS_FILE));
    }


}
