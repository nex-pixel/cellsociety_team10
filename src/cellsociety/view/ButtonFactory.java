package cellsociety.view;

import cellsociety.controller.MainController;
import cellsociety.controller.SimulatorController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class ButtonFactory {

    protected Map<String, EventHandler<ActionEvent>> buttonMap;
    protected String buttonID;

    public ButtonFactory(){
        buttonMap = new LinkedHashMap<>();
    }

    protected void addButtonToPanel(String label, EventHandler<ActionEvent> event, Pane panel){
        Button button = generateButton(label,
                event);
        button.setId(buttonID);
        panel.getChildren().add(button);
    }

    private Button generateButton(String label, EventHandler<ActionEvent> event) {
        Button button = new Button();
        button.setText(label);
        button.setOnAction(event);
        return button;
    }

    protected abstract void populateButtonEvents();

    protected Method handleMethod(String name) {
        try{
            Method m = this.getClass().getDeclaredMethod(name);
            return m;
        }catch(NoSuchMethodException e){
            return null;
        }
    }

    public Node generateButtonPanel(){
        VBox panel = new VBox();
        buttonMap.forEach((key,value) -> addButtonToPanel(key,value,panel));
        return panel;
    }


}