package cellsociety.view.factories.choiceDialogBoxFactory;

import cellsociety.controller.SimulatorController;
import javafx.scene.control.ChoiceDialog;

public class SimulatorChoiceDialogBoxFactory extends ChoiceDialogBoxFactory{

    private SimulatorController mySimulatorController;

    public SimulatorChoiceDialogBoxFactory(SimulatorController simulatorController){
        mySimulatorController = simulatorController;
    }

    @Override
    protected void showAndWaitForChoiceDialogResult(ChoiceDialog<String> choiceDialog, String resultType) {
        choiceDialog.showAndWait();
        if(resultType.equals(cssType)){
            mySimulatorController.updateCSSFile(choiceDialog.getSelectedItem());
        }
    }
}
