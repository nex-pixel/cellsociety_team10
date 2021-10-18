package cellsociety.games;


import cellsociety.components.*;

import java.awt.*;
import java.util.Map;


public abstract class Game {

    private ReadFile myReader;
    protected Grid myGrid;

    public Game (String filename) {
        createReader(filename);
        int[][] states = myReader.read();
        myGrid = new Grid(states);
    }

    public Map<Point, Cell> getGrid(){
        return myGrid.getBoard();
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
}
