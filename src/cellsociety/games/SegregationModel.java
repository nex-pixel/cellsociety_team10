package cellsociety.games;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Set;

public class SegregationModel extends Game {

    private int EMPTY;
    private int AGENT_X;
    private int AGENT_O;
    private int RANGE;
    private double myThreshold;
    private int myNumOfAgents;

    private List<Cell> myEmptyCells;

    public SegregationModel (int numRows, int numCols, double emptyRate, double agentXRate, double threshold) {
        EMPTY = getIntProperty("SegregationEmpty");
        AGENT_X = getIntProperty("SegregationAgentX");
        AGENT_O = getIntProperty("SegregationAgent0");
        RANGE = getIntProperty("SegregationRange");
        myEmptyCells = new ArrayList<>();
        myThreshold = threshold;
        myNumOfAgents = 0;
        int[][] randArray = new int[numRows][numCols];
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                double randStatus = Math.random();
                if (randStatus <= emptyRate){
                    randArray[r][c] = EMPTY;
                }
                else if (randStatus - emptyRate <= agentXRate * (1 - emptyRate)) {
                    randArray[r][c] = AGENT_X;
                    myNumOfAgents++;
                }
                else {
                    randArray[r][c] = AGENT_O;
                    myNumOfAgents++;
                }
            }
        }
        setGrid(randArray);
        setEmptyCells();
    }

    // Default constructor for testing purposes
    public SegregationModel (int[][] states, double threshold) {
        myThreshold = threshold;
        myEmptyCells = new ArrayList<>();
        setGrid(states);
        setEmptyCells();
    }

    public SegregationModel (SegregationModel copy) {
        super(copy);
        myThreshold = copy.myThreshold;
    }

    public double getThreshold () { return myThreshold; }

    private void setEmptyCells () {
        for (Point point: getGrid().getPoints()) {
            Cell cell = getGrid().getBoardCell(point);
            if (cell.getCurrentStatus() == EMPTY) {
                myEmptyCells.add(cell);
            }
        }
    }

    public List<Cell> getEmptyCells () { return myEmptyCells; }

    @Override
    public void update () {
        super.update();
    }

    @Override
    protected boolean applyRule (Cell cell) {
        if (cell.getCurrentStatus()!= EMPTY && !isSatisfied(cell)) {
            //reallocate the cell
            Random rand = new Random();
            Cell nextCell = myEmptyCells.get(rand.nextInt(myEmptyCells.size()));
            myEmptyCells.remove(nextCell);
            nextCell.setNextStatus(cell.getCurrentStatus());
            myEmptyCells.add(cell);
            cell.setNextStatus(EMPTY);
            return true;
        }
        return false;
    }

    public double getSatisfiedRate () {
        int numSatisfiedAgents = 0;
        for (Point point: getGrid().getPoints()) {
            if (isSatisfied(getGrid().getBoardCell(point))) {
                numSatisfiedAgents++;
            }
        }
        return numSatisfiedAgents / myNumOfAgents;
    }

    public boolean isSatisfied (Cell cell) {
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

    @Override
    public boolean equals (Object o) {
        return super.equals(o) && (myThreshold == ((SegregationModel) o).getThreshold());
    }
}
