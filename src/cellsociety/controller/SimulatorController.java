package cellsociety.controller;

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

public class SimulatorController {

    private Game myGame;
    private Timeline myAnimation;
    private double animationSpeed;
    private SimulatorView mySimulatorView;
    private Color deadColor;
    private Color aliveColor;
    private Color defaultColor;
    private FileManager myFileManager;
    private Stage myStage;



    public SimulatorController(Color deadColor,
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

    // Start new animation to show game model's steps
    private void playAnimation () {
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
    public void saveCSVFile(String fileName){
        myGame.saveCSVFile(fileName);
    }


}
