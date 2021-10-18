package cellsociety.games;


import cellsociety.components.Grid;
import cellsociety.components.ReadCSVFile;
import cellsociety.components.ReadFile;
import cellsociety.components.ReadJSONFile;



public abstract class Game {

    private ReadFile myReader;
    private Grid myGrid;

    public Game (String filename) {
        createReader(filename);
        int[][] states = myReader.read();
        myGrid = new Grid(states);
    }

    public Grid getGrid() { return myGrid; }

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

    public abstract void update ();

}
