package cellsociety.view.factories.choiceDialogBoxFactory;

import cellsociety.ReflectionHandler;
import cellsociety.controller.SimulatorController;
import cellsociety.error.GenerateError;
import javafx.scene.control.ChoiceDialog;

import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class SimulatorChoiceDialogBoxFactory extends ChoiceDialogBoxFactory{

    private SimulatorController mySimulatorController;
    private String mainMenuChoiceBoxClassPath = "SimulatorChoiceDialogBoxFactory";
    private String mainMenuChoiceBoxEventPath = "simulatorChoiceBoxEvents";


    public SimulatorChoiceDialogBoxFactory(SimulatorController simulatorController, ResourceBundle langResources){
        mySimulatorController = simulatorController;
        myLanguageResources = langResources;
        CHOICE_EVENTS_PATH += mainMenuChoiceBoxEventPath;
        CHOICE_BOX_CLASSPATH += mainMenuChoiceBoxClassPath;
        myChoiceBoxEventsBundle = ResourceBundle.getBundle(CHOICE_EVENTS_PATH);
        myReflectionHandler = new ReflectionHandler(myLanguageResources);
    }

    @Override
    protected void invokeMethod(Method method) {
        try{
            method.invoke(SimulatorChoiceDialogBoxFactory.this);
        }catch(Exception e){
            new GenerateError(myLanguageResources.getString(LANG_KEY), INVALID_METHOD);
        }
    }

    private void updateCSSFile(){
        mySimulatorController.updateCSSFile(myChoiceDialog.getSelectedItem());
    }
}
