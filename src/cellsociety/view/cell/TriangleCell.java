package cellsociety.view.cell;

import cellsociety.controller.SimulatorController;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.TriangleMesh;

/**
 * TriangleCell is a class that creates a triangle shaped cell to be added to the Simulation Grid
 *
 * @author Young Jun
 */
public class TriangleCell extends Cell {
    private static final double TRIANGLE_WIDTH = 50.0;
    private static final double TRIANGLE_HEIGHT = 43.3;

    public TriangleCell(SimulatorController simulatorController, int orientation, int xCoordinate, int yCoordinate) {
        super(simulatorController, xCoordinate, yCoordinate);
        if (orientation == 0)
            this.getPoints().addAll(0.0, TRIANGLE_HEIGHT, TRIANGLE_WIDTH / 2, 0.0, TRIANGLE_WIDTH, TRIANGLE_HEIGHT);
        if (orientation == 1)
            this.getPoints().addAll(0.0, 0.0, TRIANGLE_WIDTH, 0.0, TRIANGLE_WIDTH / 2, TRIANGLE_HEIGHT);
        this.setOnMouseClicked(event -> simulatorController.updateCellOnClick(xCoordinate, yCoordinate));
    }
}