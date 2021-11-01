package cellsociety.view.factories.choiceDialogBoxFactory;

import cellsociety.ReflectionHandler;
import cellsociety.controller.SimulatorController;
import cellsociety.error.GenerateError;

import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class SimulatorChoiceDialogBoxFactory extends ChoiceDialogBoxFactory{
    private static final String MAIN_MENU_CHOICE_BOX_CLASS_PATH = "SimulatorChoiceDialogBoxFactory";
    private static final String MAIN_MENU_CHOICE_BOX_EVENT_PATH = "simulatorChoiceBoxEvents";
    private SimulatorController mySimulatorController;

    public SimulatorChoiceDialogBoxFactory(SimulatorController simulatorController, ResourceBundle langResources){
        mySimulatorController = simulatorController;
        myLanguageResources = langResources;
        CHOICE_EVENTS_PATH += MAIN_MENU_CHOICE_BOX_EVENT_PATH;
        CHOICE_BOX_CLASSPATH += MAIN_MENU_CHOICE_BOX_CLASS_PATH;
        myChoiceBoxEventsBundle = ResourceBundle.getBundle(CHOICE_EVENTS_PATH);
        myReflectionHandler = new ReflectionHandler(myLanguageResources);
    }

    @Override
    protected void invokeMethod(Method method) {
        try{
            method.invoke(SimulatorChoiceDialogBoxFactory.this);
        }catch(Exception e){
            new GenerateError(myLanguageResources, INVALID_METHOD);
        }
    }

    private void updateCSSFile(){
        mySimulatorController.updateCSSFile(myChoiceDialog.getSelectedItem());
    }
}
