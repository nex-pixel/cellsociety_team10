package cellsociety.games;


import cellsociety.components.*;
import cellsociety.components.filereader.ReadCSVFile;
import cellsociety.components.filereader.ReadFile;
import cellsociety.components.filereader.ReadJSONFile;
import com.opencsv.CSVWriter;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public abstract class Game {

    private ReadFile myReader;
    private Grid myGrid;
    private static final String DEFAULT_GAME_DATA = "cellsociety.resources.GameData";
    protected ResourceBundle myGameData;

    public Game () {}

    public Game (Game copy) {
        populateGameConditions();
        this.myReader = copy.myReader;
        this.myGrid = copy.myGrid;
    }

    public Game (String filename) {
        populateGameConditions();
        createReader(filename);
        int[][] states = myReader.read();
        myGrid = new SquareGrid(states, 0, 0);
    }

    public Game (int[][] states) {
        populateGameConditions();
        setGrid(states);
    }

//    protected Map<Point, Cell> getGrid () { return myGrid.getBoard(); }
    protected Grid getGrid () { return myGrid; }
    protected void setGrid (int[][] states) { myGrid = new SquareGrid(states, 0, 0); }

    public int getCellStatus (int x, int y) { return myGrid.getBoardCell(new Point(x, y)).getCurrentStatus(); }
    public int getNumRows () { return myGrid.getNumRows(); }
    public int getNumCols () { return myGrid.getNumCols(); }

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
        for (Point point: myGrid.getPoints()) {
            applyRule(myGrid.getBoardCell(point));
        }
        for (Point point: myGrid.getPoints()) {
            myGrid.getBoardCell(point).changeStatus();
        }
    }

    protected int[][] toGridArray () {
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

    protected void populateGameConditions() {
        myGameData = ResourceBundle.getBundle(DEFAULT_GAME_DATA);
    }

    protected int getIntProperty(String label) {
        return Integer.parseInt(myGameData.getString(label));
    }

    protected List<Cell> checkNumCellsThisCase(int state, List<Cell> cellList){
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
