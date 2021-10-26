package cellsociety.controller;

import cellsociety.error.GenerateError;
import cellsociety.games.*;
import cellsociety.view.SimulatorView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.ResourceBundle;

public class SimulatorController {

    private Game myGame;
    private SimulatorView mySimulatorView;
    private FileManager myFileManager;
    private Scene myScene;
    private String myCSSFile;
    private ResourceBundle myLanguageResources;



    public SimulatorController(FileManager fileManager, String cssFile, ResourceBundle resourceBundle) {
        myFileManager = fileManager;
        myCSSFile = cssFile;
        myLanguageResources = resourceBundle;
    }

    /**
     * Receives csvFile with the initial state of the cells and repeats the rule indefinitely until the user stops it
     * @param csvFile file containing the initial state
     */
    public void createNewSimulation(int modelType, File csvFile){
        Stage stage = new Stage();
        try{
            generateNewGame(modelType, csvFile);
            mySimulatorView = new SimulatorView(myGame,
                    myCSSFile, myLanguageResources, this);
            mySimulatorView.updateSimulation(myGame.getGrid());

            VBox simulationBox = mySimulatorView.returnSimulation();
            myScene = new Scene(simulationBox);
            stage.setScene(myScene);
            stage.show();
            mySimulatorView.playAnimation();
        } catch(NullPointerException e){
            myFileManager.checkFileValidity(csvFile);
        }
    }

    public void generateNewGame(int gameType, File csvFile){
        switch (gameType) {
            case 0:
                myGame = new GameOfLifeModel(csvFile.getAbsolutePath());
                break;
            case 1:
                myGame = new SpreadingFireModel(csvFile.getAbsolutePath());
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                myGame = new PercolationModel(csvFile.getAbsolutePath());
                break;
        }
    }


    /**
     * saves the current grid status into a CSV file
     */
    public void saveCSVFile(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Enter File Name");
        String fileName = dialog.showAndWait().get(); //TODO: check for ispresent
        myGame.saveCSVFile(fileName);
    }

    /**
     * load a new simulation from the CSV file selected by the user
     */
    //TODO; rethink this part - need to take timeline out of controller

    public void loadNewCSV(){
        myFileManager.chooseFile();
       // File file = myFileManager.getCurrentTextFile();
       // createNewSimulation(0, file);
        /*
        myGame = new GameOfLifeModel(myFileManager.getCurrentTextFile().getAbsolutePath());
        mySimulatorView.updateToNewSimulation(myGame.getMyGrid().getNumCols(), myGame.getMyGrid().getNumRows());
        mySimulatorView.updateSimulation(myGame.getGrid());
        VBox simulationBox = mySimulatorView.returnSimulation(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(simulationBox));
        stage.show();
        playAnimation();
         */
    }


}
