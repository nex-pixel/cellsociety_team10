package cellsociety.components;

import java.awt.*;
import java.util.Set;

public class HexagonGrid extends Grid {

    private int NEIGHBOR_MODE_COMPLETE;
    private int NEIGHBOR_MODE_EDGE;
    private int NEIGHBOR_MODE_BOTTOM_HALF;

    public HexagonGrid (int[][] states, int neighborMode, int edgePolicy) {
        super(states, neighborMode, edgePolicy);
    }

    @Override
    public void assignMaxCol () { setMaxCol(2*getNumCols()); }

    @Override
    protected void initializeBoard (int[][] states) {
        for (int rowIndex = 0; rowIndex < getNumRows(); rowIndex++) {
            for (int colIndex = 0; colIndex < getNumCols(); colIndex++) {
                int newColIndex = rowIndex % 2 == 0 ? 2 * colIndex + 1: 2 * colIndex;
                Point point =  new Point(newColIndex, rowIndex);
                Cell cell = new Cell(states[rowIndex][colIndex], newColIndex, rowIndex);
                getBoard().put(point, cell);
            }
        }
    }

    protected void applyNeighborMode (Point point) {
        if(getNeighborMode() == NEIGHBOR_MODE_BOTTOM_HALF){
            setRowColValues("HexagonGrid_BottomHalf_Rows", "HexagonGrid_BottomHalf_Cols");
        } else{
            setRowColValues("HexagonGrid_Complete_Rows","HexagonGrid_Complete_Cols");
        }
    }


    @Override
    protected boolean isInsideBoard (int x, int y) {
        int oldX = y % 2 == 0 ? (x-1)/2 : x/2;
        return super.isInsideBoard(oldX, y);
    }

    @Override
    public void expandGrid(int left, int top, int right, int bottom) {
        //ToDo: Grid is now abstract so "new Grid" doesn't work now
        int myNumRows = getNumRows();
        int myNumCols = getNumCols();
        Set<Point> points = getPoints();
        Grid newGrid = new HexagonGrid(new int[myNumRows + top + bottom][myNumRows + left + right], getNeighborMode(), getEdgePolicy());
        initializeNewGridasOriginal(2, left, top, right, bottom, myNumRows, myNumCols, points, newGrid);
    }

    @Override
    protected void populateNeighborData() {
        super.populateNeighborData();
        NEIGHBOR_MODE_COMPLETE = myReader.getIntProperty("NEIGHBOR_MODE_COMPLETE");
        NEIGHBOR_MODE_EDGE = myReader.getIntProperty("NEIGHBOR_MODE_EDGE");
        NEIGHBOR_MODE_BOTTOM_HALF = myReader.getIntProperty("NEIGHBOR_MODE_BOTTOM_HALF");
    }
}
