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
    private double probGrow;
    private boolean firePresentInGrid = true;

    public SpreadingFireModel(String filename) {
        super(filename);
        probCatch = 0.2;
        probGrow = 0.0;
        //myGrid.expandGrid(2, 2, 2, 2);
    }

    public void setProbOfFire(double probability){
        probCatch = probability;
    }

    public void setProbGrowNewTrees(double probability){
        probGrow = probability;
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
    protected boolean applyRule(Cell cell){
        List<Cell> adjacentNeighbors = cell.getAdjacentNeighbors();
        if(cell.getCurrentStatus() == EMPTY){
            int willATreeGrow = willNewTreeGrow();
            cell.setNextStatus(willATreeGrow);
        } else if(cell.getCurrentStatus() == TREE){
            int willItCatchFire = spread(adjacentNeighbors);
            cell.setNextStatus(willItCatchFire);
        } else if (cell.getCurrentStatus() == BURNING){
            cell.setNextStatus(EMPTY);
        }
        return true;
    }

    private int spread(List<Cell> neighbors){
        double randNumber = Math.random();
        if(neighbors.get(0).getCurrentStatus() == BURNING || neighbors.get(1).getCurrentStatus() == BURNING ||
                neighbors.get(2).getCurrentStatus() == BURNING || neighbors.get(3).getCurrentStatus() == BURNING) {
            if (randNumber < probCatch) {
                return BURNING;
            }
        }
        return TREE;
    }

    private int willNewTreeGrow(){
        double randomGrow = Math.random();
        if(randomGrow < probGrow){
            return TREE;
        }
        return EMPTY;
    }
}
