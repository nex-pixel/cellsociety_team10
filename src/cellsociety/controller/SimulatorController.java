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

    public static final String RESOURCE_ACTIONS_NAME = MainController.class.getPackageName() + ".Resources.ModelNames";
    public static final String RESOURCE_ACTIONS_LABEL = MainController.class.getPackageName() + ".Resources.ModelLabel";

    private Game myGame;
    private SimulatorView mySimulatorView;
    private FileManager myFileManager;
    private Scene myScene;
    private String myCSSFile;
    private ResourceBundle myLanguageResources;
    private VBox simulationBox;
    private String myModelType;
    private ResourceBundle actionNameBundle;
    private ResourceBundle actionLabelBundle;
    private File myCSVFile;

    public SimulatorController(FileManager fileManager, String cssFile, ResourceBundle resourceBundle) {
        myFileManager = fileManager;
        myCSSFile = cssFile;
        myLanguageResources = resourceBundle;
        actionLabelBundle = ResourceBundle.getBundle(RESOURCE_ACTIONS_LABEL);
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
            // Method m = this.getClass().getDeclaredMethod(actionNameBundle.getString(myModelType), String.class);
            //m.invoke(this, csvFile.getAbsolutePath());
            mySimulatorView = new SimulatorView(myGame,
                    myCSSFile, myLanguageResources, this);
        } catch(Exception e){
            // TODO: this doesnt really catch the method validity so we might want to change this
            myFileManager.checkFileValidity(csvFile);
            e.printStackTrace();
        }
    }

    protected Method handleMethod(String name) {
        try{
            Method m = SimulatorController.class.getDeclaredMethod(name);
            return m;
        }catch(NoSuchMethodException e){
            //TODO: FIX THIS
            e.printStackTrace();
        }
        //TODO: BAD
        return null;
    }

    private void makeGameOfLife(){
        myGame = new GameOfLifeModel(myCSVFile.getAbsolutePath());
    }
    private void makePercolation(){
        myGame = new PercolationModel(myCSVFile.getAbsolutePath());
    }
    private void makeSegregation(String csvFile){
        //myGame = new SegregationModel(csvFile);
    }
    private void makeSpreadingFire(){
        myGame = new SpreadingFireModel(myCSVFile.getAbsolutePath());
    }
    private void MakeWaTorWorld(){
        myGame = new WaTorWorldModel(myCSVFile.getAbsolutePath());
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
        createNewSimulation(file);
    }

    public void updateModelType(String modelType){
        myModelType = modelType;
    }


}
