package cellsociety.view.factories.buttonFactory;


import cellsociety.error.GenerateError;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.lang.reflect.Method;
import java.util.ArrayList;
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
    // protected String[] cssFileLabelOptions = {"DukeLabel", "UNCLabel", "LightLabel", "DarkLabel"};
    protected ResourceBundle labelOptionsBundle;
    protected ArrayList<String> cssFileOptions = new ArrayList<>();
    protected String modelLabelKey = "modelLabelOptions";
    protected String cssFileLabelKey = "cssFileLabelOptions";
    protected String gridLabelKey = "gridLabelOptions";

    public ButtonFactory() {
        buttonMap = new LinkedHashMap<>();
        labelOptionsBundle = ResourceBundle.getBundle("cellsociety.resources.labelOptions.labelOptions");
        //populateOptions(cssFileOptions, labelOptionsBundle);
    }

    protected void addButtonToPanel(String label, EventHandler<ActionEvent> event, Pane panel) {
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

    public Node generateButtonPanel() {
        VBox panel = new VBox();
        buttonMap.forEach((key, value) -> addButtonToPanel(key, value, panel));
        return panel;
    }

    protected void populateOptions(ArrayList<String> optionsList, ResourceBundle resourceBundle, String key) {
        String[] list = resourceBundle.getString(key).split(",");
        for (String label : list) {
            optionsList.add(myLanguageResources.getString(label));
        }
    }

}