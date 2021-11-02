package cellsociety.components;

import java.awt.*;
import java.util.Set;

/**
 * This class is a subclass representing the Triangle grid using Grid abstract class. The shape of the cells should be a triangle
 * which affects the neighbor that the cell has and the grid expansion behaviour
 * Assumptions: The grid is a regular-ish isosceles triangle shape grid, and all are the same size
 * Dependencies: awt, Set
 * Example: Triangle Grid in Wator World Simulation
 *
 * @author Norah Tan, Haseeb Chaudhry
 */
public class TriangleGrid extends Grid {

    private int NEIGHBOR_MODE_COMPLETE;
    private int NEIGHBOR_MODE_EDGE;
    private int NEIGHBOR_MODE_BOTTOM_HALF;
    private String mode, rowMode, colMode;

    /**
     * Default constructor for triangle grid. Uses the constructor from Grid abstract class
     *
     * @param states int[][] for the grid values to be created and stored in cells
     * @param neighborMode what type of neighbor's the cell will store determined by passed in int
     * @param edgePolicy what type of grid it will be on edge (wrap around or static) determined by number
     */
    public TriangleGrid(int[][] states, int neighborMode, int edgePolicy) {
        super(states, neighborMode, edgePolicy);
        populateNeighborData();
    }

    /**
     * This method is implementing the abstract method applyNeighborMode in order to determine which neighbors to add to neighbor
     * list based on array indices for cols and rows. It uses if and else statements to construct the specific two arrays to grab depending
     * on if it's neighbor mode and orientation of the triangle as triangles on the grid are directional unlike square and hexagonal
     *
     * @param point Point values passed in
     */
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

    /**
     * This method is used to expand the grid in the four directions possibly by values equal to or greater than 0
     *
     * @param left leftward expansion by certain int amount above 0 or 0
     * @param top upward expansion by certain int amount above 0 or 0
     * @param right rightward expansion by certain int amount above 0 or 0
     * @param bottom downward expansion by certain int amount above 0 or 0
     */
    @Override
    public void expandGrid(int left, int top, int right, int bottom) {
        int myNumRows = getNumRows();
        int myNumCols = getNumCols();
        Set<Point> points = getPoints();
        Grid newGrid = new TriangleGrid(new int[myNumRows + top + bottom][myNumCols + left + right], getNeighborMode(), getEdgePolicy());
        initializeNewGridasOriginal(1, left, top, right, bottom, myNumRows, myNumCols, points, newGrid);
    }

    /**
     * This method is used to read in the magic values that are used in this class and in Game
     */
    @Override
    protected void populateNeighborData() {
        super.populateNeighborData();
        NEIGHBOR_MODE_COMPLETE = myReader.getIntProperty("NEIGHBOR_MODE_COMPLETE");
        NEIGHBOR_MODE_EDGE = myReader.getIntProperty("NEIGHBOR_MODE_EDGE");
        NEIGHBOR_MODE_BOTTOM_HALF = myReader.getIntProperty("NEIGHBOR_MODE_BOTTOM_HALF");
    }
}
