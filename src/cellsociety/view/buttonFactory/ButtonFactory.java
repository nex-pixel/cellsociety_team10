package cellsociety.view.buttonFactory;


import cellsociety.error.GenerateError;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class ButtonFactory {

    protected Map<String, EventHandler<ActionEvent>> buttonMap;
    public String ACTIONS_NAME_PATH = "cellsociety.resources.buttonEvents.";
    protected ResourceBundle myActionEventsResources;
    protected ResourceBundle myLanguageResources;
    protected String buttonID;
    protected String INVALID_BUTTON_GENERATION = "InvalidButtonGeneration";
    protected String LANG_KEY = "language";
    private String INVALID_METHOD = "InvalidMethod";

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
            new GenerateError(myLanguageResources.getString(LANG_KEY), INVALID_METHOD);
            return null;
        }
    }

    public Node generateButtonPanel(){
        VBox panel = new VBox();
        buttonMap.forEach((key,value) -> addButtonToPanel(key,value,panel));
        return panel;
    }

}