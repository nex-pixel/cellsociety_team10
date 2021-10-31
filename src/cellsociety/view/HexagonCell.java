package cellsociety.view;

import cellsociety.controller.SimulatorController;
import javafx.scene.shape.Polygon;

public class HexagonCell extends Polygon {
    HexagonCell(SimulatorController simulatorController, int xCoordinate, int yCoordinate){
        this.getPoints().addAll(10.0,0.0 ,30.0,0.0, 40.0,17.321, 30.0,34.64, 10.0, 34.64, 0.0, 17.321);
        this.setOnMouseClicked(event -> simulatorController.updateCellOnClick(xCoordinate, yCoordinate));
    }

}
