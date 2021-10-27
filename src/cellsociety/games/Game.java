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

    public Game (Game copy) {
        this.myReader = copy.myReader;
        this.myGrid = copy.myGrid;
    }

    public Game (String filename) {
        createReader(filename);
        int[][] states = myReader.read();
        myGrid = new Grid(states);
    }

    public Game (int[][] states) {
        setGrid(states);
    }

    protected Map<Point, Cell> getGrid () { return myGrid.getBoard(); }
    protected void setGrid (int[][] states) { myGrid = new Grid(states); }

    public int getCellStatus (int x, int y) { return getGrid().get(new Point(x, y)).getCurrentStatus(); }
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
        Map<Point, Cell> board = myGrid.getBoard();
        for (Point point: board.keySet()) {
            applyRule(board.get(point));
        }
        for (Point point: board.keySet()) {
            board.get(point).changeStatus();
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

    @Override
    public boolean equals (Object o) {
        return Arrays.deepEquals(((Game) o).toGridArray(), this.toGridArray());
    }

}
