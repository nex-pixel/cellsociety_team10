package cellsociety.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * The class creates a cell object to be used inside of grid, the cell object stores the position of the cell,
 * the current state, future state, neighbor cells, and extraneous tracking values
 * Dependencies: ArrayList, Arrays, List
 * Assumptions: the cell will only be 2D x and y no z, and will have a current next only no back, Miscellaneous values can be anything,
 * so Game needs to be consistent with them
 * Example: Game of life cell
 *
 * @author Norah Tan, Haseeb Chaudhry
 */
public class Cell {
    private final int[] xyPosition = new int[2];
    private List<Cell> neighborCells;
    private int currentStatus;
    private int nextStatus;
    private List<Integer> miscellaneousVal;

    /**
     * This is the default constructor that is needed to create a new cell. It cannot be created anyother way since this information
     * is vital when the cell exists.
     *
     * @param statusPassedIn the current state value to be set for cell
     * @param xPosition the x position of cell
     * @param yPosition the y position of cell
     */
    public Cell(int statusPassedIn, int xPosition, int yPosition) {
        setXyPosition(xPosition, yPosition);
        neighborCells = new ArrayList<>();
        currentStatus = statusPassedIn;
        nextStatus = currentStatus;
        miscellaneousVal = new ArrayList<>();
    }

    /**
     * This method basically steps the cell. When the update method is called in the game itself. Easier to seperate conditions applied
     * to each generation and avoid overlap between them
     */
    public void changeStatus() {
        currentStatus = nextStatus;
    }

    /**
     * This method clears the neighbors of cells in order for new neighbors to be applied if grid is reintialized
     */
    public void clearNeighborCells() {
        neighborCells = new ArrayList<>();
    }

    /**
     * This method basically sets the next status or value that the cell will switch into for the next generation
     *
     * @param newStatus the next status of the cell that will be set which is an integer
     */
    public void setNextStatus(int newStatus) {
        nextStatus = newStatus;
    }

    /**
     * This method changes the current value if something happens like the cell is clicked
     *
     * @param newCurrentStatus the new value integer for this generation
     */
    public void setCurrentStatus(int newCurrentStatus) {
        currentStatus = newCurrentStatus;
        nextStatus = newCurrentStatus;
    }

    /**
     * This method sets a new x and y position for the cell. This is useful if the grid is expanded and in order to maintain
     * only positive x and y values we can set new position for existing cells
     *
     * @param xPosition new passed in x position for cell integer
     * @param yPosition new passed in y position for cell integer
     */
    public void setXyPosition(int xPosition, int yPosition) {
        xyPosition[0] = xPosition;
        xyPosition[1] = yPosition;
    }

    /**
     * This method sets a passed in list of values as the new miscellaneous values. This is useful we want the list to be reset
     * to a certain format or recode value such as initializing these miscellaneous values again.
     *
     * @param valueList Passed in list of Integer values
     */
    public void setMiscellaneousVal(List<Integer> valueList) {
        miscellaneousVal = valueList;
    }

    /**
     * This method sets a new passed in list of neighbor cells. This is helpful in adding new neighbors or resetting the neighbor cells
     * to be specific ones
     *
     * @param neighborCellsPassedIn Passed in list of Integer value
     */
    public void setNeighborCells(List<Cell> neighborCellsPassedIn) {
        neighborCells = neighborCellsPassedIn;
    }

    /**
     * This method returns the current x and y position as an integer array
     *
     * @return integer array of xy position in that order
     */
    public int[] getXyPosition() {
        return xyPosition;
    }

    /**
     * This method returns the current status of the cell
     *
     * @return returns integer representing the current status of the cell
     */
    public int getCurrentStatus() {
        return currentStatus;
    }

    /**
     * This method returns the miscellaneous values list for the cell to be used as needed. Useful for algorithimic applicaiton
     * of rules, but not for cell to manipulate or assume to be a certain format
     *
     * @return the Integer List of miscellaneous values
     */
    public List<Integer> getMiscellaneousVal() {
        return miscellaneousVal;
    }

    /**
     * This method returns the list of neighbors this cell has been assigned.
     *
     * @return List of Cells --> neighbors determined by grid
     */
    public List<Cell> getNeighborCells() {
        return neighborCells;
    }

    /**
     * This method overrides the .equals method so cells are compared based on value and x and y position in order to determine they are
     * the same cell
     *
     * @param obj The other cell to compare to
     * @return Return true for cells are same otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        final Cell other = (Cell) obj;
        if (Arrays.equals(xyPosition, other.xyPosition) && this.currentStatus == other.currentStatus) {
            return true;
        }
        return false;
    }

    /**
     * This method is used to display the values of the cell with x and y position in that order to make bug testing easier
     *
     * @return string of values for cell
     */
    @Override
    public String toString() {
        return xyPosition[0] + " " + xyPosition[1] + " with status " + currentStatus;
    }
}
