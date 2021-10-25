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
    private Timeline myAnimation;
    private double animationSpeed;
    private SimulatorView mySimulatorView;


    private FileManager myFileManager;
    private Stage myStage;
    private String myCSSFile;



    public SimulatorController(FileManager fileManager, String cssFile) {
        animationSpeed = 0.3;
        myAnimation = new Timeline();
        myFileManager = fileManager;
        myCSSFile = cssFile;
    }

    /**
     * calls update function of the game and receives the new states of the Grid
     * updates the grid based on the new status
     */
    public void step(){
        myGame.update();
        mySimulatorView.updateSimulation(myGame.getGrid());
    }

    // Start new animation to show search algorithm's steps
    private void playAnimation () {
        if (myAnimation != null) {
            myAnimation.stop();
        }
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(animationSpeed), e -> step()));
        myAnimation.play();
    }


    /**
     * Receives csvFile with the initial state of the cells and repeats the rule indefinitely until the user stops it
     * @param stage primary stage of the simulation
     * @param csvFile file containing the initial state
     */
    public void createNewSimulation(int modelType, Stage stage, File csvFile){
        myStage = stage;
        try{
            generateNewGame(modelType, csvFile);
            mySimulatorView = new SimulatorView(myGame.getMyGrid().getNumCols(), myGame.getMyGrid().getNumRows(),
                    myCSSFile);
            mySimulatorView.updateSimulation(myGame.getGrid());
            VBox simulationBox = mySimulatorView.returnSimulation(this);
            stage.setScene(new Scene(simulationBox));
            stage.show();
            playAnimation();
        }catch(NullPointerException e){
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
     * pauses the animation
     */
    public void pause(){
        myAnimation.pause();
    }

    /**
     * resumes the animation
     */
    public void play(){
        myAnimation.play();
    }

    /**
     * sets the animation speed
     * @param speed number that will be multiplied to the current animation speed
     */
    public void setAnimationSpeed(double speed){
        animationSpeed = speed;
        myAnimation.setRate(animationSpeed);
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
    public void loadNewCSV(){
        myFileManager.chooseFile();
        myGame = new GameOfLifeModel(myFileManager.getCurrentTextFile().getAbsolutePath());
        mySimulatorView.updateToNewSimulation(myGame.getMyGrid().getNumCols(), myGame.getMyGrid().getNumRows());
        mySimulatorView.updateSimulation(myGame.getGrid());
        VBox simulationBox = mySimulatorView.returnSimulation(this);
        myStage.setScene(new Scene(simulationBox));
        myStage.show();
        playAnimation();
    }


}
