package cellsociety.games;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class SpreadingFireModel extends Game{
    private int EMPTY = 0;
    private int TREE = 1;
    private int BURNING = 2;
    private double probCatch;
    private boolean firePresentInGrid = true;

    public SpreadingFireModel(String filename) {
        super(filename);
        probCatch = 0.2;
        myGrid.expandGrid(2, 2, 2, 2);
    }

    public void setProbOfFire(double probability){
        probCatch = probability;
    }

    @Override
    public void update() {
        Map<Point, Cell> board = myGrid.getBoard();
        int fireCount = 0;
        for (Point point: board.keySet()) {
            Cell cellBeingChecked = board.get(point);
            if(cellBeingChecked.getCurrentStatus() == BURNING) {
                fireCount++;
            }
            applyRule(board.get(point));
        }
        if(fireCount == 0){
            firePresentInGrid = false;
        }
        if(firePresentInGrid) {
            for (Point point : board.keySet()) {
                board.get(point).changeStatus();
            }
        }
    }

    @Override
    public void applyRule(Cell cell){
        List<Cell> neighbors = cell.getNeighborCells();
        boolean willFireSpread = spread(neighbors.get(1).getCurrentStatus(), neighbors.get(3).getCurrentStatus(),
                neighbors.get(4).getCurrentStatus(), neighbors.get(6).getCurrentStatus());
        if(cell.getCurrentStatus() == TREE){
            if(willFireSpread){
                cell.setNextStatus(BURNING);
            } else {
                cell.setNextStatus(cell.getCurrentStatus());
            }
        }
    }

    private boolean spread(int northCell, int westCell, int eastCell, int southCell){
        double randNumber = Math.random();
        if(randNumber < probCatch){
            return true;
        }
        return false;
    }
}
