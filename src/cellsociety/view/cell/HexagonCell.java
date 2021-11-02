package cellsociety.view.cell;

import cellsociety.controller.SimulatorController;
import javafx.scene.shape.Polygon;

import java.awt.*;
/**
 * HexagonCell is a class that creates a hexagon shaped Cell to be added to the Simulation Grid
 *
 * @author Young Jun
 */
public class HexagonCell extends Cell {
    private static final double HEXAGON_WIDTH = 40.0;
    private static final double HEXAGON_HEIGHT = 34.6;

    public HexagonCell(SimulatorController simulatorController, int xCoordinate, int yCoordinate) {
        super(simulatorController, xCoordinate, yCoordinate);
        this.getPoints().addAll(HEXAGON_WIDTH / 4, 0.0, HEXAGON_WIDTH * 3 / 4, 0.0, HEXAGON_WIDTH, HEXAGON_HEIGHT / 2,
                HEXAGON_WIDTH * 3 / 4, HEXAGON_HEIGHT, HEXAGON_WIDTH / 4, HEXAGON_HEIGHT, 0.0, HEXAGON_HEIGHT / 2);
        this.setOnMouseClicked(event -> simulatorController.updateCellOnClick(xCoordinate, yCoordinate));
    }
}
