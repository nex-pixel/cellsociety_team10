package cellsociety.games;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/***
 * The class models the Schelling's Model of Segregation
 *
 * @author Norah Tan, Haseeb Chaudhry
 */
public class SegregationModel extends Game {

    private int EMPTY;
    private int AGENT_X;
    private int AGENT_O;
    private int RANGE;
    private double myThreshold;
    private int myNumOfAgents;
    private static final int NUM_STATES = 3;
    private static final int DEFAULT_GRID_CHOICE = 0;

    private List<Cell> myEmptyCells;

    /***
     * Constructor that takes in a CSV file for grid initialization,
     * with default square grid, complete neighbors and finite edge policy.
     *
     * @param filename gives the CSV figuration file for the grid
     * @param threshold is the rate of satisfaction based on the percentage of surrounded agents that are like itself
     */
    public SegregationModel(String filename, double threshold) {
        super(filename);
        setEmptyCells(threshold);
    }

    /***
     * Constructor that takes in a CSV file for grid initialization,
     * with a specified grid shape, neighbor mode and edge policy.
     *
     * @param filename gives the CSV figuration file for the grid
     * @param gridType gives one of square, triangle, and hexagon grid shapes.
     * @param neighborMode gives one of complete, cardinal (adjacent on the edge), and bottom half modes of neighbors
     * @param edgePolicy gives one of finite, toroidal, and cylindrical edge policies
     * @param threshold is the rate of satisfaction based on the percentage of surrounded agents that are like itself
     */
    public SegregationModel(String filename, int gridType, int neighborMode, int edgePolicy, double threshold) {
        super(filename, gridType, neighborMode, edgePolicy);
        setEmptyCells(threshold);
    }

    /***
     * Constructor that randomly generates the grid by specifying the numbers of rows, columns,
     * the percentage of empty Cells, the ratio of agent X and agent O, and threshold for satisfaction,
     * with default square grid, complete neighbors and finite edge policy.
     *
     * @param numRows is the number of rows of the grid
     * @param numCols is the number of columns of the grid
     * @param emptyRate is the percentage of empty Cells in the grid
     * @param agentXRate is the percentage of agent X Cells among all non-empty Cells in the grid
     * @param threshold is the rate of satisfaction based on the percentage of surrounded agents that are like itself
     */
    public SegregationModel(int numRows, int numCols, double emptyRate, double agentXRate, double threshold) {
        populateGameConditions();
        myNumOfAgents = 0;
        int[][] randArray = new int[numRows][numCols];
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                double randStatus = Math.random();
                if (randStatus <= emptyRate) {
                    randArray[r][c] = EMPTY;
                } else if (randStatus - emptyRate <= agentXRate * (1 - emptyRate)) {
                    randArray[r][c] = AGENT_X;
                    myNumOfAgents++;
                } else {
                    randArray[r][c] = AGENT_O;
                    myNumOfAgents++;
                }
            }
        }
        setGrid(randArray, DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE);
        setEmptyCells(threshold);
    }

    /***
     * Constructor that randomly generates the grid by specifying the numbers of rows, columns, and threshold for satisfaction,
     * with default square grid, complete neighbors and finite edge policy.
     *
     * @param numCols is the number of columns of the grid
     * @param numRows is the number of rows of the grid
     * @param threshold is the rate of satisfaction based on the percentage of surrounded agents that are like itself
     */
    public SegregationModel(int numCols, int numRows, double threshold) {
        super(numCols, numRows);
        setEmptyCells(threshold);
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
     * @param threshold is the rate of satisfaction based on the percentage of surrounded agents that are like itself
     *
     */
    public SegregationModel(int numCols, int numRows, int gridType, int neighborMode, int edgePolicy, double threshold) {
        super(numCols, numRows, gridType, neighborMode, edgePolicy);
        setEmptyCells(threshold);
    }

    /***
     * Constructor for testing purpose.
     *
     * @param states specifies the grid
     * @param threshold is the rate of satisfaction based on the percentage of surrounded agents that are like itself
     */
    public SegregationModel(int[][] states, double threshold) {
        setGrid(states, DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE);
        setEmptyCells(threshold);
    }

    /***
     * Constructor for testing purpose.
     *
     * @param copy is the SegregationModel that we want to copy from
     */
    public SegregationModel(SegregationModel copy) {
        super(copy);
        myThreshold = copy.myThreshold;
    }

    protected void setNumStatesOnBoard() {
        setNumStates(NUM_STATES);
    }

    private void setEmptyCells(double threshold) {
        myThreshold = threshold;
        myEmptyCells = new ArrayList<>();
        for (Point point : getGrid().getPoints()) {
            Cell cell = getGrid().getBoardCell(point);
            if (cell.getCurrentStatus() == EMPTY) {
                myEmptyCells.add(cell);
            }
        }
    }

    /***
     * For testing purpose
     *
     * @return a list of Cells whose currentStatus are empty.
     */
    public List<Cell> getEmptyCells() {
        return myEmptyCells;
    }

    /***
     * Setter method that changes the threshold
     *
     * @param newThreshold is the newThreshold we want the model to have
     */
    public void setThreshold(double newThreshold) {
        myThreshold = newThreshold;
    }

    /***
     * Getter method that gives the threshold
     *
     * @return the threshold
     */
    public double getThreshold() {
        return myThreshold;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    protected boolean applyRule(Cell cell) {
        if (cell.getCurrentStatus() != EMPTY && !isSatisfied(cell)) {
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

    // method made for the frontend, but it has not been used yet
//    public double getSatisfiedRate () {
//        int numSatisfiedAgents = 0;
//        for (Point point: getGrid().getPoints()) {
//            if (isSatisfied(getGrid().getBoardCell(point))) {
//                numSatisfiedAgents++;
//            }
//        }
//        return numSatisfiedAgents / myNumOfAgents;
//    }

    /***
     * This method tells us whether a Cell is satisfied with its current location.
     *
     * @param cell is the Cell we want to know about
     * @return whether this Cell is satisfied or not
     */
    public boolean isSatisfied(Cell cell) {
        int numOccupiedNeighbors = 0;
        int numSameNeighbors = 0;
        for (Cell neighbor : cell.getNeighborCells()) {
            if (neighbor != null && neighbor.getCurrentStatus() != EMPTY) {
                numOccupiedNeighbors++;
                if (neighbor.getCurrentStatus() == cell.getCurrentStatus()) {
                    numSameNeighbors++;
                }
            }
        }
        return (double) numSameNeighbors / numOccupiedNeighbors > myThreshold;
    }

//    @Override
//    public void changeCellOnClick(int x, int y) {
//        super.changeCellOnClick(x, y);
//    }

    @Override
    protected void populateGameConditions() {
        super.populateGameConditions();
        EMPTY = retrieveIntProperty("SegregationEmpty");
        AGENT_X = retrieveIntProperty("SegregationAgentX");
        AGENT_O = retrieveIntProperty("SegregationAgent0");
        RANGE = retrieveIntProperty("SegregationRange");
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && (myThreshold == ((SegregationModel) o).getThreshold());
    }
}
