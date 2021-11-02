package cellsociety.games;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/***
 * The class models the Wa-Tor-World
 *
 * @author Norah Tan, Haseeb Chaudhry
 */

public class WaTorWorldModel extends Game {
    private int EMPTY;
    private int FISH;
    private int SHARK;
    private int SHARK_STARTING_ENERGY;
    private int STARTING_VAL;
    private int REPRODUCE_VAL;
    private int ENERGY_FROM_EATING_FISH;
    private int ENERGY_LOST_FROM_MOVING;
    private static final int NUM_STATES = 3;

    /***
     * Constructor that takes in a CSV file for grid initialization,
     * with default square grid, complete neighbors and finite edge policy.
     *
     * @param filename gives the CSV figuration file for the grid
     */
    public WaTorWorldModel(String filename) {
        super(filename);
        setSharkEnergyValues();
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
    public WaTorWorldModel(String filename, int gridType, int neighborMode, int edgePolicy) {
        super(filename, gridType, neighborMode, edgePolicy);
        setSharkEnergyValues();
    }

    /***
     * Constructor that randomly generates the grid by specifying the numbers of rows, columns, and threshold for satisfaction,
     * with default square grid, complete neighbors and finite edge policy.
     *
     * @param numCols is the number of columns of the grid
     * @param numRows is the number of rows of the grid
     */
    public WaTorWorldModel(int numCols, int numRows) {
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
    public WaTorWorldModel(int numCols, int numRows, int gridType, int neighborMode, int edgePolicy) {
        super(numCols, numRows, gridType, neighborMode, edgePolicy);
    }

    private void setSharkEnergyValues() {
        for (Point point : getGrid().getPoints()) {
            Cell cell = getGrid().getBoardCell(point);
            if (cell.getCurrentStatus() == SHARK) {
                setSharkMiscellaneousDefault(cell);
            } else {
                setFishMiscellaneousDefault(cell);
            }
        }
    }

    private void setSharkMiscellaneousDefault(Cell cell) {
        cell.setMiscellaneousVal(Arrays.asList(STARTING_VAL, SHARK_STARTING_ENERGY));
    }

    private void setFishMiscellaneousDefault(Cell cell) {
        cell.setMiscellaneousVal(Arrays.asList(STARTING_VAL, STARTING_VAL));
    }

    protected void setNumStatesOnBoard() {
        setNumStates(NUM_STATES);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    protected boolean applyRule(Cell cell) {
        List<Cell> neighbors = cell.getNeighborCells();
        int stepsAlive = cell.getMiscellaneousVal().get(0);
        int energy = cell.getMiscellaneousVal().get(1);
        if (cell.getCurrentStatus() == FISH) {
            fishMovement(cell, neighbors, stepsAlive, energy);
        } else if (cell.getCurrentStatus() == SHARK) {
            sharkMovement(cell, neighbors, stepsAlive, energy);
        }
        return true;
    }

    private void fishMovement(Cell cell, List<Cell> neighbors, int stepsAlive, int energy) {
        List<Cell> adjNeigh = checkNumCellsThisCase(EMPTY, neighbors);
        if (adjNeigh.size() > 0) {
            Cell chosenNextCell = adjNeigh.get(getRandomInt(adjNeigh.size() - 1));
            reproduceCell(cell, chosenNextCell, stepsAlive, energy, STARTING_VAL);
        }
    }

    private void sharkMovement(Cell cell, List<Cell> neighbors, int stepsAlive, int energy) {
        List<Cell> adjFishNeigh = checkNumCellsThisCase(FISH, neighbors);
        List<Cell> adjEmptyNeigh = checkNumCellsThisCase(EMPTY, neighbors);
        if (adjFishNeigh.size() > 0) {
            Cell chosenNextCell = adjFishNeigh.get(getRandomInt(adjFishNeigh.size() - 1));
            sharkReproduceCell(cell, chosenNextCell, stepsAlive, energy + ENERGY_FROM_EATING_FISH);
        } else if (adjEmptyNeigh.size() > 0) {
            Cell chosenNextCell = adjEmptyNeigh.get(adjEmptyNeigh.size() - 1);
            sharkReproduceCell(cell, chosenNextCell, stepsAlive, energy);
        }
    }

    private int getRandomInt(int max) {
        return (int) (Math.random() * (max + 1));
    }

    private void sharkReproduceCell(Cell cell, Cell chosenCell, int stepsAlive, int energy) {
        energy -= ENERGY_LOST_FROM_MOVING;
        if (energy > 0) {
            reproduceCell(cell, chosenCell, stepsAlive, energy, SHARK_STARTING_ENERGY);
        } else if (energy <= 0) {
            cell.setCurrentStatus(EMPTY);
            cell.setMiscellaneousVal(Arrays.asList(STARTING_VAL, STARTING_VAL));
        }
    }


    private void reproduceCell(Cell cell, Cell chosenCell, int stepsAlive, int energy, int startingEnergy) {
        chosenCell.setNextStatus(cell.getCurrentStatus());
        if (stepsAlive <= REPRODUCE_VAL) {
            cell.setNextStatus(EMPTY);
            stepsAlive += 1;
            chosenCell.setMiscellaneousVal(Arrays.asList(stepsAlive, energy));
        } else if (stepsAlive > REPRODUCE_VAL) {
            cell.setNextStatus(cell.getCurrentStatus());
            chosenCell.setMiscellaneousVal(Arrays.asList(STARTING_VAL, energy));
        }
        cell.setMiscellaneousVal(Arrays.asList(STARTING_VAL, startingEnergy));
    }

    @Override
    public void changeCellOnClick(int x, int y) {
        super.changeCellOnClick(x, y);
        Cell cell = getGrid().getBoardCell(x, y);
        if (cell.getCurrentStatus() == FISH) {
            setFishMiscellaneousDefault(cell);
        } else if (cell.getCurrentStatus() == SHARK) {
            setSharkMiscellaneousDefault(cell);
        }
    }

    @Override
    protected void populateGameConditions() {
        super.populateGameConditions();
        EMPTY = retrieveIntProperty("WatorWorldEmpty");
        FISH = retrieveIntProperty("WatorWorldFish");
        SHARK = retrieveIntProperty("WatorWorldShark");
        SHARK_STARTING_ENERGY = retrieveIntProperty("WatorWorldSharkStartingEnergy");
        STARTING_VAL = retrieveIntProperty("WatorWorldRepStartVal");
        REPRODUCE_VAL = retrieveIntProperty("WatorWorldReproduceNum");
        ENERGY_FROM_EATING_FISH = retrieveIntProperty("WatorWorldEnergyFromFish");
        ENERGY_LOST_FROM_MOVING = retrieveIntProperty("WatorWorldEnergyLostFromMoving");
    }
}
