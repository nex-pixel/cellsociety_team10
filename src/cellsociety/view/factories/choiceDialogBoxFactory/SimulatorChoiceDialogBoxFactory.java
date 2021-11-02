package cellsociety.view.factories.choiceDialogBoxFactory;

import cellsociety.ReflectionHandler;
import cellsociety.controller.SimulatorController;
import cellsociety.error.GenerateError;
import java.lang.reflect.Method;
import java.util.ResourceBundle;
/**
 * SimulatorChoiceDialogFactory extends ChoiceDialogBoxFactory and generates and handles
 * choice dialog boxes for the main menu view
 *
 * @author Ryleigh Byrne, Young Jun
 */
public class SimulatorChoiceDialogBoxFactory extends ChoiceDialogBoxFactory {
    private static final String SIMULATOR_CHOICE_BOX_CLASS_PATH = "SimulatorChoiceDialogBoxFactory";
    private static final String SIMULATOR_CHOICE_BOX_EVENT_PATH = "simulatorChoiceBoxEvents";
    private SimulatorController mySimulatorController;

    /**
     * Constructor for this factory. Initializes path for event resource bundle and path for class.
     * Initializes reflection handler.
     *
     * @param simulatorController SimulatorController to delegate actions to view and model
     * @param langResources current language resource bundle for scene
     */
    public SimulatorChoiceDialogBoxFactory(SimulatorController simulatorController, ResourceBundle langResources) {
        initializePaths(SIMULATOR_CHOICE_BOX_EVENT_PATH , SIMULATOR_CHOICE_BOX_CLASS_PATH);
        mySimulatorController = simulatorController;
        myLanguageResources = langResources;
        myChoiceBoxEventsBundle = ResourceBundle.getBundle(CHOICE_EVENTS_PATH);
        myReflectionHandler = new ReflectionHandler();
    }

    // Method to invoke methods called through reflection. If method doesn't exist, display error
    @Override
    protected void invokeMethod(Method method) {
        try {
            method.invoke(SimulatorChoiceDialogBoxFactory.this);
        } catch (Exception e) {
            new GenerateError(myLanguageResources, INVALID_METHOD);
        }
    }

    // if new CSSFile is selected, update CSSFile for view
    private void updateCSSFile() {
        mySimulatorController.updateCSSFile(myChoiceDialog.getSelectedItem());
    }
}
