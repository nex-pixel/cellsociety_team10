package cellsociety.games;


import cellsociety.components.*;
import com.opencsv.CSVWriter;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;


public abstract class Game {

    private ReadFile myReader;
    protected Grid myGrid;
    private static final String DEFAULT_GAME_DATA = "cellsociety.resources.";
    protected ResourceBundle myGameData;

    public Game () {}

    public Game (String filename) {
        populateGameConditions();
        createReader(filename);
        int[][] states = myReader.read();
        myGrid = new Grid(states);
    }

    public Map<Point, Cell> getGrid (){
        return myGrid.getBoard();
    }
    public void setGrid (int[][] states) { myGrid = new Grid(states); }

    public int getCellStatus (int x, int y) { return getGrid().get(new Point(x, y)).getCurrentStatus(); }

    public int getNumRows () { return myGrid.getNumRows(); }
    public int getNumCols () { return myGrid.getNumCols(); }

//    public Grid getMyGrid(){
//        return myGrid;
//    }

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
    protected abstract boolean applyRule(Cell cell);

    public void update() {
        Map<Point, Cell> board = myGrid.getBoard();
        for (Point point: board.keySet()) {
            applyRule(board.get(point));
        }
        for (Point point: board.keySet()) {
            board.get(point).changeStatus();
        }
    }

    public int[][] toGridArray () {
        int[][] ret = new int[myGrid.getNumRows()][myGrid.getNumCols()];
        for (int r = 0; r < myGrid.getNumRows(); r++) {
            for (int c = 0; c < myGrid.getNumCols(); c++) {
                ret[r][c] = getCellStatus(c, r);
            }
        }
        return ret;
    }

    public void saveCSVFile (String filename) {
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
            e.printStackTrace();
        }

    }

    private void populateGameConditions() {
        myGameData = ResourceBundle.getBundle(DEFAULT_GAME_DATA);
    }

    protected int getIntProperty(String label) {
        return Integer.parseInt(myGameData.getString(label));
    }
}
