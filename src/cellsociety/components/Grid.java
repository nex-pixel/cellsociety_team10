package cellsociety.components;


import java.awt.*;
import java.util.*;


/***
 * The class creates an abstract grid object made of cells connected to each other on a hashmap data structure. The hashmap has key of point
 * objects (Point store x and y value) and values of Cell objects(discussed in cell class). This also handles connecting the cells by
 * determining their neighbors. It also expands itself when needed by creating new empty cells. The grid also handles the cell data,
 * by providing it if needed to game.
 *
 * The class is call by sub grid classes to create a specific grid with certain cell shapes
 * Dependencies: util, awt
 * Assumptions: The grid will be a 2D grid. It will only have a certain neighbor type and edge type, and be initialized in a certain way.
 * Example: game of life grid of cells
 *
 * @author Norah Tan, Haseeb Chaudhry
 */
public abstract class Grid {
    private int myNumRows, myNumCols;
    private int myMaxRow, myMaxCol;
    private Map<Point, Cell> myBoard;
    private int[] myNeighborRows; //To determine neighbor cell locations by using integer displacement in that direction
    private int[] myNeighborCols; //same as above but for columns
    private int myEdgePolicy;
    private int myNeighborMode;
    private int EDGE_POLICY_FINITE = 0;
    private int EDGE_POLICY_TORUS = 1;
    private int EDGE_POLICY_CYLINDER = 2;
    protected PropertiesReader myReader;
    private final String neighborDataFile = "cellsociety.resources.gameData.NeighborPositionData";


    /**
     * This is the default constructor for the grid method which is called upon by subclasses to create their specific grids
     *
     * @param states int[][] for the grid values to be created and stored in cells
     * @param neighborMode what type of neighbor's the cell will store determined by passed in int
     * @param edgePolicy what type of grid it will be on edge (wrap around or static) determined by number
     */
    public Grid(int[][] states, int neighborMode, int edgePolicy) {
        populateNeighborData();
        myNumRows = states.length;
        myNumCols = states[0].length;
        myNeighborMode = neighborMode;
        myEdgePolicy = edgePolicy;
        myBoard = new HashMap<>();

        myMaxRow = myNumRows;
        assignMaxCol();

        initializeBoard(states);
        initializeNeighbors();
        populateNeighborData();
    }

    /**
     * This method creates the HashMap for the board in grid object. It iterates through the passed in values for cells that exist in a 2D array
     * and iterates through them to create each cell and point pair to store
     *
     * @param states the int[][] that contains cell initial values
     */
    protected void initializeBoard(int[][] states) {
        for (int rowIndex = 0; rowIndex < myNumRows; rowIndex++) {
            for (int colIndex = 0; colIndex < myNumCols; colIndex++) {
                Point point = new Point(colIndex, rowIndex);
                Cell cell = new Cell(states[rowIndex][colIndex], colIndex, rowIndex);
                myBoard.put(point, cell);
            }
        }
    }

    /**
     * The method changes the applied edge policy such as going from a finite grid to a torus grid by reinitalizing neighbors with
     * the new condition
     *
     * @param newEdgePolicy int to determine what the new edge policy is
     */
    public void changeEdgePolicy(int newEdgePolicy) {
        myEdgePolicy = newEdgePolicy;
        initializeNeighbors();
    }

    /**
     * The method changes the applied neighbor mode such as going from all neighbors around the cell to only neighbors connected
     * to it by their edges
     *
     * @param newNeighborMode int to determine what the neighbor mdoe is currently
     */
    public void changeNeighborMode(int newNeighborMode) {
        myNeighborMode = newNeighborMode;
        initializeNeighbors();
    }

    /**
     * The method return the number of rows in order to allow for encapsulation
     *
     * @return number of rows
     */
    public int getNumRows() {
        return myNumRows;
    }

    /**
     * The method return the number of columns in order to allow for encapsulation
     *
     * @return number of columns
     */
    public int getNumCols() {
        return myNumCols;
    }

    /**
     * This method sets the number of rows if grid is modified
     *
     * @param newNumRows new int number of rows
     */
    public void setMyNumRows(int newNumRows) {
        myNumRows = newNumRows;
    }

    /**
     * This method sets the number of columns if grid is modified
     *
     * @param newNumCols new int number of columns
     */
    public void setMyNumCols(int newNumCols) {
        myNumCols = newNumCols;
    }

    /**
     * This method sets the number of columns in order to add all the neighbors possible for current neighbor mode
     *
     * @param maxCol new int number of columns that exist max
     */
    protected void setMaxCol(int maxCol) {
        myMaxCol = maxCol;
    }

    /**
     * This method allows for encapsulation yet allowing us to change the myMaxCol in this class y calling setMaxCol from other classe.
     */
    protected void assignMaxCol() {
        setMaxCol(myNumCols);
    }

    /**
     * This method allows for the output of what edge policy is being currently used
     *
     * @return int representing edge policy
     */
    public int getEdgePolicy() {
        return myEdgePolicy;
    }

    /**
     * This method allows for the output of what neighbor mode is being currently used
     *
     * @return int representing neighbor mode
     */

    public int getNeighborMode() {
        return myNeighborMode;
    }

    /**
     * This method allows us to set an array for the indices of neighbors in order to loop through and add them
     *
     * @param rows int[] representing row indices of neighbors
     */
    public void setNeighborRows(int[] rows) {
        myNeighborRows = rows;
    }

    /**
     * This method return the total row indices being applied for to create neighbors
     *
     * @return int[] representing row indices of neighbors
     */
    public int[] getNeighborRows() {
        return myNeighborRows;
    }

    /**
     * This method allows us to set an array for the indices of neighbors in order to loop through and add them
     *
     * @param cols int[] representing column indices of neighbors
     */
    public void setNeighborCols(int[] cols) {
        myNeighborCols = cols;
    }

    /**
     * This method return the total row indices being applied to create neighbors
     *
     * @return int[] representing column indices of neighbors
     */
    public int[] getNeighborCols() {
        return myNeighborCols;
    }

    /**
     * This method returns all the existing points that are currently in the game board so they can be iterated through
     * allowing encapsulation and showing what existing values are
     *
     * @return set of points representing keys for the Board HashMap
     */
    public Set<Point> getPoints() {
        return myBoard.keySet();
    }

    /**
     * This method returns the Cell for a specific x and y value by searching for it's key and retrieving the value
     *
     * @param x x position of cell to be returned
     * @param y y position of cell to be returned
     * @return returns the cell with the passed in x and y exactly
     */
    public Cell getBoardCell(int x, int y) {
        Point currPoint = getPoint(x,y);
        for(Point point: getPoints()){
            if(point.x == currPoint.x && point.y == currPoint.y) return myBoard.get(point);
        }
        return null;
    }

    /**
     * Return the cell for a specific point from board hashmap
     *
     * @param point point object representing a specific x and y value as key for hashmap
     * @return cell which is a value of the specific point key
     */
    public Cell getBoardCell(Point point) {
        return myBoard.get(point);
    }

    /**
     * Return the current cell status for a specfic x and y value pair
     *
     * @param x x position of cell being requested
     * @param y y position of cell being requested
     * @return int representing the cell status for specific x and y
     */
    public int getCellStatus(int x, int y) {
        return myBoard.get(getPoint(x, y)).getCurrentStatus();
    }

    /**
     * Returns a new point object for specific x and y value in order to be used for key value pair retrieval
     *
     * @param x x position of cell
     * @param y y position of cell
     * @return The point object representing the inputted values
     */
    protected Point getPoint(int x, int y) {
        return new Point(x, y);
    }

    /**
     * Return the cell for a specific x and y value pai
     *
     * @param x x position of cell
     * @param y y position of cell
     * @return returns cell with exact location as inputted value pair
     */
    public Cell getXYBoardCell(int x, int y) {
        Point point = new Point(x, y);
        return myBoard.get(point);
    }

    /**
     * Return the hashmap Data structure for grid to be used in subclasses
     *
     * @return hashmap key = point object, value = cell object
     */
    protected Map<Point, Cell> getBoard() {
        return myBoard;
    }

    /**
     * The method sets a new board for this grid by taking in a new hashmap with Key value pairs corrected.
     * useful for grid expansion.
     *
     * @param passedInBoard hashmap key = point object, value = cell object
     */
    protected void setBoard(Map<Point, Cell> passedInBoard) {
        this.myBoard = passedInBoard;
    }

    /**
     * This method initializes the neighbors by applying neighbor mode in its sub classes
     * and edge policy in the current class to determine how grid should be constructed.
     * Then it iterates through possible neighbor values that need to be added for each cell and adds them
     */
    protected void initializeNeighbors() {
        for (Point point : myBoard.keySet()) {
            myBoard.get(point).clearNeighborCells();
            Cell cell = myBoard.get(point);
            applyNeighborMode(point);
            iterativelyAddNeighbors(point, cell);
        }
    }

    // Will also preserve the order by using LinkedHashSet
    // The method removes any duplicated neighbors in a cell in order to correctly apply rules to the observed cell
    private void removeDuplicatesInNeighborCells(Cell cell) {
        cell.setNeighborCells(new ArrayList<>(
                new LinkedHashSet<>(cell.getNeighborCells())));
    }

    // The method is a helper method for initializeNeighbors by carrying out the for loop addition of the neighbors based on their
    // indices
    private void iterativelyAddNeighbors(Point point, Cell cell) {
        for (int i = 0; i < myNeighborRows.length; i++) {
            int x = point.x + myNeighborCols[i];
            int y = point.y + myNeighborRows[i];

            Point neighborPosition = applyEdgePolicy(x, y);
            if (myBoard.containsKey(neighborPosition) && isInsideBoard(neighborPosition.x, neighborPosition.y)) {
                Cell c = myBoard.get(neighborPosition);
                cell.getNeighborCells().add(c);
            }
//            else {
//                cell.getNeighborCells().add(null);
//            }
        }
        removeDuplicatesInNeighborCells(cell);
    }

    /**
     * Abstract method to use in subclasses which create the neigborRow and neighborCol int[] with indices
     *
     * @param point Point values passed in
     */
    protected abstract void applyNeighborMode(Point point);

    /**
     * This method applies the edgepolicy that is passed in order to create the neighbors and be applied in initialize neighbors
     *
     * @param x x position of point that it is being applied to
     * @param y y position of point that it is being applied to
     * @return return the new point that needs to be added for edge cells to determine if a cell will wrap around
     */
    protected Point applyEdgePolicy(int x, int y) {
        if (myEdgePolicy != EDGE_POLICY_FINITE) {
            x = Math.floorMod(x, myMaxCol);
        }
        if (myEdgePolicy == EDGE_POLICY_TORUS) {
            y = Math.floorMod(y, myMaxRow);
        }
        return new Point(x, y);
    }

    /**
     * This method is used to determine if a particular x and y value is in bounds of the board. essentially determining the edge of grid
     *
     * @param x x position of point to be tested
     * @param y y position of point to be tested
     * @return true if the point is on the board, and false if not
     */
    protected boolean isInsideBoard(int x, int y) {
        return (x >= 0 && x < myNumCols && y >= 0 && y < myNumRows);
    }

    /**
     * Abstract method to expand the grid based on the type of cell shape used in its subclasses. The grid can expand in different
     * directions upward, downward, left, and right
     *
     * @param left leftward expansion by certain int amount above 0 or 0
     * @param top upward expansion by certain int amount above 0 or 0
     * @param right rightward expansion by certain int amount above 0 or 0
     * @param bottom downward expansion by certain int amount above 0 or 0
     */
    //expand the board with empty cells to each side depending on what's written
    public abstract void expandGrid(int left, int top, int right, int bottom);

//    private Grid clearNeighborsForCells(Grid passedInGrid){
//        for(Point point: passedInGrid.myBoard.keySet()){
//            passedInGrid.myBoard.get(point).clearNeighborCells();
//        }
//        return passedInGrid;
//    }

    /**
     * This method is a helper method for grid expansion for each subclass, by applying the appropriate type of grid expansion.
     * This is determined by multiplication factor as grid expansion by x and y value are variable
     *
     * @param multiplicationFactor int to determine by how much the grid should be expanded in x direction as a multiple of it
     * @param left int to determine by how much the grid should expand in the -x direction leftward
     * @param top int to determine by how much the grid should expand in the -y direction upward
     * @param right int to determine by how much the grid should expand in the +x direction rightward
     * @param bottom int to determine by how much the grid should expand in the +y direction downward
     * @param myNumRows int number of rows the grid had previously
     * @param myNumCols int number of columns the grid had privously
     * @param points set of points that exist for new grid
     * @param newGrid new expanded grid to which the data is being transferred
     */
    protected void initializeNewGridasOriginal(int multiplicationFactor, int left, int top, int right, int bottom, int myNumRows, int myNumCols, Set<Point> points, Grid newGrid) {
        for (Point point : points) {
            Cell movedCell = getBoardCell(point);
            movedCell.setXyPosition(point.x + (left * multiplicationFactor), point.y + top);
            Point movedPoint = point;
            movedPoint.setLocation(point.x + (left * multiplicationFactor), point.y + top);

            newGrid.getBoard().put(movedPoint, movedCell);
        }
        setBoard(newGrid.getBoard());
        initializeNeighbors();

        setMyNumRows(myNumRows + top + bottom);
        setMyNumCols(myNumCols + right + left);
    }

    /**
     * Read in the magic values for the specific variables written and create the reader to be used in subclasses to read in more
     * magic values
     */
    protected void populateNeighborData() {
        myReader = new PropertiesReader(neighborDataFile);
        EDGE_POLICY_FINITE = myReader.getIntProperty("EDGE_POLICY_FINITE");
        EDGE_POLICY_TORUS = myReader.getIntProperty("EDGE_POLICY_TORUS");
        EDGE_POLICY_CYLINDER = myReader.getIntProperty("EDGE_POLICY_CYLINDER");
    }

    /**
     * Helper method for sublcasses to set the neighborRows and neighborCols arrays to read in magic value lists
     *
     * @param row String from properties for row array
     * @param col String from properties for columns array
     */
    protected void setRowColValues(String row, String col) {
        setNeighborRows(myReader.getIntListProperty(row));
        setNeighborCols(myReader.getIntListProperty(col));
    }


}
