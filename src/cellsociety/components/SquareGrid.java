package cellsociety.components;

import java.awt.*;

public class SquareGrid extends Grid {


    private int NEIGHBOR_MODE_COMPLETE = 0;
    private int NEIGHBOR_MODE_EDGE = 1;
    private int NEIGHBOR_MODE_BOTTOM_HALF = 2;


    public SquareGrid (int[][] states, int neighborMode, int edgePolicy) {
        super(states, neighborMode, edgePolicy);
    }

    @Override
    protected void initializeNeighbors () {
        super.initializeNeighbors();
    }

    protected void applyNeighborMode (Point point) {
        if (getNeighborMode() == NEIGHBOR_MODE_COMPLETE) {
            setNeighborRows(new int[]{-1, -1, -1, 0, 1, 1, 1, 0});
            setNeighborCols(new int[]{-1, 0, 1, 1, 1, 0, -1, -1});
        } else if (getNeighborMode() == NEIGHBOR_MODE_EDGE) {
            setNeighborRows(new int[]{-1, 0, 1, 0});
            setNeighborCols(new int[]{0, 1, 0, -1});
        } else if (getNeighborMode() == NEIGHBOR_MODE_BOTTOM_HALF) {
            setNeighborRows(new int[]{0, 1, 1, 1, 0});
            setNeighborCols(new int[]{1, 1, 0, -1, -1});
        }
    }
}
