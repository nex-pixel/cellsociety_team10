package cellsociety.view;

import cellsociety.components.Cell;
import cellsociety.controller.SimulatorController;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class SquareCell extends Rectangle {

    public SquareCell(SimulatorController simulatorController, int xCoordinate, int yCoordinate){

        this.setOnMouseClicked(event -> simulatorController.updateCellOnClick(xCoordinate, yCoordinate));

    }

}
