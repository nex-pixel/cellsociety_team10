package cellsociety.components;

import java.awt.*;

public class TriangleGrid extends Grid {

    private int NEIGHBOR_MODE_COMPLETE = 0;
    private int NEIGHBOR_MODE_EDGE = 1;
    private int NEIGHBOR_MODE_BOTTOM_HALF = 2;

    public TriangleGrid (int[][] states, int neighborMode, int edgePolicy) {
        super(states, neighborMode, edgePolicy);
    }

    @Override
    protected void initializeNeighbors () {
        super.initializeNeighbors();
    }

    protected void applyNeighborMode (Point point) {
        if (getNeighborMode() == NEIGHBOR_MODE_COMPLETE) {
            if ((point.x + point.y) % 2 == 0) { // upward triangle
                //TODO: make the int[] magic lists
                //TODO: make a new method that includes both lines
                setNeighborRows(new int[]{-1, -1, -1, 0, 0, 1, 1, 1, 1, 1, 0, 0});
                setNeighborCols(new int[]{-1, 0, 1, 1, 2, 2, 1, 0, -1, -2, -2, -1});
            }
            else {
                setNeighborRows(new int[]{-1, -1, -1, -1, -1, 0, 0, 1, 1, 1, 0, 0});
                setNeighborCols(new int[]{-2, -1, 0, 1, 2, 1, 2, 1, 0, -1, -2, -1});
            }
        }
        else if (getNeighborMode() == NEIGHBOR_MODE_EDGE) {
            if ((point.x + point.y) % 2 == 0) { // upward triangle
                setNeighborRows(new int[]{0, 1, 0});
                setNeighborCols(new int[]{1, 0, -1});
            }
            else {
                setNeighborRows(new int[]{-1, 0, 0});
                setNeighborCols(new int[]{0, 1, -1});
            }
        }
        else if (getNeighborMode() == NEIGHBOR_MODE_BOTTOM_HALF) {
            if ((point.x + point.y) % 2 == 0) { // upward triangle
                //TODO: make the int[] magic lists
                //TODO: make a new method that includes both lines
                setNeighborRows(new int[]{0, 0, 1, 1, 1, 1, 1, 0, 0});
                setNeighborCols(new int[]{1, 2, 2, 1, 0, -1, -2, -2, -1});
            }
            else {
                setNeighborRows(new int[]{0, 0, 1, 1, 1, 0, 0});
                setNeighborCols(new int[]{1, 2, 1, 0, -1, -2, -1});
            }
        }
    }
}
