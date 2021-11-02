package cellsociety.view.factories.buttonFactory;

import cellsociety.ReflectionHandler;
import cellsociety.error.GenerateError;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public abstract class ButtonFactory {

    protected Map<String, EventHandler<ActionEvent>> buttonMap;
    public String ACTIONS_NAME_PATH = "cellsociety.resources.buttonEvents.";
    private static final String LABEL_OPTIONS_PATH = "cellsociety.resources.labelOptions.labelOptions";
    private static final String VALUE_SPLIT_STRING = ",";
    protected static final String CSS_FILE_LABEL_KEY = "cssFileLabelOptions";
    protected static final String INVALID_BUTTON_GENERATION = "InvalidButtonGeneration";
    protected static final String CSS_FILE = "cssFile";

    protected String buttonClassPath = "cellsociety.view.factories.buttonFactory.";
    protected ResourceBundle myActionEventsResources;
    protected ResourceBundle myLanguageResources;
    protected String buttonID;;
    protected ResourceBundle labelOptionsBundle;
    protected ArrayList<String> cssFileOptions = new ArrayList<>();
    protected ReflectionHandler myReflectionHandler;


    public ButtonFactory() {
        buttonMap = new LinkedHashMap<>();
        labelOptionsBundle = ResourceBundle.getBundle(LABEL_OPTIONS_PATH);
        myReflectionHandler = new ReflectionHandler();
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
        String[] list = resourceBundle.getString(key).split(VALUE_SPLIT_STRING);
        for (String label : list) {
            optionsList.add(myLanguageResources.getString(label));
        }
    }

}