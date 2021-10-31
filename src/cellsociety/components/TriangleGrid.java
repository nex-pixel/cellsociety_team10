package cellsociety.components;

import java.awt.*;

public class TriangleGrid extends Grid {

    private int NEIGHBOR_MODE_COMPLETE;
    private int NEIGHBOR_MODE_EDGE;
    private int NEIGHBOR_MODE_BOTTOM_HALF;
    private String rowMode = "TriangleGrid_";
    private String colMode = "TriangleGrid_";

    public TriangleGrid (int[][] states, int neighborMode, int edgePolicy) { super(states, neighborMode, edgePolicy); populateNeighborData();}

    protected void applyNeighborMode (Point point) {
        if (getNeighborMode() == NEIGHBOR_MODE_EDGE) {
            rowMode += "Edge_";
            colMode += "Edge_";
        } else if (getNeighborMode() == NEIGHBOR_MODE_BOTTOM_HALF) {
            rowMode += "BottomHalf_";
            colMode += "BottomHalf_";
        } else {
            rowMode += "Complete_";
            colMode += "Complete_";
        }

        if ((point.x + point.y) % 2 == 0) { // upward triangle
            setRowColValues(rowMode + "Rows_" + "Upward", colMode + "Cols_" + "Upward");
        } else {
            setRowColValues(rowMode + "Rows_" + "Downward", colMode + "Cols_" + "Downward");
        }


//        if (getNeighborMode() == NEIGHBOR_MODE_COMPLETE) {
//            if ((point.x + point.y) % 2 == 0) { // upward triangle
//                setNeighborRows(new int[]{-1, -1, -1, 0, 0, 1, 1, 1, 1, 1, 0, 0});
//                setNeighborCols(new int[]{-1, 0, 1, 1, 2, 2, 1, 0, -1, -2, -2, -1});
//            } else {
//                setNeighborRows(new int[]{-1, -1, -1, -1, -1, 0, 0, 1, 1, 1, 0, 0});
//                setNeighborCols(new int[]{-2, -1, 0, 1, 2, 1, 2, 1, 0, -1, -2, -1});
//            }
//        } else if (getNeighborMode() == NEIGHBOR_MODE_EDGE) {
//            if ((point.x + point.y) % 2 == 0) { // upward triangle
//                setNeighborRows(new int[]{0, 1, 0});
//                setNeighborCols(new int[]{1, 0, -1});
//            } else {
//                setNeighborRows(new int[]{-1, 0, 0});
//                setNeighborCols(new int[]{0, 1, -1});
//            }
//        } else if (getNeighborMode() == NEIGHBOR_MODE_BOTTOM_HALF) {
//            if ((point.x + point.y) % 2 == 0) { // upward triangle
//                setNeighborRows(new int[]{0, 0, 1, 1, 1, 1, 1, 0, 0});
//                setNeighborCols(new int[]{1, 2, 2, 1, 0, -1, -2, -2, -1});
//            } else {
//                setNeighborRows(new int[]{0, 0, 1, 1, 1, 0, 0});
//                setNeighborCols(new int[]{1, 2, 1, 0, -1, -2, -1});
//            }
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
