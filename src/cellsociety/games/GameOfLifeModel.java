package cellsociety.games;

import cellsociety.components.Cell;

import java.util.List;

/***
 * The class models the Game of Life
 *
 * @author Norah Tan, Haseeb Chaudhry
 */

public class GameOfLifeModel extends Game {
    private int ALIVE;
    private int DEAD;
    private static final int NUM_STATES = 2;


    /***
     * Constructor that takes in a CSV file for grid initialization,
     * with default square grid, complete neighbors and finite edge policy.
     *
     * @param filename gives the CSV figuration file for the grid
     */
    public GameOfLifeModel(String filename) {
        super(filename);
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
    public GameOfLifeModel(String filename, int gridType, int neighborMode, int edgePolicy) {
        super(filename, gridType, neighborMode, edgePolicy);
    }

    /***
     * Constructor for testing purpose.
     *
     * @param copy is the GameOfLifeModel that we want to copy from
     */
    public GameOfLifeModel(GameOfLifeModel copy) {
        super(copy);
    }

    /***
     * Constructor for testing purpose.
     *
     * @param states specifies the grid
     */
    public GameOfLifeModel(int[][] states) {
        super(states);
    }

    /***
     * Constructor that randomly generates the grid by specifying the numbers of rows, columns, and threshold for satisfaction,
     * with default square grid, complete neighbors and finite edge policy.
     *
     * @param numCols is the number of columns of the grid
     * @param numRows is the number of rows of the grid
     */
    public GameOfLifeModel(int numCols, int numRows) {
        super(numCols, numRows);
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
    public GameOfLifeModel(int numCols, int numRows, int gridType, int neighborMode, int edgePolicy) {
        super(numCols, numRows, gridType, neighborMode, edgePolicy);
    }

    protected void setNumStatesOnBoard() {
        setNumStates(this.NUM_STATES);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    protected boolean applyRule(Cell cell) {
        List<Cell> neighbors = cell.getNeighborCells();
        int numCellsAlive = 0;
        for (Cell cellNextDoor : neighbors) {
            if (cellNextDoor != null && cellNextDoor.getCurrentStatus() == ALIVE) {
                numCellsAlive++;
            }
        }
        if (cell.getCurrentStatus() == ALIVE) {
            cell.setNextStatus(numCellsAlive == 2 || numCellsAlive == 3 ? 1 : 0);
        } else {
            cell.setNextStatus(numCellsAlive == 3 ? 1 : 0);
        }
        return true;
    }

//    @Override
//    public void changeCellOnClick(int x, int y) {
//        super.changeCellOnClick(x, y);
//    }

    @Override
    protected void populateGameConditions() {
        super.populateGameConditions();
        DEAD = retrieveIntProperty("GameOfLifeDead");
        ALIVE = retrieveIntProperty("GameOfLifeAlive");
    }
}
