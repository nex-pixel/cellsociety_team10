package cellsociety.controller;

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
import java.util.List;
import java.util.ResourceBundle;

public class SimulatorController {

    public String RESOURCE_ACTIONS_NAME = "cellsociety.controller.resources.ModelNames.";

    private Game myGame;
    private List<Game> myGameList;
    private SimulatorView mySimulatorView;
    private FileManager myFileManager;
    private String myCSSFile;
    private ResourceBundle myLanguageResources;
    private String myModelType;
    private ResourceBundle actionNameBundle;
    private File myCSVFile;
    private String LANG_KEY = "language";

    public SimulatorController(FileManager fileManager, String cssFile, ResourceBundle resourceBundle) {
        myFileManager = fileManager;
        myCSSFile = cssFile;
        myLanguageResources = resourceBundle;
        RESOURCE_ACTIONS_NAME += myLanguageResources.getString(LANG_KEY);
        myGameList = new ArrayList<>();
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

    private void makeGameOfLife(){
        myGame = new GameOfLifeModel(myCSVFile.getAbsolutePath());
        myGameList.add(myGame);
    }
    private void makePercolation(){
        myGame = new PercolationModel(myCSVFile.getAbsolutePath());
        myGameList.add(myGame);
    }
    private void makeSegregation(){
        myGame = new SegregationModel(myCSVFile.getAbsolutePath(), 0.5);
    }
    private void makeSpreadingFire(){
        myGame = new SpreadingFireModel(myCSVFile.getAbsolutePath());
        myGameList.add(myGame);
    }
    private void makeWaTorWorld(){
        myGame = new WaTorWorldModel(myCSVFile.getAbsolutePath());
        myGameList.add(myGame);
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
        MainMenuView newGameOptionView = new MainMenuView(myLanguageResources);
        Stage optionStage = new Stage();
       // optionStage.setScene(newGameOptionView.setNewGameChoiceDisplay(300,300));
        optionStage.show();
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
