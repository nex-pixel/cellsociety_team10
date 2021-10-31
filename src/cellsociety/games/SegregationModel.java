package cellsociety.games;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class SegregationModel extends Game {

    private int EMPTY;
    private int AGENT_X;
    private int AGENT_O;
    private int RANGE;
    private double myThreshold;
    private int myNumOfAgents;

    private List<Cell> myEmptyCells;

    public SegregationModel (String filename, double threshold) {
        super(filename);
        setEmptyCells(threshold);
    }

    public SegregationModel (String filename, int gridType, int neighborMode, int edgePolicy, double threshold) {
        super(filename, gridType, neighborMode, edgePolicy);
        setEmptyCells(threshold);
    }

    public SegregationModel (int numRows, int numCols, double emptyRate, double agentXRate, double threshold) {
        populateGameConditions();
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
        setGrid(randArray, 0 , 0, 0);
        setEmptyCells(threshold);
    }

    // Default constructor for testing purposes
    public SegregationModel (int[][] states, double threshold) {
        setGrid(states, 0, 0, 0);
        setEmptyCells(threshold);
    }

    public SegregationModel (SegregationModel copy) {
        super(copy);
        myThreshold = copy.myThreshold;
    }

    private void setEmptyCells (double threshold) {
        myThreshold = threshold;
        myEmptyCells = new ArrayList<>();
        for (Point point: getGrid().getPoints()) {
            Cell cell = getGrid().getBoardCell(point);
            if (cell.getCurrentStatus() == EMPTY) {
                myEmptyCells.add(cell);
            }
        }
    }

    public List<Cell> getEmptyCells () { return myEmptyCells; }
    public void setThreshold (double newThreshold){
        myThreshold = newThreshold;
    }
    public double getThreshold () { return myThreshold; }

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
    public void changeCellOnClick(Point point) {
        Cell cell = getGrid().getBoardCell(point);
        cell.setCurrentStatus((cell.getCurrentStatus() + 1) % 3);
        update();
    }

    @Override
    protected void populateGameConditions () {
        super.populateGameConditions();
        EMPTY = retrieveIntProperty("SegregationEmpty");
        AGENT_X = retrieveIntProperty("SegregationAgentX");
        AGENT_O = retrieveIntProperty("SegregationAgent0");
        RANGE = retrieveIntProperty("SegregationRange");
    }

    @Override
    public boolean equals (Object o) {
        return super.equals(o) && (myThreshold == ((SegregationModel) o).getThreshold());
    }
}
