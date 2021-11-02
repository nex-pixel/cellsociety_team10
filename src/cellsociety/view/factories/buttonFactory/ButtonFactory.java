package cellsociety.view.factories.buttonFactory;

import cellsociety.ReflectionHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;


public abstract class ButtonFactory {
    /**
     * ButtonFactory is an abstract class that generates buttons and delegates button actions
     *
     * @author Ryleigh Byrne, Young Jun
     */
    private static final String LABEL_OPTIONS_PATH = "cellsociety.resources.labelOptions.labelOptions";
    private static final String VALUE_SPLIT_STRING = ",";
    protected static final String CSS_FILE_LABEL_KEY = "cssFileLabelOptions";
    protected static final String INVALID_BUTTON_GENERATION = "InvalidButtonGeneration";
    protected static final String CSS_FILE = "cssFile";

    protected Map<String, EventHandler<ActionEvent>> buttonMap;
    public String actionsNamePath = "cellsociety.resources.buttonEvents.";
    protected String buttonClassPath = "cellsociety.view.factories.buttonFactory.";
    protected ResourceBundle myActionEventsResources;
    protected ResourceBundle myLanguageResources;
    protected String buttonID;
    protected ResourceBundle labelOptionsBundle;
    protected ArrayList<String> cssFileOptions = new ArrayList<>();
    protected ReflectionHandler myReflectionHandler;

    /**
     * Constructor for ButtonFactory. Initializes button map, label resource bundle to
     * handle action events that generate choice boxes, and a reflection handler
     */
    public ButtonFactory() {
        buttonMap = new LinkedHashMap<>();
        labelOptionsBundle = ResourceBundle.getBundle(LABEL_OPTIONS_PATH);
        myReflectionHandler = new ReflectionHandler();
    }
    // initializes paths for specific factories
    protected String initializePaths(String basePath, String specificPath){
        basePath += specificPath;
        String result = basePath;
        return result;
    }

    // adds button the panel
    protected void addButtonToPanel(String label, EventHandler<ActionEvent> event, Pane panel) {
        Button button = generateButton(label,
                event);
        button.setId(buttonID);
        panel.getChildren().add(button);
    }
    // generates button
    private Button generateButton(String label, EventHandler<ActionEvent> event) {
        Button button = new Button();
        button.setText(label);
        button.setOnAction(event);
        return button;
    }
    // populates button map with key of button label and value of action event
    protected abstract void populateButtonEvents();

    /**
     * Generates a button panel based on button map
     * @return panel with all buttons in map added
     */
    public Node generateButtonPanel() {
        VBox panel = new VBox();
        buttonMap.forEach((key, value) -> addButtonToPanel(key, value, panel));
        return panel;
    }
    // populate options for choice boxes that are generated on a button press. Options are found in resource bundle
    protected void populateOptions(ArrayList<String> optionsList, ResourceBundle resourceBundle, String key) {
        String[] list = resourceBundle.getString(key).split(VALUE_SPLIT_STRING);
        for (String label : list) {
            optionsList.add(myLanguageResources.getString(label));
        }
    }

}