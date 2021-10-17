package cellsociety.simulations;


import cellsociety.components.*;


public abstract class Game {

    private ReadFile myReader;
    protected Grid myGrid;

    public Game (String filename) {
        createReader(filename);
        int[][] states = myReader.read();
        myGrid = new Grid(states);
    }

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

    public abstract void applyRule(Cell cell);

    public abstract void update();
}
