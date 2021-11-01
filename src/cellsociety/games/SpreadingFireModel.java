package cellsociety.games;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.List;

public class SpreadingFireModel extends Game{
    private int EMPTY;
    private int TREE;
    private int BURNING;
    private double probCatch;
    private double probGrow;
    private boolean firePresentInGrid = true;
    private int expandGridByInt;
    private int NUM_STATES = 3;
    private final int DEFAULT_GRID_CHOICE = 0;

    public SpreadingFireModel(String filename) {
        super(filename);
        getGrid().expandGrid(expandGridByInt, expandGridByInt, expandGridByInt, expandGridByInt);
    }

    public SpreadingFireModel(String filename, boolean TEST) {
        super(filename);
        if(TEST == false) {
            getGrid().expandGrid(expandGridByInt, expandGridByInt, expandGridByInt, expandGridByInt);
        }
    }

    public SpreadingFireModel (int numCols, int numRows){
        super(numCols, numRows);
        setNumStatesOnBoard(NUM_STATES);
        setGrid(createRandomIntTwoDArray(numCols, numRows), DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE, DEFAULT_GRID_CHOICE);
    }

    public SpreadingFireModel (int numCols, int numRows, int gridType, int neighborMode, int edgePolicy) {
        super(numCols, numRows, gridType, neighborMode, edgePolicy);
        setNumStatesOnBoard(NUM_STATES);
        setGrid(createRandomIntTwoDArray(numCols, numRows), gridType, neighborMode, edgePolicy);
    }

    public SpreadingFireModel (String filename, int gridType, int neighborMode, int edgePolicy) {
        super(filename, gridType, neighborMode, edgePolicy);
    }

    public void setProbOfFire(double probability){
        probCatch = probability;
    }

    public void setProbGrowNewTrees(double probability){
        probGrow = probability;
    }

    @Override
    public void update() {
        checkForFire();
        if(firePresentInGrid) {
            super.update();
        }
    }

    private void checkForFire(){
        firePresentInGrid = false;
        for(Point point: getGrid().getPoints()){
            if(getGrid().getBoardCell(point).getCurrentStatus() == BURNING) {
                firePresentInGrid = true;
            }
        }
    }

    @Override
    protected boolean applyRule(Cell cell){
        List<Cell> neighbors = cell.getNeighborCells();
        if(cell.getCurrentStatus() == EMPTY){
            int willATreeGrow = willNewTreeGrow(neighbors);
            cell.setNextStatus(willATreeGrow);
        } else if(cell.getCurrentStatus() == TREE){
            int willItCatchFire = spread(neighbors);
            cell.setNextStatus(willItCatchFire);
        } else if (cell.getCurrentStatus() == BURNING){
            cell.setNextStatus(EMPTY);
        }
        return true;
    }

    private int spread(List<Cell> neighbors){
        double randNumber = Math.random();
        if(checkNumCellsThisCase(BURNING, neighbors).size() > 0) {
            if (randNumber < probCatch) {
                return BURNING;
            }
        }
        return TREE;
    }

    private int willNewTreeGrow(List<Cell> neighbors){
        double randomGrow = Math.random();
        if(checkNumCellsThisCase(TREE, neighbors).size() > 0) {
            if (randomGrow < probGrow) {
                return TREE;
            }
        }
        return EMPTY;
    }

    @Override
    public void changeCellOnClick(Point point) {
        Cell cell = getGrid().getBoardCell(point);
        cell.setCurrentStatus((cell.getCurrentStatus() + 1) % 3);
        update();
    }

    @Override
    protected void populateGameConditions () {
        super.populateGameConditions();
        probCatch = myGameDataReader.getDoubleProperty("SpreadingFireProbSpread");;
        probGrow = myGameDataReader.getDoubleProperty("SpreadingFireProbGrow");
        expandGridByInt = retrieveIntProperty("SpreadingFireExpandGridBy");
        EMPTY = retrieveIntProperty("SpreadingFireEmpty");
        TREE = retrieveIntProperty("SpreadingFireTree");
        BURNING = retrieveIntProperty("SpreadingFireBurning");
    }
}
