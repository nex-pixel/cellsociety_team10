package cellsociety.controller;

import cellsociety.games.*;
import cellsociety.view.SimulatorView;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class SimulatorController {

    private Game myGame;
    private SimulatorView mySimulatorView;
    private FileManager myFileManager;
    private Scene myScene;
    private String myCSSFile;
    private ResourceBundle myLanguageResources;
    private VBox simulationBox;
    private String myModelType;

    public SimulatorController(FileManager fileManager, String cssFile, ResourceBundle resourceBundle) {
        myFileManager = fileManager;
        myCSSFile = cssFile;
        myLanguageResources = resourceBundle;
    }

    /**
     * Receives csvFile with the initial state of the cells and repeats the rule indefinitely until the user stops it
     * @param csvFile file containing the initial state
     */
    public void createNewSimulation(String modelType, File csvFile){
        try{
            myModelType = modelType;
            Method m = this.getClass().getDeclaredMethod(modelType, String.class);
            m.invoke(this, csvFile.getAbsolutePath());
            mySimulatorView = new SimulatorView(myGame,
                    myCSSFile, myLanguageResources, this);
        } catch(Exception e){
            myFileManager.checkFileValidity(csvFile);
        }
    }

    private void makeGameOfLife(String csvFile){
        myGame = new GameOfLifeModel(csvFile);
    }
    private void makePercolation(String csvFile){
        myGame = new PercolationModel(csvFile);
    }
    private void makeSegregation(String csvFile){
        //myGame = new SegregationModel(csvFile);
    }
    private void makeSpreadingFire(String csvFile){
        myGame = new SpreadingFireModel(csvFile);
    }
    private void MakeWaTorWorld(String csvFile){
        myGame = new WaTorWorldModel(csvFile);
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
        File file = myFileManager.getCurrentTextFile();
        createNewSimulation(myModelType, file);
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
