package cellsociety.controller;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;

import java.util.Optional;

public class ViewController {

    private String language;
    private String modelType;

    public ViewController(){

    }

    /**
     * setter for language;
     * @param result language
     */
    public void updateLanguage(String result){
        language = result;
    }

    /**
     * setter for modelType
     * @param result modelType
     */
    public void updateModelType(String result){
        modelType = result;
    }

}
