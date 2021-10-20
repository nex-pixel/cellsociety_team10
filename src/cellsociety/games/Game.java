package cellsociety.games;


import cellsociety.components.*;

import java.awt.*;
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

    private void createReader (String filename) {
        String fileType = filename.split(".")[-1];
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
}
