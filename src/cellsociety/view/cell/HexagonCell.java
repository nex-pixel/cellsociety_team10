package cellsociety.view.cell;

import cellsociety.controller.SimulatorController;
import javafx.scene.shape.Polygon;

import java.awt.*;

public class HexagonCell extends Cell {

    public HexagonCell(SimulatorController simulatorController, int xCoordinate, int yCoordinate) {
        super(simulatorController, xCoordinate, yCoordinate);
        this.getPoints().addAll(10.0, 0.0, 30.0, 0.0, 40.0, 17.321, 30.0, 34.64, 10.0, 34.64, 0.0, 17.321);
        this.setOnMouseClicked(event -> simulatorController.updateCellOnClick(xCoordinate, yCoordinate));
    }
}
