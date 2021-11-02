package cellsociety.view.factories.choiceDialogBoxFactory;

import cellsociety.ReflectionHandler;
import javafx.scene.control.ChoiceDialog;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Abstract class to generate and handle ChoiceDialogBoxes
 *
 * @author Ryleigh Byrne, Young Jun
 */
public abstract class ChoiceDialogBoxFactory {

    protected static final String INVALID_METHOD = "InvalidMethod";
    protected String CHOICE_EVENTS_PATH = "cellsociety.resources.choiceBoxEvents.";
    protected String CHOICE_BOX_CLASSPATH = "cellsociety.view.factories.choiceDialogBoxFactory.";
    protected ChoiceDialog<String> myChoiceDialog;
    protected ResourceBundle myChoiceBoxEventsBundle;
    protected ReflectionHandler myReflectionHandler;
    protected ResourceBundle myLanguageResources;

    /**
     * Generates choice dialog box given choices. Shows and waits for result of choice dialog box
     * @param defaultChoice default choice that is displayed
     * @param options options that can be selected
     * @param resultType type of result, used to handle response
     * @param content text content to tell use what they are choosing
     * @return ChoiceDialog
     */
    public ChoiceDialog<String> generateChoiceDialogBox(String defaultChoice, ArrayList<String> options, String resultType, String content) {
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(defaultChoice);
        addItemsToOptionsList(options, choiceDialog);
        choiceDialog.setContentText(content);
        showAndWaitForChoiceDialogResult(choiceDialog, resultType);
        return choiceDialog;
    }

    // initialize event path for handling choices and class path for handling reflection
    protected void initializePaths(String eventPath, String classPath) {
        CHOICE_EVENTS_PATH += eventPath;
        CHOICE_BOX_CLASSPATH += classPath;
    }
    // show dialog box and wait for user response. invoke method based upon the result
    protected void showAndWaitForChoiceDialogResult(ChoiceDialog<String> choiceDialog, String resultType) {
        myChoiceDialog = choiceDialog;
        myChoiceDialog.showAndWait();
        Method method = myReflectionHandler.handleMethod(myChoiceBoxEventsBundle.getString(resultType), CHOICE_BOX_CLASSPATH);
        invokeMethod(method);
    }

    protected abstract void invokeMethod(Method method);

    // add options to choice dialog item list
    private void addItemsToOptionsList(ArrayList<String> options, ChoiceDialog<String> choiceDialog) {
        for (String s : options) {
            choiceDialog.getItems().add(s);
        }
    }

}
