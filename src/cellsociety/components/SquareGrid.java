package cellsociety.components;

import java.awt.*;
import java.util.Set;

public class SquareGrid extends Grid {

    private int NEIGHBOR_MODE_COMPLETE;
    private int NEIGHBOR_MODE_EDGE;
    private int NEIGHBOR_MODE_BOTTOM_HALF;

    public SquareGrid(int[][] states, int neighborMode, int edgePolicy) {
        super(states, neighborMode, edgePolicy);
    }

    protected void applyNeighborMode(Point point) {
        if (getNeighborMode() == NEIGHBOR_MODE_COMPLETE) {
            setRowColValues("SquareGrid_Complete_Rows", "SquareGrid_Complete_Cols");
        } else if (getNeighborMode() == NEIGHBOR_MODE_EDGE) {
            setRowColValues("SquareGrid_Edge_Rows", "SquareGrid_Edge_Cols");
        } else if (getNeighborMode() == NEIGHBOR_MODE_BOTTOM_HALF) {
            setRowColValues("SquareGrid_BottomHalf_Rows", "SquareGrid_BottomHalf_Cols");
        }
    }

    @Override
    public void expandGrid(int left, int top, int right, int bottom) {
        //ToDo: Grid is now abstract so "new Grid" doesn't work now
        int myNumRows = getNumRows();
        int myNumCols = getNumCols();
        Set<Point> points = getPoints();
        Grid newGrid = new SquareGrid(new int[myNumRows + top + bottom][myNumCols + left + right], getNeighborMode(), getEdgePolicy());
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
