package cellsociety.view;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.TriangleMesh;

public class TriangleCell extends Polygon {
    private int xCoordinate;
    private int yCoordinate;

    public TriangleCell(int orientation, int xCoordinate, int yCoordinate){
        if(orientation == 0) this.getPoints().addAll(0.0,0.0, 50.0,0.0, 25.0,43.30125);
        if(orientation == 1) this.getPoints().addAll(0.0, 43.30125, 25.0, 0.0, 50.0, 43.30125);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
}