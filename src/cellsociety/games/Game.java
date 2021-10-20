package cellsociety.games;


import cellsociety.components.*;
import com.opencsv.CSVWriter;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Map;


public abstract class Game {

    private ReadFile myReader;
    protected Grid myGrid;

    public Game () {}

    public Game (String filename) {
        createReader(filename);
        int[][] states = myReader.read();
        myGrid = new Grid(states);
    }

    public Map<Point, Cell> getGrid (){
        return myGrid.getBoard();
    }
    public void setGrid (int[][] states) { myGrid = new Grid(states); }

    public Grid getMyGrid(){
        return myGrid;
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

    protected abstract void applyRule(Cell cell);

    public abstract void update();

    public int[][] toGridArray () {
        int[][] ret = new int[myGrid.getNumRows()][myGrid.getNumCols()];
        for (int r = 0; r < myGrid.getNumRows(); r++) {
            for (int c = 0; c < myGrid.getNumCols(); c++) {
                ret[r][c] = getGrid().get(new Point(c, r)).getCurrentStatus();
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

}
