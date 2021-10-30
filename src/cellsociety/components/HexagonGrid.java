package cellsociety.components;

import java.awt.*;

public class HexagonGrid extends Grid {

    private int NEIGHBOR_MODE_COMPLETE = 0;
    private int NEIGHBOR_MODE_EDGE = 1;
    private int NEIGHBOR_MODE_BOTTOM_HALF = 2;

    public HexagonGrid (int[][] states, int neighborMode, int edgePolicy) { super(states, neighborMode, edgePolicy); }

    @Override
    protected void initializeBoard (int[][] states) {
        for (int rowIndex = 0; rowIndex < getNumRows(); rowIndex++) {
            for (int colIndex = 0; colIndex < getNumCols(); colIndex++) {
                Point point = new Point(2 * colIndex + 1, rowIndex);
                Cell cell = new Cell(states[rowIndex][colIndex], 2 * colIndex + 1, rowIndex);
                getBoard().put(point, cell);
            }
        }
    }

    protected void applyNeighborMode (Point point) {
        if (getNeighborMode() != NEIGHBOR_MODE_BOTTOM_HALF) {
            setNeighborRows(new int[]{-1, -1, 0, 1, 1, 0});
            setNeighborCols(new int[]{-1, 1, 2, 1, -1, -2});
        } else if(getNeighborMode() == NEIGHBOR_MODE_BOTTOM_HALF) {
            setNeighborRows(new int[]{0, 1, 1, 0});
            setNeighborCols(new int[]{2, 1, -1, -2});
        }
    }
}
