package cellsociety.view.cell;

import cellsociety.controller.SimulatorController;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.TriangleMesh;

public class TriangleCell extends Cell {
    public TriangleCell(SimulatorController simulatorController, int orientation, int xCoordinate, int yCoordinate){
        super(simulatorController, xCoordinate, yCoordinate);
        if(orientation == 0) this.getPoints().addAll(0.0, 43.30125, 25.0, 0.0, 50.0, 43.30125);
        if(orientation == 1) this.getPoints().addAll(0.0,0.0, 50.0,0.0, 25.0,43.30125);
        this.setOnMouseClicked(event -> simulatorController.updateCellOnClick(xCoordinate, yCoordinate));
    }
}