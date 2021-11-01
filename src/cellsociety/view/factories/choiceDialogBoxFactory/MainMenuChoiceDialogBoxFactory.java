package cellsociety.view.factories.choiceDialogBoxFactory;

import cellsociety.ReflectionHandler;
import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.error.GenerateError;
import javafx.scene.control.ChoiceDialog;

import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class MainMenuChoiceDialogBoxFactory extends ChoiceDialogBoxFactory {

    private MainController myMainController;
    private FileManager myFileManager;
    private String mainMenuChoiceBoxClassPath = "MainMenuChoiceDialogBoxFactory";
    private String mainMenuChoiceBoxEventPath = "mainMenuChoiceBoxEvents";


    public MainMenuChoiceDialogBoxFactory(MainController mainController, FileManager fileManager, ResourceBundle langResources){
        CHOICE_EVENTS_PATH += mainMenuChoiceBoxEventPath;
        CHOICE_BOX_CLASSPATH += mainMenuChoiceBoxClassPath;
        myChoiceBoxEventsBundle = ResourceBundle.getBundle(CHOICE_EVENTS_PATH);
        myMainController = mainController;
        myFileManager = fileManager;
        myLanguageResources = langResources;
        myReflectionHandler = new ReflectionHandler(myLanguageResources);
    }

    @Override
    protected void invokeMethod(Method method){
        try{
            method.invoke(MainMenuChoiceDialogBoxFactory.this);
        }catch(Exception e){
            new GenerateError(myLanguageResources.getString(LANG_KEY), INVALID_METHOD);
        }
    }

    private void updateCSSFile(){
        myMainController.updateCSS(myChoiceDialog.getSelectedItem());
    }

    private void updateModelType(){
        myMainController.updateModelType(myChoiceDialog.getSelectedItem(), myFileManager);
    }

    private void updateGridType(){
        myMainController.setCellType(Integer.parseInt(myChoiceDialog.getSelectedItem().substring(0,1)));
    }

    private void updateNeighborModeType(){
        myMainController.setNeighborMode(Integer.parseInt(myChoiceDialog.getSelectedItem().substring(0,1)));
    }

    private void updateEdgePolicyType(){
        myMainController.setEdgePolicy(Integer.parseInt(myChoiceDialog.getSelectedItem().substring(0,1)));
    }

}
