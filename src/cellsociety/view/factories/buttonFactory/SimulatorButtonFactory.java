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

    private SimulatorController mySimulatorController;
    private SimulatorView mySimulatorView;
    private String simulatorActionEventsPath = "SimulatorActionEvents";
    private String simulatorButtonID = "simulator-button";
    private SimulatorChoiceDialogBoxFactory mySimulatorChoiceDialogBoxFactory;
    private ReflectionHandler myReflectionHandler;

    public SimulatorButtonFactory(SimulatorView simulatorView, SimulatorController simulatorController, ResourceBundle langResourceBundle){
        super();
        ACTIONS_NAME_PATH += simulatorActionEventsPath;
        myActionEventsResources = ResourceBundle.getBundle(ACTIONS_NAME_PATH);
        mySimulatorView = simulatorView;
        mySimulatorController = simulatorController;
        myLanguageResources = langResourceBundle;
        buttonID = simulatorButtonID;
        mySimulatorChoiceDialogBoxFactory = new SimulatorChoiceDialogBoxFactory(mySimulatorController, myLanguageResources);
        myReflectionHandler = new ReflectionHandler(myLanguageResources);
        populateOptions(cssFileOptions, labelOptionsBundle, cssFileLabelKey);
        populateButtonEvents();
    }

    @Override
    protected void populateButtonEvents(){
        try{
            ArrayList<String> list = Collections.list(myActionEventsResources.getKeys());
            Collections.sort(list);
            for(String key : list){
                EventHandler<ActionEvent> buttonEvent = (EventHandler<ActionEvent>) myReflectionHandler.handleMethod(myActionEventsResources.getString(key), "cellsociety.view.factories.buttonFactory.SimulatorButtonFactory").invoke(SimulatorButtonFactory.this);
                buttonMap.put(myLanguageResources.getString(key), buttonEvent);
            }
        }catch(IllegalAccessException | InvocationTargetException e){
            new GenerateError(myLanguageResources, INVALID_BUTTON_GENERATION);
        }
    }

    private EventHandler<ActionEvent> generatePlayEvent(){
        return event -> mySimulatorView.play();
    }

    private EventHandler<ActionEvent> generatePauseEvent(){
        return event -> mySimulatorView.pause();
    }

    private EventHandler<ActionEvent> generateStepEvent(){
        return event -> mySimulatorView.step();
    }

    private EventHandler<ActionEvent> generateSaveEvent(){
        return event -> mySimulatorController.saveCSVFile();
    }

    private EventHandler<ActionEvent> generateLoadEvent(){
        return event -> mySimulatorController.loadNewCSV();
    }

    private EventHandler<ActionEvent> generateReplaceEvent(){return event -> mySimulatorController.replaceWithNewCSV();}

    private EventHandler<ActionEvent> generateAboutEvent() {return event -> {
        mySimulatorView.displaySimulationInfo();
    };
    }

    private EventHandler<ActionEvent> generateNewCSSEvent(){ return event -> mySimulatorChoiceDialogBoxFactory.generateChoiceDialogBox(cssFileOptions.get(0),
            cssFileOptions, "cssFile", myLanguageResources.getString("ThemeContent"));}


}
