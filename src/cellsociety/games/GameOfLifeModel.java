package cellsociety.games;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.List;

public class GameOfLifeModel extends Game {
    private int ALIVE;
    private int DEAD;

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
    public void changeCellOnClick(Point point) {
        Cell cell = getGrid().getBoardCell(point);
        cell.setCurrentStatus((cell.getCurrentStatus() + 1) % 2);
    }

    @Override
    protected void populateGameConditions () {
        super.populateGameConditions();
        DEAD = retrieveIntProperty("GameOfLifeDead");
        ALIVE = retrieveIntProperty("GameOfLifeAlive");
    }
}
