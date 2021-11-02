package cellsociety.view.factories.choiceDialogBoxFactory;

import cellsociety.ReflectionHandler;
import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.error.GenerateError;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

/**
 * MainMenuChoiceDialogFactory extends ChoiceDialogBoxFactory and generates and handles
 * choice dialog boxes for the main menu view
 *
 * @author Ryleigh Byrne, Young Jun
 */
public class MainMenuChoiceDialogBoxFactory extends ChoiceDialogBoxFactory {
    private static final String MAIN_MENU_CHOICE_BOX_CLASS_PATH = "MainMenuChoiceDialogBoxFactory";
    private static final String MAIN_MENU_CHOICE_BOX_EVENT_PATH = "mainMenuChoiceBoxEvents";
    private MainController myMainController;
    private FileManager myFileManager;

    /**
     * Constructor for this factory. Initializes path for event resource bundle and path for class.
     * Initializes reflection handler.
     *
     * @param mainController MainController to delegate actions to view and model
     * @param fileManager FileManager to manage file actions
     * @param langResources current language resource bundle for scene
     */
    public MainMenuChoiceDialogBoxFactory(MainController mainController, FileManager fileManager, ResourceBundle langResources) {
        initializePaths(MAIN_MENU_CHOICE_BOX_EVENT_PATH, MAIN_MENU_CHOICE_BOX_CLASS_PATH);
        myChoiceBoxEventsBundle = ResourceBundle.getBundle(CHOICE_EVENTS_PATH);
        myMainController = mainController;
        myFileManager = fileManager;
        myLanguageResources = langResources;
        myReflectionHandler = new ReflectionHandler();
    }

    // Method to invoke methods called through reflection. If method doesn't exist, display error
    @Override
    protected void invokeMethod(Method method) {
        try {
            method.invoke(MainMenuChoiceDialogBoxFactory.this);
        } catch (Exception e) {
            new GenerateError(myLanguageResources, INVALID_METHOD);
        }
    }

    // if new CSS file is selected, update CSSFile
    private void updateCSSFile() {
        myMainController.updateCSS(myChoiceDialog.getSelectedItem());
    }
    // if new game type is selected, update modelType
    private void updateModelType() {
        myMainController.updateModelType(myChoiceDialog.getSelectedItem(), myFileManager);
    }
    // if new grid type is selected, update gridType
    private void updateGridType() {
        myMainController.setCellType(Integer.parseInt(myChoiceDialog.getSelectedItem().substring(0, 1)));
    }
    // if new neighbor mode is selected, update neighborModeType
    private void updateNeighborModeType() {
        myMainController.setMyNeighborMode(Integer.parseInt(myChoiceDialog.getSelectedItem().substring(0, 1)));
    }
    // if new edge policy is selected, update edgePolicyType
    private void updateEdgePolicyType() {
        myMainController.setMyEdgePolicy(Integer.parseInt(myChoiceDialog.getSelectedItem().substring(0, 1)));
    }

}
