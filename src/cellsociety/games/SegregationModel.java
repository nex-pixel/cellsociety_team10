package cellsociety.games;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.List;

public class SegregationModel extends Game {

    private int EMPTY = 0;
    private int AGENT_X = 1;
    private int AGENT_O = 2;
    private int RANGE = 3;
    private double myThreshold;

    private List<Cell> myEmptyCells;

    public SegregationModel (int numRows, int numCols, double threshold) {
        myEmptyCells = new ArrayList<>();
        myThreshold = threshold;
        Random rand = new Random();
        int[][] randArray = new int[numRows][numCols];
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                randArray[r][c] = rand.nextInt(RANGE);
            }
        }
        setGrid(randArray);
        setEmptyCells();
    }

    private void setEmptyCells () {
        for (Point point: getGrid().keySet()) {
            Cell cell = getGrid().get(point);
            if (cell.getCurrentStatus() == EMPTY) {
                myEmptyCells.add(cell);
            }
        }
    }

    @Override
    public void update () {
        Map<Point, Cell> board = myGrid.getBoard();
        for (Point point: board.keySet()) {
            applyRule(board.get(point));
        }
        for (Point point: board.keySet()) {
            board.get(point).changeStatus();
        }
    }

    @Override
    protected void applyRule (Cell cell) {
        if (!isSatisfied(cell)) {
            //reallocate the cell
            Random rand = new Random();
            Cell nextCell = myEmptyCells.get(rand.nextInt(myEmptyCells.size()));
            myEmptyCells.remove(nextCell);
            nextCell.setNextStatus(cell.getCurrentStatus());
            myEmptyCells.add(cell);
            cell.setNextStatus(EMPTY);
        }
    }

    private boolean isSatisfied (Cell cell) {
        int numOccupiedNeighbors = 0;
        int numSameNeighbors = 0;
        for (Cell neighbor: cell.getNeighborCells()) {
            if (neighbor != null && neighbor.getCurrentStatus() != EMPTY) {
                numOccupiedNeighbors++;
                if (neighbor.getCurrentStatus() == cell.getCurrentStatus()) {
                    numSameNeighbors++;
                }
            }
        }
        return (double) numSameNeighbors / numOccupiedNeighbors > myThreshold;
    }
}
