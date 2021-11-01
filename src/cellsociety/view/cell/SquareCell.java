package cellsociety.view.cell;

import cellsociety.controller.SimulatorController;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class SquareCell extends Cell{

    public SquareCell(SimulatorController simulatorController, int xCoordinate, int yCoordinate){
        super(simulatorController, xCoordinate, yCoordinate);
        this.getPoints().addAll(0.0, 40.0, 40.0, 40.0, 40.0, 0.0, 0.0, 0.0);
        this.setOnMouseClicked(event -> simulatorController.updateCellOnClick(xCoordinate, yCoordinate));
    }

}
