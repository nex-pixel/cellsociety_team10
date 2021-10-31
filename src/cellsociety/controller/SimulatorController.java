package cellsociety.controller;

import cellsociety.Main;
import cellsociety.games.*;
import cellsociety.view.MainMenuView;
import cellsociety.view.SimulatorView;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SimulatorController {

    public String RESOURCE_ACTIONS_NAME = "cellsociety.controller.resources.modelNames.";

    private Game myGame;
    private SimulatorView mySimulatorView;
    private FileManager myFileManager;
    private String myCSSFile;
    private ResourceBundle myLanguageResources;
    private String myModelType;
    private ResourceBundle actionNameBundle;
    private File myCSVFile;
    private String LANG_KEY = "language";
    private MainController myMainController;

    public SimulatorController(MainController mainController, FileManager fileManager, String cssFile, ResourceBundle resourceBundle) {
        myMainController = mainController;
        myFileManager = fileManager;
        myCSSFile = cssFile;
        myLanguageResources = resourceBundle;
        RESOURCE_ACTIONS_NAME += myLanguageResources.getString(LANG_KEY);
    }

    /**
     * Receives csvFile with the initial state of the cells and repeats the rule indefinitely until the user stops it
     * @param csvFile file containing the initial state
     */
    public void createNewSimulation(File csvFile){
        myCSVFile = csvFile;
        actionNameBundle = ResourceBundle.getBundle(RESOURCE_ACTIONS_NAME);
        try{
            handleMethod(actionNameBundle.getString(myModelType)).invoke(SimulatorController.this);
            mySimulatorView = new SimulatorView(myGame,
                    myCSSFile, myLanguageResources, this);
        } catch(Exception e){
            myFileManager.checkFileValidity(csvFile);
            e.printStackTrace();
        }
    }

    protected Method handleMethod(String name) {
        try{
            Method m = this.getClass().getDeclaredMethod(name);
            return m;
        }catch(NoSuchMethodException e){
            return null;
        }
    }

    public String getSimFilePath () {
        return myCSVFile.getAbsolutePath().replaceAll("csv", "sim");
    }

    private void makeGameOfLife(){
        myGame = new GameOfLifeModel(myCSVFile.getAbsolutePath());
    }
    private void makePercolation(){
        myGame = new PercolationModel(myCSVFile.getAbsolutePath());
    }
    private void makeSegregation(){
        myGame = new SegregationModel(myCSVFile.getAbsolutePath(), 0.5);
    }
    private void makeSpreadingFire(){
        myGame = new SpreadingFireModel(myCSVFile.getAbsolutePath());
    }
    private void makeWaTorWorld(){
        myGame = new WaTorWorldModel(myCSVFile.getAbsolutePath());
    }

    /**
     * load a new simulation from the CSV file selected by the user
     */
    public void loadNewCSV(){
        myMainController.loadNewGame();
    }

    public void replaceWithNewCSV(){
        mySimulatorView.stopSimulation();
        myMainController.loadNewGame();
    }

    public void updateModelType(String modelType){
        myModelType = modelType;
    }

    public void updateCSSFile(String cssFile){
        myCSSFile = cssFile;
    }

    public void updateCellOnClick(int xCoordinate, int yCoordinate){
        Point point = new Point(xCoordinate, yCoordinate);
        myGame.changeCellOnClick(point);
    }
}
