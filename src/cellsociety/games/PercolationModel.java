package cellsociety.games;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PercolationModel extends Game {

    private int BLOCKED = 0;
    private int OPEN = 1;
    private int PERCOLATED = 2;

    private List<Cell> myOpenCells;

    public PercolationModel (String filename) {
        super(filename);
        myOpenCells = new ArrayList<>();
        setOpenCells();
    }

    private void setOpenCells () {
        Map<Point, Cell> board = getGrid();
        for (Point point: board.keySet()) {
            Cell cell = board.get(point);
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
        }
    }

    @Override
    protected boolean applyRule (Cell cell) {
        // cell must be OPEN
        for (Cell neighbor: cell.getNeighborCells()) {
            if (neighbor != null && neighbor.getCurrentStatus() == PERCOLATED) {
                cell.setNextStatus(PERCOLATED);
                myOpenCells.remove(cell);
                return true;
            }
        }
        return false;
    }

    public void openCell (Cell cell) {
        if (cell.getXyPosition()[1] == 0) {
            cell.setCurrentStatus(PERCOLATED);
        }
        else if (cell.getCurrentStatus() == BLOCKED) {
            cell.setCurrentStatus(OPEN);
            myOpenCells.add(cell);
        }
    }

    public boolean isPercolated () {
        int r = getMyGrid().getNumRows() - 1;
        for (int c = 0; c < getMyGrid().getNumCols(); c++) {
            if (getGrid().get(new Point(c, r)).getCurrentStatus() == PERCOLATED) {
                return true;
            }
        }
        return false;
    }
}
