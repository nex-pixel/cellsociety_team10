package cellsociety.view.cell;

import cellsociety.controller.SimulatorController;
import javafx.scene.shape.Polygon;

import java.awt.Point;

/**
 * Cell is an abstract class that creates and returns a cell to be added to the Simulation Grid
 *
 * @author Young Jun
 */
public abstract class Cell extends Polygon {
    private Point myPoint;

    public Cell(SimulatorController simulatorController, int xCoordinate, int yCoordinate) {
        myPoint = new Point(xCoordinate, yCoordinate);
    }

    /**
     * getter for myPoint
     * @return returns myPoint with x and y coordinate
     */
    public Point getPoint() {
        return myPoint;
    }
}
