package cellsociety.view.factories.choiceDialogBoxFactory;

import cellsociety.ReflectionHandler;
import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.error.GenerateError;

import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class MainMenuChoiceDialogBoxFactory extends ChoiceDialogBoxFactory {
    private static final String MAIN_MENU_CHOICE_BOX_CLASS_PATH = "MainMenuChoiceDialogBoxFactory";
    private static final String MAIN_MENU_CHOICE_BOX_EVENT_PATH = "mainMenuChoiceBoxEvents";
    private MainController myMainController;
    private FileManager myFileManager;

    public MainMenuChoiceDialogBoxFactory(MainController mainController, FileManager fileManager, ResourceBundle langResources) {
        initializePaths();
        myChoiceBoxEventsBundle = ResourceBundle.getBundle(CHOICE_EVENTS_PATH);
        myMainController = mainController;
        myFileManager = fileManager;
        myLanguageResources = langResources;
        myReflectionHandler = new ReflectionHandler(myLanguageResources);
    }

    private void initializePaths() {
        CHOICE_EVENTS_PATH += MAIN_MENU_CHOICE_BOX_EVENT_PATH;
        CHOICE_BOX_CLASSPATH += MAIN_MENU_CHOICE_BOX_CLASS_PATH;
    }

    @Override
    protected void invokeMethod(Method method) {
        try {
            method.invoke(MainMenuChoiceDialogBoxFactory.this);
        } catch (Exception e) {
            new GenerateError(myLanguageResources, INVALID_METHOD);
        }
    }

    private void updateCSSFile() {
        myMainController.updateCSS(myChoiceDialog.getSelectedItem());
    }

    private void updateModelType() {
        myMainController.updateModelType(myChoiceDialog.getSelectedItem(), myFileManager);
    }

    private void updateGridType() {
        myMainController.setCellType(Integer.parseInt(myChoiceDialog.getSelectedItem().substring(0, 1)));
    }

    private void updateNeighborModeType() {
        myMainController.setMyNeighborMode(Integer.parseInt(myChoiceDialog.getSelectedItem().substring(0, 1)));
    }

    private void updateEdgePolicyType() {
        myMainController.setMyEdgePolicy(Integer.parseInt(myChoiceDialog.getSelectedItem().substring(0, 1)));
    }

}
