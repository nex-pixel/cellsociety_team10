package cellsociety.controller;

import cellsociety.components.Cell;
import cellsociety.components.Grid;
import cellsociety.components.ReadCSVFile;
import cellsociety.games.Game;
import cellsociety.games.GameOfLifeModel;
import cellsociety.view.MainMenuView;
import cellsociety.view.SimulatorView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.control.Button;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SimulatorController {

    private GameOfLifeModel myGame;
    private Timeline myAnimation;
    private double animationSpeed;
    private SimulatorView mySimulatorView;
    private Color deadColor;
    private Color aliveColor;
    private Color defaultColor;
    private FileManager myFileManager;
    private Stage myStage;



    public SimulatorController(int gridWidth, int gridHeight, Color deadColor,
                               Color aliveColor, Color defaultColor) {
        animationSpeed = 0.3;
        myAnimation = new Timeline();
        this.deadColor = deadColor;
        this.aliveColor = aliveColor;
        this.defaultColor = defaultColor;
        myFileManager = new FileManager();
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
    public void createNewSimulation(Stage stage, File csvFile){
        myStage = stage;
        if(csvFile == null){myGame = new GameOfLifeModel("data/game_of_life/blinkers.csv"); // default
        }else{
            myGame = new GameOfLifeModel(csvFile.getAbsolutePath());
        }
        mySimulatorView = new SimulatorView(myGame.getMyGrid().getNumCols(), myGame.getMyGrid().getNumRows(),
                deadColor, aliveColor, defaultColor);
        mySimulatorView.updateSimulation(myGame.getGrid());
        VBox simulationBox = mySimulatorView.returnSimulation(this);
        stage.setScene(new Scene(simulationBox));
        stage.show();
        playAnimation();
    }



    public void pause(){
        myAnimation.pause();
    }
    public void play(){
        myAnimation.play();
    }

    public void setAnimationSpeed(double speed){
        animationSpeed = speed;
        myAnimation.setRate(animationSpeed);
    }

    public void saveCSVFile(){
        String fileName = getTextInput("Enter File Name");
        myGame.saveCSVFile(fileName);
    }

    private String getTextInput(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText(prompt);
        Optional<String> answer = dialog.showAndWait();
        return answer.get();
    }

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
