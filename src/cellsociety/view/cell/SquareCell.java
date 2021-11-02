package cellsociety.view.cell;

import cellsociety.controller.SimulatorController;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

import java.util.Map;
/**
 * SquareCell is a class that creates a square shaped cell to be added to the Simulation Grid
 *
 * @author Young Jun
 */
public class SquareCell extends Cell {
    private static final double SQUARE_WIDTH = 40.0;


    public SquareCell(SimulatorController simulatorController, int xCoordinate, int yCoordinate) {
        super(simulatorController, xCoordinate, yCoordinate);
        this.getPoints().addAll(0.0, SQUARE_WIDTH, SQUARE_WIDTH, SQUARE_WIDTH, SQUARE_WIDTH, 0.0, 0.0, 0.0);
        this.setOnMouseClicked(event -> simulatorController.updateCellOnClick(xCoordinate, yCoordinate));
    }

}
