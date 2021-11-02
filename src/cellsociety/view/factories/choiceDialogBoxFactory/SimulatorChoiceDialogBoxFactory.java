package cellsociety.view.factories.choiceDialogBoxFactory;

import cellsociety.ReflectionHandler;
import cellsociety.controller.SimulatorController;
import cellsociety.error.GenerateError;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class SimulatorChoiceDialogBoxFactory extends ChoiceDialogBoxFactory {
    private static final String SIMULATOR_CHOICE_BOX_CLASS_PATH = "SimulatorChoiceDialogBoxFactory";
    private static final String SIMULATOR_CHOICE_BOX_EVENT_PATH = "simulatorChoiceBoxEvents";
    private SimulatorController mySimulatorController;

    public SimulatorChoiceDialogBoxFactory(SimulatorController simulatorController, ResourceBundle langResources) {
        initializePaths(SIMULATOR_CHOICE_BOX_EVENT_PATH , SIMULATOR_CHOICE_BOX_CLASS_PATH);
        mySimulatorController = simulatorController;
        myLanguageResources = langResources;
        myChoiceBoxEventsBundle = ResourceBundle.getBundle(CHOICE_EVENTS_PATH);
        myReflectionHandler = new ReflectionHandler();
    }

    @Override
    protected void invokeMethod(Method method) {
        try {
            method.invoke(SimulatorChoiceDialogBoxFactory.this);
        } catch (Exception e) {
            new GenerateError(myLanguageResources, INVALID_METHOD);
        }
    }

    private void updateCSSFile() {
        mySimulatorController.updateCSSFile(myChoiceDialog.getSelectedItem());
    }
}
