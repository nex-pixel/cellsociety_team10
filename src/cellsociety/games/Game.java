package cellsociety.games;


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


public abstract class Game {

    private ReadFile myReader;
    private Grid myGrid;
    private static final String DEFAULT_GAME_DATA = "cellsociety.resources.GameData";
    protected PropertiesReader myGameDataReader;
    private String LANG_KEY = "language";
    private String INVALID_SAVE = "InvalidSaveFile";
    private final int DEFAULT_GRID_CHOICE = 0;
    private int NUM_STATES;

    public Game () {}

    // Constructor for testing purposes
    public Game (Game copy) {
        populateGameConditions();
        this.myReader = copy.myReader;
        this.myGrid = copy.myGrid;
    }

    // default game with SquareGrid, complete neighbo52wdc r mode, finite edge policy
    public Game (String filename) {
        populateGameConditions();
        createReader(filename);
        int[][] states = myReader.read();
        setGrid(states, DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE);
    }

    public Game (int[][] states) {
        // default game with SquareGrid, complete neighbor mode, finite edge policy
        populateGameConditions();
        setGrid(states, DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE , DEFAULT_GRID_CHOICE);
    }

    public Game (int numCols, int numRows){
        populateGameConditions();
    }

    public Game (String filename, int gridType, int neighborMode, int edgePolicy) {
        populateGameConditions();
        createReader(filename);
        int[][] states = myReader.read();
        setGrid(states, gridType, neighborMode, edgePolicy);
    }

    public Game (int[][] states, int gridType, int neighborMode, int edgePolicy) {
        populateGameConditions();
        setGrid(states, gridType, neighborMode, edgePolicy);
    }

    public Game (int numCols, int numRows, int gridType, int neighborMode, int edgePolicy) {
        populateGameConditions();
    }

    protected Grid getGrid () { return myGrid; }

    protected void setGrid (int[][] states, int gridType, int neighborMode, int edgePolicy) {
        switch (gridType) {
            case 0 -> myGrid = new SquareGrid(states, neighborMode, edgePolicy);
            case 1 -> myGrid = new TriangleGrid(states, neighborMode, edgePolicy);
            case 2 -> myGrid = new HexagonGrid(states, neighborMode, edgePolicy);
        }
    }

    protected void setNumStatesOnBoard(int numStates){
        NUM_STATES = numStates;
    }

    protected int[][] createRandomIntTwoDArray(int numCols, int numRows){
        int[][] retArray = new int[numRows][numCols];
        for(int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                retArray[i][j] = getRandomInt(NUM_STATES);
            }
        }
        return retArray;
    }

    private int getRandomInt(int max){
        return (int)(Math.random() * (max + 1));
    }

    public int getCellStatus (int x, int y) { return myGrid.getBoardCell(new Point(x, y)).getCurrentStatus(); }

    public int getCellStatus (Point point) { return myGrid.getBoardCell(point).getCurrentStatus(); }

    public int getNumRows () { return myGrid.getNumRows(); }

    public int getNumCols () { return myGrid.getNumCols(); }

    public Set<Point> getAllPoints () { return myGrid.getPoints(); }

    public void changeNeighborMode (int newNeighborMode){
        myGrid.changeNeighborMode(newNeighborMode);
    }

    public void changeEdgePolicy (int newEdgePolicy){
        myGrid.changeEdgePolicy(newEdgePolicy);
    }

    private void createReader (String filename) {
        String[] temp = filename.split("\\.");
        String fileType = temp[temp.length - 1];

        if (fileType.equals("csv")) {
            myReader = new ReadCSVFile(filename);
        }
        else if (fileType.equals("json")) {
            myReader = new ReadJSONFile(filename);
        }
        // There may be some more types
    }

    //boolean
    protected abstract boolean applyRule (Cell cell);

    public void update () {
        for (Point point: myGrid.getPoints()) {
            applyRule(myGrid.getBoardCell(point));
        }
        for (Point point: myGrid.getPoints()) {
            myGrid.getBoardCell(point).changeStatus();
        }
    }

    public abstract void changeCellOnClick (Point point);

    protected int[][] toGridArray () {
        int[][] ret = new int[myGrid.getNumRows()][myGrid.getNumCols()];
        for (int r = 0; r < myGrid.getNumRows(); r++) {
            for (int c = 0; c < myGrid.getNumCols(); c++) {
                ret[r][c] = getCellStatus(c, r);
            }
        }
        return ret;
    }

    public void saveCSVFile (String filename, ResourceBundle languageResource) {
        try {
            File file = new File(filename);
            CSVWriter writer = new CSVWriter(new FileWriter(file));
            writer.writeNext(new String[] {Integer.toString(myGrid.getNumRows()), Integer.toString(myGrid.getNumCols())}, false);
            int[][] array = toGridArray();
            for (int r = 0; r < myGrid.getNumRows(); r++) {
                writer.writeNext(Arrays.stream(array[r])
                        .mapToObj(String::valueOf)
                        .toArray(String[]::new), false);
            }
            writer.close();
        }
        catch (Exception e) {
            new GenerateError(languageResource.getString(LANG_KEY), INVALID_SAVE);
        }

    }


    protected void populateGameConditions () {
        myGameDataReader = new PropertiesReader(DEFAULT_GAME_DATA);
    }

    protected int retrieveIntProperty (String label) {
        return myGameDataReader.getIntProperty(label);
    }

    protected List<Cell> checkNumCellsThisCase (int state, List<Cell> cellList){
        List<Cell> retList = new ArrayList<>();
        for(Cell cellInList: cellList){
            if(cellInList == null){
              //skip
            } else if(cellInList.getCurrentStatus() == state){
                retList.add(cellInList);
            }
        }
        return retList;
    }

    @Override
    public boolean equals (Object o) {
        return Arrays.deepEquals(((Game) o).toGridArray(), this.toGridArray());
    }
}
