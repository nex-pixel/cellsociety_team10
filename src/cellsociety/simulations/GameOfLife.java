package cellsociety.simulations;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class GameOfLife extends Game {
    private int ALIVE = 1;
    private int DEAD = 0;

    public GameOfLife (String filename) {
        super(filename);
    }

    @Override
    public void update() {
        Map<Point, Cell> board = myGrid.getBoard();
        for (Point point: board.keySet()) {
            applyRule(board.get(point));
        }
        for (Point point: board.keySet()) {
            board.get(point).changeStatus();
        }
    }

    @Override
    public void applyRule(Cell cell){
        List<Cell> neighbors = cell.getNeighborCells();
        int numCellsAlive = 0;
        for(Cell cellNextDoor: neighbors){
            if(cellNextDoor.getCurrentStatus() == ALIVE){
                numCellsAlive++;
            }
        }
        if(numCellsAlive <= 1){
            cell.setNextStatus(DEAD);
        } else if(numCellsAlive == 2){
            cell.setNextStatus(cell.getCurrentStatus());
        } else if(numCellsAlive == 3){
            cell.setNextStatus(ALIVE);
        } else if(numCellsAlive >= 4){
            cell.setNextStatus(DEAD);
        }
    }
}
