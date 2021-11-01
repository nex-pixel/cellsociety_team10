package cellsociety.view.factories.choiceDialogBoxFactory;

import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;

public abstract class ChoiceDialogBoxFactory {

    protected String cssType = "cssFile";

    public ChoiceDialog<String> generateChoiceDialogBox(String defaultChoice, ArrayList<String> options, String resultType, String content){
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(defaultChoice);
        addItemsToOptionsList(options, choiceDialog);
        choiceDialog.setContentText(content);
        showAndWaitForChoiceDialogResult(choiceDialog, resultType);
        return choiceDialog;
    }

    protected abstract void showAndWaitForChoiceDialogResult(ChoiceDialog<String> choiceDialog, String resultType);

    private void addItemsToOptionsList(ArrayList<String> options, ChoiceDialog<String> choiceDialog){
        for(String s : options){
            choiceDialog.getItems().add(s);
        }
    }

}
