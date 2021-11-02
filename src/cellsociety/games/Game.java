package cellsociety.games;


import cellsociety.ReflectionHandler;
import cellsociety.components.*;
import cellsociety.components.filereader.ReadCSVFile;
import cellsociety.components.filereader.ReadFile;
import cellsociety.components.filereader.ReadJSONFile;
import cellsociety.error.GenerateError;
import com.opencsv.CSVWriter;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.List;

/***
 * The class abstract models the general feature of Game
 *
 * @author Norah Tan, Haseeb Chaudhry, Ryleigh Byrne
 */
public abstract class Game {
    private static final String EVENT_PATH = "cellsociety.resources.gridTypeEvents.gridTypeEvents";
    private static final String DEFAULT_GAME_DATA = "cellsociety.resources.gameData.GameData";
    private static final String GAME_CLASSPATH = "cellsociety.games.Game";
    private static final String INVALID_SAVE = "InvalidSaveFile";
    private static final String SAVE_FILE_PATH = "data/saved_files/";
    private static final String CSV_EXTENSION = ".csv";

    private ReadFile myReader;
    private Grid myGrid;
    protected PropertiesReader myGameDataReader;
    private static final int DEFAULT_GRID_CHOICE = 0;
    private int NUM_STATES;
    private int[][] myStates;
    private int myGridType;
    private int myNeighborMode;
    private int myEdgePolicy;
    private ResourceBundle myGridEventResources;

    /***
     * Default constructor
     */
    public Game() {
    }

    /***
     * Constructor for testing purpose.
     *
     * @param copy is the Game that we want to copy from
     */
    public Game(Game copy) {
        populateGameConditions();
        this.myReader = copy.myReader;
        this.myGrid = copy.myGrid;
    }

    /***
     * Constructor that takes in a CSV file for grid initialization,
     * with default square grid, complete neighbors and finite edge policy.
     *
     * @param filename gives the CSV figuration file for the grid
     */
    public Game(String filename) {
        populateGameConditions();
        createReader(filename);
        int[][] states = new int[1][1];
        try {
            states = myReader.read();
        } catch (Exception e){
            String error = String.format("The file was not read in properly, or was not found.");
            System.out.println(error);
        }
        setGrid(states, DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE);
    }

    /***
     * Constructor for testing purpose.
     *
     * @param states specifies the grid
     */
    public Game(int[][] states) {
        populateGameConditions();
        setGrid(states, DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE);
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
    public Game(String filename, int gridType, int neighborMode, int edgePolicy) {
        populateGameConditions();
        createReader(filename);
        int[][] states = new int[1][1];
        try {
            states = myReader.read();
        } catch (Exception e){
            String error = String.format("The file was not read in properly, or was not found.");
            System.out.println(error);
        }setGrid(states, gridType, neighborMode, edgePolicy);
    }

    /***
     * Constructor for testing purpose, with a specified grid shape, neighbor mode and edge policy.
     *
     * @param states specifies the grid
     * @param gridType gives one of square, triangle, and hexagon grid shapes.
     * @param neighborMode gives one of complete, cardinal (adjacent on the edge), and bottom half modes of neighbors
     * @param edgePolicy gives one of finite, toroidal, and cylindrical edge policies
     */
    public Game(int[][] states, int gridType, int neighborMode, int edgePolicy) {
        populateGameConditions();
        setGrid(states, gridType, neighborMode, edgePolicy);
    }

    /***
     * Constructor that randomly generates the grid by specifying the numbers of rows, columns, and threshold for satisfaction,
     * with default square grid, complete neighbors and finite edge policy.
     *
     * @param numCols is the number of columns of the grid
     * @param numRows is the number of rows of the grid
     */
    public Game(int numCols, int numRows) {
        populateGameConditions();
        setNumStatesOnBoard();
        setGrid(createRandomIntTwoDArray(numCols, numRows), DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE);
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
    public Game(int numCols, int numRows, int gridType, int neighborMode, int edgePolicy) {
        populateGameConditions();
        setNumStatesOnBoard();
        setGrid(createRandomIntTwoDArray(numCols, numRows), gridType, neighborMode, edgePolicy);
    }

    /***
     * Getter method that gives the grid
     *
     * @return myGrid
     */
    protected Grid getGrid() {
        return myGrid;
    }

    /***
     * Setter method that initialize the grid according to the following parameters
     *
     * @param states specifies the grid
     * @param gridType gives one of square, triangle, and hexagon grid shapes.
     * @param neighborMode gives one of complete, cardinal (adjacent on the edge), and bottom half modes of neighbors
     * @param edgePolicy gives one of finite, toroidal, and cylindrical edge policies
     */
    protected void setGrid(int[][] states, int gridType, int neighborMode, int edgePolicy) {
        myStates = states;
        myGridType = gridType;
        myNeighborMode = neighborMode;
        myEdgePolicy = edgePolicy;
        myGridEventResources = ResourceBundle.getBundle(EVENT_PATH);
        ReflectionHandler reflectionHandler = new ReflectionHandler();
        String typeOfGrid = gridType + ".type";
        try{
            reflectionHandler.handleMethod(myGridEventResources.getString(typeOfGrid), GAME_CLASSPATH).invoke(Game.this);
        }catch(Exception e){
            String error = String.format("The method: %s you are trying to call does not exist. Please fix and rerun.", myGridEventResources.getString(typeOfGrid));
            System.out.println(error);
        }
    }

    private void makeNewSquareGrid(){
        myGrid = new SquareGrid(myStates, myNeighborMode, myEdgePolicy);
    }

    private void makeNewTriangleGrid(){
        myGrid = new TriangleGrid(myStates, myNeighborMode, myEdgePolicy);
    }

    private void makeNewHexagonGrid(){
        myGrid = new HexagonGrid(myStates, myNeighborMode, myEdgePolicy);
    }

    /***
     * An abstract method that helps set the number of states on board that is unique to each child game
     */
    protected abstract void setNumStatesOnBoard();

    /***
     * Setter method that sets the number of states on the board as specified
     *
     * @param numStates
     */
    protected void setNumStates(int numStates) {
        NUM_STATES = numStates;
    }

    /***
     * Create a random 2d array with states being a non-negative integer up to NUM_STATES
     *
     * @param numCols specifies the number of columns in the 2d array
     * @param numRows specifies the number of columns in the 2d array
     * @return the random 2d array
     */
    protected int[][] createRandomIntTwoDArray(int numCols, int numRows) {
        int[][] retArray = new int[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                retArray[i][j] = getRandomInt(this.NUM_STATES - 1);
            }
        }
        return retArray;
    }

    private int getRandomInt(int max) {
        return (int) (Math.random() * (max + 1));
    }

    /***
     * Getter method that returns the current status of a Cell at a location
     *
     * @param x is the x-location
     * @param y is the y-location
     * @return the current status of the Cell located at (x, y)
     */
    public int getCellStatus(int x, int y) {
        return myGrid.getCellStatus(x, y);
    }

    /***
     * Getter method that returns the current status of a Cell at a location
     *
     * @param point is the Point representation of the xy-location
     * @return the current status of the Cell located at (point.x, point.y)
     */
    public int getCellStatus(Point point) {
        return myGrid.getBoardCell(point).getCurrentStatus();
    }

    /***
     * Getter method
     *
     * @return the number of rows in the grid
     */
    public int getNumRows() {
        return myGrid.getNumRows();
    }

    /***
     * Getter method
     *
     * @return the number of columns in the grid
     */
    public int getNumCols() {
        return myGrid.getNumCols();
    }

    /***
     * Getter method
     *
     * @return a list of the locations of all Cells in the grid
     */
    public Set<Point> getAllPoints() {
        return myGrid.getPoints();
    }

    /***
     * Setter method that changes the neighbor mode
     *
     * @param newNeighborMode
     */
    public void changeNeighborMode(int newNeighborMode) {
        myGrid.changeNeighborMode(newNeighborMode);
    }

    /***
     * Setter method that changes the edge policy
     *
     * @param newEdgePolicy
     */
    public void changeEdgePolicy(int newEdgePolicy) {
        myGrid.changeEdgePolicy(newEdgePolicy);
    }

    private void createReader(String filename) {
        String[] temp = filename.split("\\.");
        String fileType = temp[temp.length - 1];

        if (fileType.equals("csv")) {
            myReader = new ReadCSVFile(filename);
        } else if (fileType.equals("json")) {
            myReader = new ReadJSONFile(filename);
        }
        // There may be some more types
    }

    /***
     * An abstract method that applies the specific game rule to the Cell and change Cell's nextStatus
     * @param cell
     * @return whether the status of the cell has been changes
     */
    protected abstract boolean applyRule(Cell cell);

    /***
     * Complete one round of update on each Cell on the board, change their statuses
     */
    public void update() {
        for (Point point : myGrid.getPoints()) {
            applyRule(myGrid.getBoardCell(point));
        }
        for (Point point : myGrid.getPoints()) {
            myGrid.getBoardCell(point).changeStatus();
        }
    }

    /***
     * Change the current status of the Cell by adding 1 (mod NUM_STATES)
     *
     * @param x is the x-location of the Cell
     * @param y is the y-location of the Cell
     */
    public void changeCellOnClick (int x, int y) {
        setNumStatesOnBoard();
        Cell cell = getGrid().getBoardCell(x,y);
        cell.setCurrentStatus((cell.getCurrentStatus() + 1) % NUM_STATES);
    }

    /***
     * A helper method that transforms myGrid back into a 2d array
     *
     * @return the 2d array of myGrid
     */
    protected int[][] toGridArray() {
        int[][] ret = new int[myGrid.getNumRows()][myGrid.getNumCols()];
        for (int r = 0; r < myGrid.getNumRows(); r++) {
            for (int c = 0; c < myGrid.getNumCols(); c++) {
                ret[r][c] = getCellStatus(c, r);
            }
        }
        return ret;
    }

    /***
     * Save myGrid into a CSV file
     *
     * @param filename specifies the path that the saved CSV file will be located in
     * @param languageResource specifies the language
     */
    public void saveCSVFile(String filename, ResourceBundle languageResource) {
        try {
            File file = new File(SAVE_FILE_PATH + filename + CSV_EXTENSION);
            CSVWriter writer = new CSVWriter(new FileWriter(file));
            writer.writeNext(new String[]{Integer.toString(myGrid.getNumCols()), Integer.toString(myGrid.getNumRows())}, false);
            int[][] array = toGridArray();
            for (int r = 0; r < myGrid.getNumRows(); r++) {
                writer.writeNext(Arrays.stream(array[r])
                        .mapToObj(String::valueOf)
                        .toArray(String[]::new), false);
            }
            writer.close();
        } catch (Exception e) {
            new GenerateError(languageResource, INVALID_SAVE);
        }

    }

    /***
     * Import all the "magic numbers" needed in models
     */
    protected void populateGameConditions() {
        myGameDataReader = new PropertiesReader(DEFAULT_GAME_DATA);
    }

    /***
     * Import a constant variable of type Integer
     * @param label is the name of the Integer variable
     * @return the Integer variable
     */
    protected int retrieveIntProperty(String label) {
        return myGameDataReader.getIntProperty(label);
    }

    /***
     * Retain a list of Cells with a specified state from a general list
     *
     * @param state is the state of the Cells we want to retain
     * @param cellList is the list of Cells we want to filter from
     * @return the filtered list with Cells only with the desired state
     */
    protected List<Cell> checkNumCellsThisCase(int state, List<Cell> cellList) {
        List<Cell> retList = new ArrayList<>();
        for (Cell cellInList : cellList) {
            if (cellInList != null && cellInList.getCurrentStatus() == state) {
                retList.add(cellInList);
            }
        }
        return retList;
    }

    @Override
    public boolean equals(Object o) {
        return Arrays.deepEquals(((Game) o).toGridArray(), this.toGridArray());
    }
}
