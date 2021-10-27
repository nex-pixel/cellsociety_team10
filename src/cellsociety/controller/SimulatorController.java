package cellsociety.controller;

import cellsociety.games.*;
import cellsociety.view.SimulatorView;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ResourceBundle;

public class SimulatorController {

    private Game myGame;
    private SimulatorView mySimulatorView;
    private FileManager myFileManager;
    private Scene myScene;
    private String myCSSFile;
    private ResourceBundle myLanguageResources;
    private VBox simulationBox;
    private int myModelType;



<<<<<<< HEAD
    // Start new animation to show game model's steps
    private void playAnimation () {
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(animationSpeed), e -> step()));
        myAnimation.play();
=======
    public SimulatorController(FileManager fileManager, String cssFile, ResourceBundle resourceBundle) {
        myFileManager = fileManager;
        myCSSFile = cssFile;
        myLanguageResources = resourceBundle;
>>>>>>> 4e65a68a9073584034680d35d20ecc2d2e2e5f72
    }

    /**
     * Receives csvFile with the initial state of the cells and repeats the rule indefinitely until the user stops it
     * @param csvFile file containing the initial state
     */
<<<<<<< HEAD
    public void createNewSimulation(int modelType, Stage stage, File csvFile){
        myStage = stage;
        myGame = generateNewGame(modelType, csvFile);
        // create new simulation from the game
        showGameSimulationView(myGame);
        playAnimation();
    }

    /**
     * load a new simulation from the CSV file selected by the user
     * @param csvFile file selected by the user
     */
    public void loadNewSimulationFromCSV(File csvFile){
        myGame = new GameOfLifeModel(csvFile.getAbsolutePath());
        showGameSimulationView(myGame);
        playAnimation();
    }

    private void showGameSimulationView(Game game){
        mySimulatorView = new SimulatorView(game.getMyGrid().getNumCols(), game.getMyGrid().getNumRows(),
                deadColor, aliveColor, defaultColor);
        mySimulatorView.updateSimulation(game.getGrid());
        myStage.setScene(new Scene(mySimulatorView.returnSimulation(this)));
        myStage.show();
    }

    // create new game based on the gameType and return
    private Game generateNewGame(int gameType, File csvFile){
        System.out.println(gameType);
        switch (gameType) {
=======
    public void createNewSimulation(int modelType, File csvFile){
        try{
            myModelType = modelType;
            generateNewGame(csvFile);
            mySimulatorView = new SimulatorView(myGame,
                    myCSSFile, myLanguageResources, this);
        } catch(NullPointerException e){
            myFileManager.checkFileValidity(csvFile);
        }
    }

    public void generateNewGame(File csvFile){
        switch (myModelType) {
>>>>>>> 4e65a68a9073584034680d35d20ecc2d2e2e5f72
            case 0:
                return new GameOfLifeModel(csvFile.getAbsolutePath());
            case 1:
                return new SpreadingFireModel(csvFile.getAbsolutePath());
            case 2:
                break;
            case 3:
                break;
            case 4:
                 return new PercolationModel(csvFile.getAbsolutePath());
        }
        return null;
    }


    /**
     * saves the current grid status into a CSV file
     */
    public void saveCSVFile(String fileName){
        myGame.saveCSVFile(fileName);
    }

<<<<<<< HEAD
=======
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
>>>>>>> 4e65a68a9073584034680d35d20ecc2d2e2e5f72


}
