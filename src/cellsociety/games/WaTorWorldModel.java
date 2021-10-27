package cellsociety.games;

import cellsociety.components.Cell;
import cellsociety.components.Grid;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaTorWorldModel extends Game{
    //neighbors only north south east and west count
    private final int EMPTY = 0;
    private final int FISH = 1;
    private final int SHARK = 2;
    private final int SHARK_STARTING_ENERGY = 10;
    private final int FISH_STARTING_VAL = 0;
    private final int FISH_REPRODUCE_VAL = 5;
    private Random rand;

    public WaTorWorldModel(String filename){
        super(filename);
        setSharkEnergyValues();
    }

    private void setSharkEnergyValues(){
        for(Point point: myGrid.getBoard().keySet()){
            Cell cell = myGrid.getBoard().get(point);
            if(cell.getCurrentStatus() == SHARK){
                cell.setMiscellaneousVal(SHARK_STARTING_ENERGY);
            } else if (cell.getCurrentStatus() == FISH){
                cell.setMiscellaneousVal(FISH_STARTING_VAL);
            }
        }
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    protected boolean applyRule(Cell cell) {
        List<Cell> adjNeighbors = cell.getAdjacentNeighbors();
        if(cell.getCurrentStatus() == FISH){
            fishMovement(cell, adjNeighbors);
        }
        return true;
    }

    private void fishMovement(Cell cell, List<Cell> adjacentNeighbors){
        List<Cell> adjNeigh = checkNumCellsThisCase(EMPTY, adjacentNeighbors);
        if(adjNeigh.size() > 0){
            int whichAdjacentCell = rand.nextInt(adjNeigh.size());
            Cell chosenNextCell = adjNeigh.get(whichAdjacentCell);
            chosenNextCell.setNextStatus(cell.getCurrentStatus());
            if(cell.getMiscellaneousVal() <= FISH_REPRODUCE_VAL) {
                cell.setNextStatus(EMPTY);
                cell.setMiscellaneousVal(cell.getMiscellaneousVal() + 1);
            } else if(cell.getMiscellaneousVal() > FISH_REPRODUCE_VAL){
                cell.setNextStatus(cell.getCurrentStatus());
                cell.setMiscellaneousVal(FISH_STARTING_VAL);
            }
        }
    }

    private List<Cell> checkNumCellsThisCase(int state, List<Cell> cellList){
        List<Cell> retList = new ArrayList<>();
        for(Cell cellInList: cellList){
            if(cellInList.getCurrentStatus() == state){
                retList.add(cellInList);
            }
        }
        return retList;
    }
}
