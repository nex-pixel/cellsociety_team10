package cellsociety.games;

import cellsociety.components.Cell;
import cellsociety.components.Grid;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PercolationModel extends Game {

    private int BLOCKED = 0;
    private int OPEN = 1;
    private int PERCOLATED = 2;

    private List<Cell> myOpenCells;
    private List<Cell> cellsToBeRemoved;

    public PercolationModel (String filename) {
        super(filename);
        setOpenCells();
        BLOCKED = getIntProperty("PercolationBlocked");
        OPEN = getIntProperty("PercolationOpen");
        PERCOLATED = getIntProperty("PercolationPercolated");
    }

    public PercolationModel (int numRows, int numCols) {
        int[][] states = new int[numRows][numCols];
        myGrid = new Grid(states);
        setOpenCells();
    }

    private void setOpenCells () {
        cellsToBeRemoved = new ArrayList<>();
        myOpenCells = new ArrayList<>();
        Set<Point> points = myGrid.getPoints();
        for (Point point: points) {
            Cell cell = myGrid.getBoardCell(point);
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

    public void clickCell (Cell cell) {
        if (cell.getXyPosition()[1] == 0) {
            cell.setCurrentStatus(PERCOLATED);
        }
        else if (cell.getCurrentStatus() == BLOCKED) {
            cell.setCurrentStatus(OPEN);
            myOpenCells.add(cell);
        }
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
}
