package cellsociety.view.buttonFactory;

import cellsociety.controller.SimulatorController;
import cellsociety.error.Error;
import cellsociety.error.GenerateError;
import cellsociety.games.Game;
import cellsociety.view.SimulatorView;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SimulatorButtonFactory extends ButtonFactory {

    private SimulatorController mySimulatorController;
    private SimulatorView mySimulatorView;
    private String InvalidSimFile = "simFileNotFound";

    public SimulatorButtonFactory(SimulatorView simulatorView, SimulatorController simulatorController, ResourceBundle langResourceBundle){
        super();
        ACTIONS_NAME_PATH += "SimulatorActionEvents";
        myActionEventsResources = ResourceBundle.getBundle(ACTIONS_NAME_PATH);
        mySimulatorView = simulatorView;
        mySimulatorController = simulatorController;
        myLanguageResources = langResourceBundle;
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
            new GenerateError(myLanguageResources.getString(LANG_KEY), INVALID_BUTTON_GENERATION);
        }
    }


    private void setSliderProperties(Slider lengthSlider){
        lengthSlider.setShowTickMarks(true);
        lengthSlider.setShowTickLabels(true);
        lengthSlider.setMajorTickUnit(1);
        lengthSlider.setMaxWidth(100);
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
        return event -> mySimulatorView.saveCSVFile();
    }

    private EventHandler<ActionEvent> generateLoadEvent(){
        return event -> mySimulatorController.loadNewCSV();
    }

    private EventHandler<ActionEvent> generateReplaceEvent(){return event -> mySimulatorController.replaceWithNewCSV();}

    private EventHandler<ActionEvent> generateAboutEvent() {return event -> {
        try {
            mySimulatorView.showAbout();
        } catch (FileNotFoundException e) {
            new GenerateError(myLanguageResources.getString(LANG_KEY), InvalidSimFile);
        }};
    }


}
