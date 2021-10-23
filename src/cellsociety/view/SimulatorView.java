package cellsociety.view;

import cellsociety.controller.SimulatorController;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.awt.Point;
import cellsociety.components.Cell;


import javax.swing.*;
import java.util.Map;

public class SimulatorView {
    private GridPane myGridView;
    private Color deadColor;
    private Color aliveColor;
    private Color defaultColor;
    private int gridWidth;
    private int gridHeight;
    private SimulatorController mySimulatorController;

    public SimulatorView(int gridWidth, int gridHeight, Color deadColor, Color aliveColor, Color defaultColor){
        myGridView = new GridPane();
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.deadColor = deadColor;
        this.aliveColor = aliveColor;
        this.defaultColor = defaultColor;
        setDefaultGrid();
    }

    // fills the grid with squareCells of defaultColor
    private void setDefaultGrid(){
        for(int i = 0; i < gridWidth; i ++){
            for(int j = 0; j < gridHeight; j++){
                squareCell cell = new squareCell();
                cell.setWidth(40); // TODO: Needs to change based on the size of the stage
                cell.setHeight(40); // TODO: need refactoring?
                cell.setFill(defaultColor);
                myGridView.add(cell, i, j);
            }
        }
    }

    /**
     * updates the cells based on the values in Map
     * dead cells are colored with deadColor, alive cells are colored with aliveColor
     * @param cellStatus Map containing the status of cells
     * @return scene with updated cell status
     */
    public void updateSimulation(Map<Point, Cell> cellStatus){
        for(Point coordinate: cellStatus.keySet()){
            int gridNumber = (int) (coordinate.getX() * gridHeight + coordinate.getY());
            Node currNode = myGridView.getChildren().get(gridNumber);
            myGridView.getChildren().remove(currNode);
            squareCell currCell = (squareCell) currNode;
            if(cellStatus.get(coordinate).getCurrentStatus() == 0){ // TODO: assumed dead is 0
                currCell.setFill(deadColor);
            }else if(cellStatus.get(coordinate).getCurrentStatus() == 1){ //TODO assumed alive is 1
                currCell.setFill(aliveColor);
            }
            myGridView.getChildren().add(gridNumber, currCell);
        }
    }

    /**
     * getter for getMyGridView
     * @return getMyGridView
     */
    public GridPane getMyGridView(){
        return myGridView;
    }


    public VBox returnSimulation(SimulatorController simulatorController){
        HBox buttonBox = new HBox();
        mySimulatorController = simulatorController;
        Button pause = generateButton("Pause", event -> mySimulatorController.pause());
        Button play = generateButton("Play", event -> mySimulatorController.play());
        buttonBox.getChildren().addAll(pause, play, makeSlider("Speed", 0.1, 2.0));
        VBox simulationBox = new VBox();
        simulationBox.getChildren().addAll(myGridView, buttonBox);
        return simulationBox;
    }

    private Button generateButton(String label, EventHandler<ActionEvent> event) {
        javafx.scene.control.Button button = new javafx.scene.control.Button();
        button.setText(label);
        button.setOnAction(event);
        return button;
    }

    protected Node makeSlider(String text, double minVal, double maxVal) {
        HBox slide = new HBox();
        Label l = new Label(text);
        l.setTextFill(Color.BLACK);
        Slider lengthSlider = new Slider(minVal, maxVal, 1);
        lengthSlider.setShowTickMarks(true);
        lengthSlider.setShowTickLabels(true);
        lengthSlider.setMaxWidth(10);

        lengthSlider.valueProperty().addListener((obs, oldval, newVal) ->
                mySimulatorController.setAnimationSpeed(newVal.intValue()));

        return slide;
    }

}
