package cellsociety.controller;

import cellsociety.components.Grid;
import cellsociety.components.ReadCSVFile;
import cellsociety.games.Game;
import cellsociety.games.GameOfLifeModel;
import cellsociety.view.SimulatorView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class SimulatorController {

    private List<SimulatorView> mySimulations;
    private List<Game> myGames;
    private Timeline myAnimation;
    private double animationSpeed;


    public SimulatorController(List<String> fileNames, double animationSpeed, int gridWidth, int gridHeight, Color deadColor,
                               Color aliveColor, Color defaultColor){
        mySimulations = new ArrayList<>();
        myGames = new ArrayList<>();
        this.animationSpeed = animationSpeed;

        for(String name : fileNames){
            Game game = new GameOfLifeModel(name);
            myGames.add(game);
            mySimulations.add(new SimulatorView(gridWidth, gridHeight, deadColor, aliveColor, defaultColor));
        }
    }

    public void createSimulation(Grid grid, SimulatorView simulatorView){
    }

    public void step(){
        for(int i = 0; i < myGames.size(); i++){
            myGames.get(i).update();
            mySimulations.get(i).updateSimulation(myGames.get(i).getGrid());
        }
    }

    // Start new animation to show search algorithm's steps
    private void playAnimation () {
        if (myAnimation != null) {
            myAnimation.stop();
        }
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(animationSpeed), e -> step()));
        myAnimation.play();
    }
}

