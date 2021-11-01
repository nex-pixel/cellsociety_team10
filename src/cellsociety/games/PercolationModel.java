package cellsociety.games;

import cellsociety.components.Cell;
import cellsociety.components.HexagonGrid;
import cellsociety.components.SquareGrid;
import cellsociety.components.TriangleGrid;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PercolationModel extends Game {

    private int BLOCKED;
    private int OPEN;
    private int PERCOLATED;
    private int NUM_STATES = 3;
    private final int DEFAULT_GRID_CHOICE = 0;

    private List<Cell> myOpenCells;
    private List<Cell> cellsToBeRemoved;

    public PercolationModel (String filename) {
        super(filename);
        setOpenCells();
    }

    public PercolationModel (String filename, int gridType, int neighborMode, int edgePolicy) {
        super(filename, gridType, neighborMode, edgePolicy);
        setOpenCells();
    }

//    public PercolationModel (int numRows, int numCols) {
//        populateGameConditions();
//        int[][] states = new int[numRows][numCols];
//        setGrid(states, 0,0,0);
//        setOpenCells();
//    }

    public PercolationModel (int numCols, int numRows){
        super(numCols, numRows);
        setNumStatesOnBoard(NUM_STATES);
        setGrid(createRandomIntTwoDArray(numCols, numRows), DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE);
    }

    public PercolationModel (int numCols, int numRows, int gridType, int neighborMode, int edgePolicy) {
        super(numCols, numRows, gridType, neighborMode, edgePolicy);
        setNumStatesOnBoard(NUM_STATES);
        setGrid(createRandomIntTwoDArray(numCols, numRows), gridType, neighborMode, edgePolicy);
    }

    // method for testing purpose
    public List<Cell> getOpenCells () { return myOpenCells; }

    private void setOpenCells () {
        cellsToBeRemoved = new ArrayList<>();
        myOpenCells = new ArrayList<>();
        for (Point point: getGrid().getPoints()) {
            Cell cell = getGrid().getBoardCell(point);
            if (cell.getCurrentStatus() == OPEN) {
                myOpenCells.add(cell);
            }
        }
    }

    @Override
    public void update() {
        boolean isSpanning = true;
        while (isSpanning) {
            isSpanning = false;
            for (Cell cell: myOpenCells) {
                isSpanning = isSpanning || applyRule(cell);
            }
            for (Cell cell: myOpenCells) {
                cell.changeStatus();
            }
            myOpenCells.removeAll(cellsToBeRemoved);
            cellsToBeRemoved.clear();
        }
    }

    @Override
    protected boolean applyRule (Cell cell) {
        // cell must be OPEN
        for (Cell neighbor: cell.getNeighborCells()) {
            if (neighbor != null && neighbor.getCurrentStatus() == PERCOLATED) {
                cell.setNextStatus(PERCOLATED);
                cellsToBeRemoved.add(cell);
                return true;
            }
        }
        return false;
    }

    public boolean isPercolated () {
        int r = getNumRows() - 1;
        for (int c = 0; c < getNumCols(); c++) {
            if (getCellStatus(c, r) == PERCOLATED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void changeCellOnClick(Point point) {
        Cell cell = getGrid().getBoardCell(point);
        if (cell.getXyPosition()[1] == 0) {
            cell.setCurrentStatus(PERCOLATED);
        }
        else if (cell.getCurrentStatus() == BLOCKED) {
            cell.setCurrentStatus(OPEN);
            myOpenCells.add(cell);
        }
        update();
    }

    @Override
    protected void populateGameConditions () {
        super.populateGameConditions();
        BLOCKED = retrieveIntProperty("PercolationBlocked");
        OPEN = retrieveIntProperty("PercolationOpen");
        PERCOLATED = retrieveIntProperty("PercolationPercolated");
    }
}
