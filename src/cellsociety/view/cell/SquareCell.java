package cellsociety.view.cell;

import cellsociety.components.Cell;
import cellsociety.controller.SimulatorController;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class SquareCell extends Rectangle {

    public SquareCell(SimulatorController simulatorController, int xCoordinate, int yCoordinate){
        this.setHeight(40);
        this.setWidth(40);
        this.setOnMouseClicked(event -> simulatorController.updateCellOnClick(xCoordinate, yCoordinate));

    }

}
