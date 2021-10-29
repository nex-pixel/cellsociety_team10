package cellsociety.games;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SpreadingFireModel extends Game{
    private int EMPTY;
    private int TREE;
    private int BURNING;
    private double probCatch;
    private double probGrow;
    private boolean firePresentInGrid = true;
    private int expandGridByInt;

    public SpreadingFireModel(String filename) {
        super(filename);
        probCatch = Double.parseDouble(myGameData.getString("SpreadingFireProbSpread"));;
        probGrow = Double.parseDouble(myGameData.getString("SpreadingFireProbGrow"));
        expandGridByInt = getIntProperty("SpreadingFireExpandGridBy");
        EMPTY = getIntProperty("SpreadingFireEmpty");
        TREE = getIntProperty("SpreadingFireTree");
        BURNING = getIntProperty("SpreadingFireBurning");
        //getGrid().expandGrid(expandGridByInt, expandGridByInt, expandGridByInt, expandGridByInt);
    }

    public void setProbOfFire(double probability){
        probCatch = probability;
    }

    public void setProbGrowNewTrees(double probability){
        probGrow = probability;
    }

    @Override
    public void update() {
        Set<Point> points = getGrid().getPoints();
        int fireCount = 0;
//        Map<Point, Cell> board = myGrid.getBoard();
        for (Point point: points) {
            Cell cellBeingChecked = getGrid().getBoardCell(point);
            if(cellBeingChecked.getCurrentStatus() == BURNING) {
                fireCount++;
            }
            applyRule(getGrid().getBoardCell(point));
        }
        if(fireCount == 0){
            firePresentInGrid = false;
        }
        if(firePresentInGrid) {
            for (Point point : points) {
                getGrid().getBoardCell(point).changeStatus();
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
