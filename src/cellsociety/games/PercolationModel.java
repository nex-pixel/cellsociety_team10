package cellsociety.games;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.*;
import java.util.List;

/***
 * The class models the Game of Percolation
 *
 * @author Norah Tan, Haseeb Chaudhry
 */
public class PercolationModel extends Game {

    private int BLOCKED;
    private int OPEN;
    private int PERCOLATED;
    private static final int NUM_STATES = 3;

    private List<Cell> myOpenCells;
    private List<Cell> cellsToBeRemoved;

    /***
     * Constructor that takes in a CSV file for grid initialization,
     * with default square grid, complete neighbors and finite edge policy.
     *
     * @param filename gives the CSV figuration file for the grid
     */
    public PercolationModel(String filename) {
        super(filename);
        setOpenCells();
    }

    /***
     * Constructor that takes in a CSV file for grid initialization,
     * with a specified grid shape, neighbor mode and edge policy.
     *
     * @param filename gives the CSV figuration file for the grid
     * @param gridType gives one of square, triangle, and hexagon grid shapes.
     * @param neighborMode gives one of complete, cardinal (adjacent on the edge), and bottom half modes of neighbors
     * @param edgePolicy gives one of finite, toroidal, and cylindrical edge policies
     */
    public PercolationModel(String filename, int gridType, int neighborMode, int edgePolicy) {
        super(filename, gridType, neighborMode, edgePolicy);
        setOpenCells();
    }

//    public PercolationModel (int numRows, int numCols) {
//        populateGameConditions();
//        int[][] states = new int[numRows][numCols];
//        setGrid(states, 0,0,0);
//        setOpenCells();
//    }

    /***
     * Constructor that randomly generates the grid by specifying the numbers of rows, columns, and threshold for satisfaction,
     * with default square grid, complete neighbors and finite edge policy.
     *
     * @param numCols is the number of columns of the grid
     * @param numRows is the number of rows of the grid
     */
    public PercolationModel(int numCols, int numRows) {
        super(numCols, numRows);
        setOpenCells();
    }

    /***
     * Constructor that randomly generates the grid by specifying the numbers of rows, columns,
     * with a specified grid shape, neighbor mode and edge policy.
     *
     * @param numCols is the number of columns of the grid
     * @param numRows is the number of rows of the grid
     * @param gridType gives one of square, triangle, and hexagon grid shapes.
     * @param neighborMode gives one of complete, cardinal (adjacent on the edge), and bottom half modes of neighbors
     * @param edgePolicy gives one of finite, toroidal, and cylindrical edge policies
     *
     */
    public PercolationModel(int numCols, int numRows, int gridType, int neighborMode, int edgePolicy) {
        super(numCols, numRows, gridType, neighborMode, edgePolicy);
        setOpenCells();
    }

    /***
     * For testing purpose
     *
     * @return a list of Cells whose currentStatus are open.
     */
    public List<Cell> getOpenCells() {
        return myOpenCells;
    }

    private void setOpenCells() {
        cellsToBeRemoved = new ArrayList<>();
        myOpenCells = new ArrayList<>();
        for (Point point : getGrid().getPoints()) {
            Cell cell = getGrid().getBoardCell(point);
            if (cell.getCurrentStatus() == OPEN) {
                myOpenCells.add(cell);
            }
        }
    }

    protected void setNumStatesOnBoard() {
        setNumStates(NUM_STATES);
    }

    @Override
    public void update() {
        boolean isSpanning = true;
        while (isSpanning) {
            isSpanning = false;
            for (Cell cell : myOpenCells) {
                isSpanning = isSpanning || applyRule(cell);
            }
            for (Cell cell : myOpenCells) {
                cell.changeStatus();
            }
            myOpenCells.removeAll(cellsToBeRemoved);
            cellsToBeRemoved.clear();
        }
    }

    @Override
    protected boolean applyRule(Cell cell) {
        // cell must be OPEN
        for (Cell neighbor : cell.getNeighborCells()) {
            if (neighbor != null && neighbor.getCurrentStatus() == PERCOLATED) {
                cell.setNextStatus(PERCOLATED);
                cellsToBeRemoved.add(cell);
                return true;
            }
        }
        return false;
    }

    // method is made for the frontend, but it has not been used yet
//    public boolean isPercolated () {
//        int r = getNumRows() - 1;
//        for (int c = 0; c < getNumCols(); c++) {
//            if (getCellStatus(c, r) == PERCOLATED) {
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public void changeCellOnClick(int x, int y) {
        Cell cell = getGrid().getBoardCell(x, y);
        if (cell.getXyPosition()[1] == 0) {
            cell.setCurrentStatus(PERCOLATED);
        } else if (cell.getCurrentStatus() == BLOCKED) {
            cell.setCurrentStatus(OPEN);
            myOpenCells.add(cell);
        }
    }

    @Override
    protected void populateGameConditions() {
        super.populateGameConditions();
        BLOCKED = retrieveIntProperty("PercolationBlocked");
        OPEN = retrieveIntProperty("PercolationOpen");
        PERCOLATED = retrieveIntProperty("PercolationPercolated");
    }
}
