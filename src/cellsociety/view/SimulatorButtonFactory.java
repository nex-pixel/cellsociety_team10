package cellsociety.view;

import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.controller.SimulatorController;
import cellsociety.games.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SimulatorButtonFactory extends ButtonFactory {

    private SimulatorController mySimulatorController;
    private String myCSSFile;
    private Map<String, EventHandler<ActionEvent>> simulatorButtonMap = new LinkedHashMap<>();
    private String simulatorButtonID = "simulator-button";
    private ResourceBundle myLanguageResources;
    private ResourceBundle myActionEventsResources;
    private String INVALID_CSS_ERROR = "InvalidCSSFile";
    private Game myGame;
    private Scene myScene;

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
        System.out.println("hey");
        return event -> mySimulatorController.loadNewCSV();
    }

    @Override
    public Node generateButtonPanel(){
        HBox panel = new HBox();
        buttonMap.forEach((key,value) -> addButtonToPanel(key,value,panel));
        return panel;
    }

}
