package cellsociety.view.gridBuilder;

import cellsociety.controller.SimulatorController;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

public abstract class GridBuilder {
    protected void setPaneSize(Pane gamePane, double xSize, double ySize){
        gamePane.setPrefWidth(xSize);
        gamePane.setPrefHeight(ySize);
    }

    protected void setCellLocation(Polygon cell, double xLocation, double yLocation){
        cell.setTranslateX(xLocation);
        cell.setTranslateY(yLocation);
    }

    public abstract void CreateGrid(SimulatorController mySimulatorController, int gridWidth, int gridHeight, GridPane gamePane);
}
