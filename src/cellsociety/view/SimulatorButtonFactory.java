package cellsociety.view;


import cellsociety.controller.SimulatorController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SimulatorButtonFactory extends ButtonFactory {

    private SimulatorController mySimulatorController;
    private ResourceBundle myLanguageResources;
    private ResourceBundle myActionEventsResources;

    private SimulatorView mySimulatorView;

    public SimulatorButtonFactory(SimulatorView simulatorView, SimulatorController simulatorController, ResourceBundle langResourceBundle, ResourceBundle actionResourceBundle){
        super();
        mySimulatorView = simulatorView;
        mySimulatorController = simulatorController;
        myLanguageResources = langResourceBundle;
        myActionEventsResources = actionResourceBundle;
        buttonID = "simulator-button";
        populateButtonEvents();
    }

    @Override
    protected void populateButtonEvents(){
        try{
            ArrayList<String> list = Collections.list(myActionEventsResources.getKeys());
            Collections.sort(list);
            for(String key : list){
                EventHandler<ActionEvent> buttonEvent = (EventHandler<ActionEvent>) handleMethod(myActionEventsResources.getString(key)).invoke(SimulatorButtonFactory.this);
                buttonMap.put(myLanguageResources.getString(key), buttonEvent);
            }
        }catch(IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
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

    private EventHandler<ActionEvent> generateAddSimEvent(){
        return event -> mySimulatorController.loadNewCSV();
    }

    @Override
    public Node generateButtonPanel(){
        HBox panel = new HBox();
        buttonMap.forEach((key,value) -> addButtonToPanel(key,value,panel));
        return panel;
    }

}
