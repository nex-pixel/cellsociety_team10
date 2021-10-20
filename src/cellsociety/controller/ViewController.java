package cellsociety.controller;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;

import java.util.Optional;

public class ViewController {

    private String language;
    private String modelType;

    public ViewController(){

    }


    public void updateLanguage(String result){
        language = result;
    }

    public void updateModelType(String result){
        modelType = result;
    }

}
