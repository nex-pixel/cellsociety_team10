package cellsociety.view.gridBuilder;

import cellsociety.controller.SimulatorController;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
/**
 * GridBuilder is an abstract class that creates a grid of the simulation
 *
 * @author Young Jun
 */
public abstract class GridBuilder {
    protected static final String DEFAULT_CELL_ID = "default-cell";

    public abstract void CreateGrid(SimulatorController mySimulatorController, int gridWidth, int gridHeight, GridPane gamePane);

    protected void setPaneSize(Pane gamePane, double xSize, double ySize) {
        gamePane.setPrefWidth(xSize);
        gamePane.setPrefHeight(ySize);
    }

    protected void setCellLocation(Polygon cell, double xLocation, double yLocation) {
        cell.setTranslateX(xLocation);
        cell.setTranslateY(yLocation);
    }

}
