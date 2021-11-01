package cellsociety.view.factories.choiceDialogBoxFactory;

import cellsociety.ReflectionHandler;
import cellsociety.error.GenerateError;
import javafx.scene.control.ChoiceDialog;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.ResourceBundle;

public abstract class ChoiceDialogBoxFactory {

    protected String CHOICE_EVENTS_PATH = "cellsociety.resources.choiceBoxEvents.";
    protected String CHOICE_BOX_CLASSPATH = "cellsociety.view.factories.choiceDialogBoxFactory.";
    protected ChoiceDialog<String> myChoiceDialog;
    protected String INVALID_METHOD = "InvalidMethod";
    protected ResourceBundle myChoiceBoxEventsBundle;
    protected ReflectionHandler myReflectionHandler;
    protected ResourceBundle myLanguageResources;

    public ChoiceDialog<String> generateChoiceDialogBox(String defaultChoice, ArrayList<String> options, String resultType, String content){
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(defaultChoice);
        addItemsToOptionsList(options, choiceDialog);
        choiceDialog.setContentText(content);
        showAndWaitForChoiceDialogResult(choiceDialog, resultType);
        return choiceDialog;
    }

    protected void showAndWaitForChoiceDialogResult(ChoiceDialog<String> choiceDialog, String resultType){
        myChoiceDialog = choiceDialog;
        myChoiceDialog.showAndWait();
        Method method = myReflectionHandler.handleMethod(myChoiceBoxEventsBundle.getString(resultType), CHOICE_BOX_CLASSPATH);
        invokeMethod(method);
    }

    protected abstract void invokeMethod(Method method);

    private void addItemsToOptionsList(ArrayList<String> options, ChoiceDialog<String> choiceDialog){
        for(String s : options){
            choiceDialog.getItems().add(s);
        }
    }

}
