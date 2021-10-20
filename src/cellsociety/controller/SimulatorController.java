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

public class SimulatorController {

    private GameOfLifeModel myGame;
    private Timeline myAnimation;
    private double animationSpeed;
    private SimulatorView mySimulatorView;


    public SimulatorController(int gridWidth, int gridHeight, Color deadColor,
                               Color aliveColor, Color defaultColor){
        animationSpeed = 1;
        myAnimation = new Timeline();
    }

    public void returnSimulation(){
        VBox simulationBox = new VBox();
        HBox controlBox = new HBox();
    }

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


    public void createNewSimulation(Stage stage, File csvFile){
        System.out.println(csvFile.getAbsolutePath());
        myGame = new GameOfLifeModel(csvFile.getAbsolutePath());
        mySimulatorView = new SimulatorView(myGame.getMyGrid().getNumCols(), myGame.getMyGrid().getNumRows(),
                Color.CORAL, Color.BEIGE, Color.BROWN);
        mySimulatorView.updateSimulation(myGame.getGrid());
        GridPane gameGrid = mySimulatorView.getMyGridView();
        HBox buttonBox = new HBox();
        Button pause = generateButton("Pause", event -> myAnimation.pause());
        Button play = generateButton("Play", event -> myAnimation.play());
        buttonBox.getChildren().addAll(pause, play);
        VBox simulationBox = new VBox();
        simulationBox.getChildren().addAll(gameGrid, buttonBox);
        stage.setScene(new Scene(simulationBox));
        stage.show();
        playAnimation();
    }

    private Button generateButton(String label, EventHandler<ActionEvent> event) {
        javafx.scene.control.Button button = new javafx.scene.control.Button();
        button.setText(label);
        button.setOnAction(event);
        return button;
    }
}

