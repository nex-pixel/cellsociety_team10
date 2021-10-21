package cellsociety.games;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class GameOfLifeModel extends Game {
    private int ALIVE = 1;
    private int DEAD = 0;

    public GameOfLifeModel(String filename) {
        super(filename);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    protected void applyRule(Cell cell){
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
    }
}
