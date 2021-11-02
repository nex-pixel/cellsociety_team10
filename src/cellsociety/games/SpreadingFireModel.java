package cellsociety.games;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.List;

/***
 * The class models the Spreading Of Fire
 *
 * @author Norah Tan, Haseeb Chaudhry
 */
public class SpreadingFireModel extends Game {
    private int EMPTY;
    private int TREE;
    private int BURNING;
    private double probCatch;
    private double probGrow;
    private boolean firePresentInGrid = true;
    private int expandGridByInt;
    private static final int NUM_STATES = 3;

    /***
     * Constructor that takes in a CSV file for grid initialization,
     * with default square grid, complete neighbors and finite edge policy.
     *
     * @param filename gives the CSV figuration file for the grid
     */
    public SpreadingFireModel(String filename) {
        super(filename);
        getGrid().expandGrid(expandGridByInt, expandGridByInt, expandGridByInt, expandGridByInt);
    }

    /***
     * Constructor for testing purpose.
     *
     * @param filename gives the CSV figuration file for the grid
     * @param TEST is an indicator
     */
    public SpreadingFireModel(String filename, boolean TEST) {
        super(filename);
        if (TEST == false) {
            getGrid().expandGrid(expandGridByInt, expandGridByInt, expandGridByInt, expandGridByInt);
        }
    }

    /***
     * Constructor that randomly generates the grid by specifying the numbers of rows, columns, and threshold for satisfaction,
     * with default square grid, complete neighbors and finite edge policy.
     *
     * @param numCols is the number of columns of the grid
     * @param numRows is the number of rows of the grid
     */
    public SpreadingFireModel(int numCols, int numRows) {
        super(numCols, numRows);
        getGrid().expandGrid(expandGridByInt, expandGridByInt, expandGridByInt, expandGridByInt);
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
    public SpreadingFireModel(int numCols, int numRows, int gridType, int neighborMode, int edgePolicy) {
        super(numCols, numRows, gridType, neighborMode, edgePolicy);
        getGrid().expandGrid(expandGridByInt, expandGridByInt, expandGridByInt, expandGridByInt);
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
    public SpreadingFireModel(String filename, int gridType, int neighborMode, int edgePolicy) {
        super(filename, gridType, neighborMode, edgePolicy);
        getGrid().expandGrid(expandGridByInt, expandGridByInt, expandGridByInt, expandGridByInt);
    }

    /***
     * Setter method that changes the probability of having fire
     *
     * @param probability is the new probability
     */
    public void setProbOfFire(double probability) {
        probCatch = probability;
    }

    /***
     * Setter method that changes the probability of growing new trees
     * @param probability is the new probability
     */
    public void setProbGrowNewTrees(double probability) {
        probGrow = probability;
    }

    protected void setNumStatesOnBoard() {
        setNumStates(NUM_STATES);
    }

    @Override
    public void update() {
        checkForFire();
        if (firePresentInGrid) {
            super.update();
        }
    }

    private void checkForFire() {
        firePresentInGrid = false;
        for (Point point : getGrid().getPoints()) {
            if (getGrid().getBoardCell(point).getCurrentStatus() == BURNING) {
                firePresentInGrid = true;
            }
        }
    }

    @Override
    protected boolean applyRule(Cell cell) {
        List<Cell> neighbors = cell.getNeighborCells();
        if (cell.getCurrentStatus() == EMPTY) {
            int willATreeGrow = willNewTreeGrow(neighbors);
            cell.setNextStatus(willATreeGrow);
        } else if (cell.getCurrentStatus() == TREE) {
            int willItCatchFire = spread(neighbors);
            cell.setNextStatus(willItCatchFire);
        } else if (cell.getCurrentStatus() == BURNING) {
            cell.setNextStatus(EMPTY);
        }
        return true;
    }

    private int spread(List<Cell> neighbors) {
        double randNumber = Math.random();
        if (checkNumCellsThisCase(BURNING, neighbors).size() > 0) {
            if (randNumber < probCatch) {
                return BURNING;
            }
        }
        return TREE;
    }

    private int willNewTreeGrow(List<Cell> neighbors) {
        double randomGrow = Math.random();
        if (checkNumCellsThisCase(TREE, neighbors).size() > 0) {
            if (randomGrow < probGrow) {
                return TREE;
            }
        }
        return EMPTY;
    }

//    @Override
//    public void changeCellOnClick(int x, int y) {
//        super.changeCellOnClick(x, y);
//    }

    @Override
    protected void populateGameConditions() {
        super.populateGameConditions();
        probCatch = myGameDataReader.getDoubleProperty("SpreadingFireProbSpread");
        ;
        probGrow = myGameDataReader.getDoubleProperty("SpreadingFireProbGrow");
        expandGridByInt = retrieveIntProperty("SpreadingFireExpandGridBy");
        EMPTY = retrieveIntProperty("SpreadingFireEmpty");
        TREE = retrieveIntProperty("SpreadingFireTree");
        BURNING = retrieveIntProperty("SpreadingFireBurning");
    }
}
