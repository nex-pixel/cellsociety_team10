package cellsociety.games;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PercolationModel extends Game {

    private int BLOCKED;
    private int OPEN;
    private int PERCOLATED;

    private List<Cell> myOpenCells;
    private List<Cell> cellsToBeRemoved;

    public PercolationModel (String filename) {
        super(filename);
        setOpenCells();
    }

    public PercolationModel (int numRows, int numCols) {
        populateGameConditions();
        int[][] states = new int[numRows][numCols];
        setGrid(states);
        setOpenCells();
    }

    private void setOpenCells () {
        cellsToBeRemoved = new ArrayList<>();
        myOpenCells = new ArrayList<>();
//        Map<Point, Cell> board = getGrid();
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
