package cellsociety.view;

import cellsociety.controller.SimulatorController;
import cellsociety.games.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SimulatorButtonView {

    private SimulatorController mySimulatorController;
    private String myCSSFile;
    private Map<String, EventHandler<ActionEvent>> simulatorButtonMap = new LinkedHashMap<>();
    private String simulatorButtonID = "simulator-button";
    private ResourceBundle myLanguageResources;
    private String PLAY_LABEL = "PlayLabel";
    private String PAUSE_LABEL = "PauseLabel";
    private String STEP_LABEL = "StepLabel";
    private String SAVE_LABEL = "SaveLabel";
    private String LOAD_LABEL = "LoadLabel";
    private String ADD_SIM_LABEL = "AddLabel";
    private String INVALID_CSS_ERROR = "InvalidCSSFile";
    private Game myGame;
    private Scene myScene;

    private SimulatorView mySimulatorView;

    public SimulatorButtonView(SimulatorView simulatorView, SimulatorController simulatorController){
        mySimulatorView = simulatorView;
        mySimulatorController = simulatorController;
    }

  private void populateSimulatorButtonMap() {
    /*
        simulatorButtonMap.put(myLanguageResources.getString(PAUSE_LABEL), event -> mySimulatorView.pause());
        simulatorButtonMap.put(myLanguageResources.getString(PLAY_LABEL), event -> mySimulatorView.play());
        simulatorButtonMap.put(myLanguageResources.getString(STEP_LABEL), event -> mySimulatorView.step());
        simulatorButtonMap.put(myLanguageResources.getString(SAVE_LABEL), event -> mySimulatorController.saveCSVFile());
        simulatorButtonMap.put(myLanguageResources.getString(LOAD_LABEL), event -> mySimulatorController.loadNewCSV());

    }
         */
  }

    public HBox generateSimulatorButtonBox(){
        HBox buttonBox = new HBox();
        simulatorButtonMap.forEach((key,value) -> addButtonToPanel(key,value,buttonBox));
        return buttonBox;
    }

    private void addButtonToPanel(String label, EventHandler<ActionEvent> event, HBox panel){
        Button button = generateButton(label,
                event);
        button.setId(simulatorButtonID);
        panel.getChildren().add(button);
    }

    private Button generateButton(String label, EventHandler<ActionEvent> event) {
        javafx.scene.control.Button button = new javafx.scene.control.Button();
        button.setText(label);
        button.setOnAction(event);
        return button;
    }}
