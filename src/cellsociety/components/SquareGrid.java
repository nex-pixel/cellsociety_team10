package cellsociety.components;

import java.awt.*;

public class SquareGrid extends Grid {

    private PropertiesReader reader;
    private int NEIGHBOR_MODE_COMPLETE;
    private int NEIGHBOR_MODE_EDGE;
    private int NEIGHBOR_MODE_BOTTOM_HALF;

    public SquareGrid (int[][] states, int neighborMode, int edgePolicy) {
        super(states, neighborMode, edgePolicy);
        populateNeighborData();
    }

    protected void applyNeighborMode (Point point) {
        if (getNeighborMode() == NEIGHBOR_MODE_COMPLETE) {
            setRowColValues("SquareGrid_Complete_Rows","SquareGrid_Complete_Cols");
        } else if (getNeighborMode() == NEIGHBOR_MODE_EDGE) {
            setRowColValues("SquareGrid_Edge_Rows","SquareGrid_Edge_Cols");
        } else if (getNeighborMode() == NEIGHBOR_MODE_BOTTOM_HALF) {
            setRowColValues("SquareGrid_BottomHalf_Rows", "SquareGrid_BottomHalf_Cols");
        }
        
//        if (getNeighborMode() == NEIGHBOR_MODE_COMPLETE) {
//            setNeighborRows(new int[]{-1, -1, -1, 0, 1, 1, 1, 0});
//            setNeighborCols(new int[]{-1, 0, 1, 1, 1, 0, -1, -1});
//        } else if (getNeighborMode() == NEIGHBOR_MODE_EDGE) {
//            setNeighborRows(new int[]{-1, 0, 1, 0});
//            setNeighborCols(new int[]{0, 1, 0, -1});
//        } else if (getNeighborMode() == NEIGHBOR_MODE_BOTTOM_HALF) {
//            setNeighborRows(new int[]{0, 1, 1, 1, 0});
//            setNeighborCols(new int[]{1, 1, 0, -1, -1});
//        }
    }

    @Override
    protected void populateNeighborData() {
        super.populateNeighborData();
        NEIGHBOR_MODE_COMPLETE = myReader.getIntProperty("NEIGHBOR_MODE_COMPLETE");
        NEIGHBOR_MODE_EDGE = myReader.getIntProperty("NEIGHBOR_MODE_EDGE");
        NEIGHBOR_MODE_BOTTOM_HALF = myReader.getIntProperty("NEIGHBOR_MODE_BOTTOM_HALF");
    }
}
