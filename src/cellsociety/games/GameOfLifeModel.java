package cellsociety.games;

import cellsociety.components.Cell;
import cellsociety.components.HexagonGrid;
import cellsociety.components.SquareGrid;
import cellsociety.components.TriangleGrid;

import java.awt.*;
import java.util.List;

public class GameOfLifeModel extends Game {
    private int ALIVE;
    private int DEAD;
    private int NUM_STATES = 2;
    private final int DEFAULT_GRID_CHOICE = 0;

    public GameOfLifeModel (String filename) {
        super(filename);
    }

    public GameOfLifeModel (String filename, int gridType, int neighborMode, int edgePolicy) {
        super(filename, gridType, neighborMode, edgePolicy);
    }

    public GameOfLifeModel (GameOfLifeModel copy) {
        super(copy);
    }

    public GameOfLifeModel (int[][] states) {
        super(states);
    }

    public GameOfLifeModel (int numCols, int numRows){
        super(numCols, numRows);
    }

    public GameOfLifeModel (int numCols, int numRows, int gridType, int neighborMode, int edgePolicy) {
        super(numCols, numRows, gridType, neighborMode, edgePolicy);
    }

    protected void setNumStatesOnBoard () {
        setNumStates(NUM_STATES);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    protected boolean applyRule(Cell cell){
        List<Cell> neighbors = cell.getNeighborCells();
        int numCellsAlive = 0;
        for (Cell cellNextDoor: neighbors) {
            if (cellNextDoor != null && cellNextDoor.getCurrentStatus() == ALIVE) {
                numCellsAlive++;
            }
        }
        if (cell.getCurrentStatus() == ALIVE) {
            cell.setNextStatus(numCellsAlive == 2 || numCellsAlive == 3 ? 1 : 0);
        }
        else {
            cell.setNextStatus(numCellsAlive == 3 ? 1 : 0);
        }
        return true;
    }

    @Override
    public void changeCellOnClick(int x, int y) {
        Cell cell = getGrid().getBoardCell(x,y);
        cell.setCurrentStatus((cell.getCurrentStatus() + 1) % 2);
    }

    @Override
    protected void populateGameConditions () {
        super.populateGameConditions();
        DEAD = retrieveIntProperty("GameOfLifeDead");
        ALIVE = retrieveIntProperty("GameOfLifeAlive");
    }
}
