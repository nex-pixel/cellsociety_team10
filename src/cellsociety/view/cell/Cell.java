package cellsociety.view.cell;

import cellsociety.controller.SimulatorController;
import javafx.scene.shape.Polygon;

import java.awt.*;

public class Cell extends Polygon {
    private Point myPoint;

    public Cell(SimulatorController simulatorController, int xCoordinate, int yCoordinate) {
        myPoint = new Point(xCoordinate, yCoordinate);
    }

    public Point getPoint() {
        return myPoint;
    }
}
