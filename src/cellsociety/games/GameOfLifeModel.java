package cellsociety.games;

import cellsociety.components.Cell;

import java.util.List;

public class GameOfLifeModel extends Game {
    private int ALIVE;
    private int DEAD;

    public GameOfLifeModel (String filename) {
        super(filename);
        setStateProperties();
    }

    public GameOfLifeModel (GameOfLifeModel copy) {
        super(copy);
        setStateProperties();
    }

    public GameOfLifeModel (int[][] states) {
        super(states);
        setStateProperties();
    }

    private void setStateProperties(){
        DEAD = getIntProperty("GameOfLifeDead");
        ALIVE = getIntProperty("GameOfLifeAlive");
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
}
