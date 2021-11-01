package cellsociety.components;

import java.awt.*;
import java.util.Set;

public class TriangleGrid extends Grid {

    private int NEIGHBOR_MODE_COMPLETE;
    private int NEIGHBOR_MODE_EDGE;
    private int NEIGHBOR_MODE_BOTTOM_HALF;
    private String mode, rowMode, colMode;

    public TriangleGrid(int[][] states, int neighborMode, int edgePolicy) {
        super(states, neighborMode, edgePolicy);
        populateNeighborData();
    }

    protected void applyNeighborMode(Point point) {
        mode = "TriangleGrid_";
        if (getNeighborMode() == NEIGHBOR_MODE_EDGE) {
            rowMode = mode + "Edge_";
            colMode = mode + "Edge_";
        } else if (getNeighborMode() == NEIGHBOR_MODE_BOTTOM_HALF) {
            rowMode = mode + "BottomHalf_";
            colMode = mode + "BottomHalf_";
        } else {
            rowMode = mode + "Complete_";
            colMode = mode + "Complete_";
        }

        if ((point.x + point.y) % 2 == 0) { // upward triangle
            setRowColValues(rowMode + "Rows_" + "Upward", colMode + "Cols_" + "Upward");
        } else {
            setRowColValues(rowMode + "Rows_" + "Downward", colMode + "Cols_" + "Downward");
        }
    }

    @Override
    public void expandGrid(int left, int top, int right, int bottom) {
        //ToDo: Grid is now abstract so "new Grid" doesn't work now
        int myNumRows = getNumRows();
        int myNumCols = getNumCols();
        Set<Point> points = getPoints();
        Grid newGrid = new TriangleGrid(new int[myNumRows + top + bottom][myNumCols + left + right], getNeighborMode(), getEdgePolicy());
        initializeNewGridasOriginal(1, left, top, right, bottom, myNumRows, myNumCols, points, newGrid);
    }

    @Override
    protected void populateNeighborData() {
        super.populateNeighborData();
        NEIGHBOR_MODE_COMPLETE = myReader.getIntProperty("NEIGHBOR_MODE_COMPLETE");
        NEIGHBOR_MODE_EDGE = myReader.getIntProperty("NEIGHBOR_MODE_EDGE");
        NEIGHBOR_MODE_BOTTOM_HALF = myReader.getIntProperty("NEIGHBOR_MODE_BOTTOM_HALF");
    }
}
