package cellsociety.components;

import java.awt.*;
import java.util.Set;

/**
 * This class is a subclass representing the Hexagonal grid using Grid abstract class. The shape of the cells should be a hexagon
 * which affects the neighbor that the cell has and the grid expansion behaviour. It also initializes the grid with its own
 * algorithmic coordinate system. Thus certain x and y value determination method need to be overridden.
 * Assumptions: The grid is a regular hexagon shape grid, and all are the same size
 * Dependencies: awt, Set
 * Example: Hexagonal Grid in game of life
 *
 * @author Norah Tan, Haseeb Chaudhry
 */
public class HexagonGrid extends Grid {
    private int NEIGHBOR_MODE_COMPLETE;
    private int NEIGHBOR_MODE_EDGE;
    private int NEIGHBOR_MODE_BOTTOM_HALF;

    /**
     * Default constructor for hexagonal grid. Uses the constructor from Grid abstract class
     *
     * @param states int[][] for the grid values to be created and stored in cells
     * @param neighborMode what type of neighbor's the cell will store determined by passed in int
     * @param edgePolicy what type of grid it will be on edge (wrap around or static) determined by number
     */
    public HexagonGrid(int[][] states, int neighborMode, int edgePolicy) {
        super(states, neighborMode, edgePolicy);
    }

    /**
     * This method is used to override num cols in order to properly iterate through x values which go by two for each cell
     */
    @Override
    public void assignMaxCol() {
        setMaxCol(2 * getNumCols());
    }

    /**
     * This method creates the board making use of an algorithm to determine hexagonal placement as if a square coordinate system
     * was overlayed the top. The x values go by two from cell to cell or column to , and y go by one from cell to cell or row by row.
     * odd rows are shifted by one x from even rows
     *
     * @param states the int[][] that contains cell initial values
     */
    @Override
    protected void initializeBoard(int[][] states) {
        for (int rowIndex = 0; rowIndex < getNumRows(); rowIndex++) {
            for (int colIndex = 0; colIndex < getNumCols(); colIndex++) {
                int newColIndex = rowIndex % 2 == 0 ? 2 * colIndex + 1 : 2 * colIndex;
                Point point = new Point(newColIndex, rowIndex);
                Cell cell = new Cell(states[rowIndex][colIndex], newColIndex, rowIndex);
                getBoard().put(point, cell);
            }
        }
    }

    /**
     * This method is implementing the abstract method applyNeighborMode in order to determine which neighbors to add to neighbor
     * list based on array indices for cols and rows.
     *
     * @param point Point values passed in
     */
    protected void applyNeighborMode(Point point) {
        if (getNeighborMode() == NEIGHBOR_MODE_BOTTOM_HALF) {
            setRowColValues("HexagonGrid_BottomHalf_Rows", "HexagonGrid_BottomHalf_Cols");
        } else {
            setRowColValues("HexagonGrid_Complete_Rows", "HexagonGrid_Complete_Cols");
        }
    }

    /**
     * This method overrides the grid isInsideBoard since hexagonal make use of an algrorithim to determine x and y values for cell's
     * and to check if a cell is inside the grid we look through this algorithm
     *
     * @param x x position of point to be tested
     * @param y y position of point to be tested
     * @return true if inside the grid, false otherwise
     */
    @Override
    protected boolean isInsideBoard(int x, int y) {
        int oldX = y % 2 == 0 ? (x - 1) / 2 : x / 2;
        return super.isInsideBoard(oldX, y);
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
        Grid newGrid = new HexagonGrid(new int[myNumRows + top + bottom][myNumCols + left + right], getNeighborMode(), getEdgePolicy());
        initializeNewGridasOriginal(2, left, top, right, bottom, myNumRows, myNumCols, points, newGrid);
    }

    /**
     * This method is override to determine how to get the point with specific x and y value from hexagonal grid since the coordinate
     * system is unique for it
     *
     * @param x x position of cell
     * @param y y position of cell
     * @return the Point object that would be correct for the passed in x and y
     */
    @Override
    public Point getPoint(int x, int y) {
        return new Point((y + 1) % 2 + 2 * x, y);
    }

    /**
     * This method is overried like getPoint in order to determine the correct x and y value for point in grid and return the correct cell
     * correlating with that point
     *
     * @param x x position of cell to be returned
     * @param y y position of cell to be returned
     * @return Cell object for correct hexagonal coordinate system point
     */
    @Override
    public Cell getBoardCell(int x, int y){
        return getBoard().get(new Point((y+1)%2 + 2*x, y));
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
