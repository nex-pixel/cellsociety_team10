package cellsociety.view.factories.choiceDialogBoxFactory;

import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import javafx.scene.control.ChoiceDialog;

public class MainMenuChoiceDialogBoxFactory extends ChoiceDialogBoxFactory {

    private MainController myMainController;
    private FileManager myFileManager;
    private String modelType = "modelType";

    public MainMenuChoiceDialogBoxFactory(MainController mainController, FileManager fileManager){
        myMainController = mainController;
        myFileManager = fileManager;
    }

    @Override
    protected void showAndWaitForChoiceDialogResult(ChoiceDialog<String> choiceDialog, String resultType){
        choiceDialog.showAndWait();
        if(resultType.equals("cssFile")){
            myMainController.updateCSS(choiceDialog.getSelectedItem());
        }
        if(resultType.equals("modelType")){
            myMainController.updateModelType(choiceDialog.getSelectedItem(), myFileManager);
        }
        if(resultType.equals("gridType")){
            myMainController.setCellType(Integer.parseInt(choiceDialog.getSelectedItem().substring(0,1)));
        }
        if(resultType.equals("neighborModeType")){
            myMainController.setNeighborMode(Integer.parseInt(choiceDialog.getSelectedItem().substring(0,1)));
        }
        if(resultType.equals("EdgePolicyType")){
            myMainController.setEdgePolicy(Integer.parseInt(choiceDialog.getSelectedItem().substring(0,1)));
        }
    }

}
