package cellsociety.games;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WaTorWorldModel extends Game{
    //neighbors only north south east and west count
    private int EMPTY;
    private int FISH;
    private int SHARK;
    private int SHARK_STARTING_ENERGY;
    private int STARTING_VAL;
    private int REPRODUCE_VAL;
    private Random rand;

    public WaTorWorldModel(String filename){
        super(filename);
        EMPTY = getIntProperty("WatorWorldEmpty");
        FISH = getIntProperty("WatorWorldFish");
        SHARK = getIntProperty("WatorWorldShark");
        SHARK_STARTING_ENERGY = getIntProperty("WatorWorldSharkStartingEnergy");
        STARTING_VAL = getIntProperty("WatorWorldRepStartVal");
        REPRODUCE_VAL = getIntProperty("WatorWorldReproduceNum");

        setSharkEnergyValues();
    }

    private void setSharkEnergyValues(){
        for(Point point: getGrid().getPoints()){
            Cell cell = getGrid().getBoardCell(point);
            if(cell.getCurrentStatus() == SHARK){
                cell.setMiscellaneousVal(Arrays.asList(STARTING_VAL, SHARK_STARTING_ENERGY));
            } else if (cell.getCurrentStatus() == FISH){
                cell.setMiscellaneousVal(Arrays.asList(STARTING_VAL,STARTING_VAL));
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
        } else if(cell.getCurrentStatus() == SHARK){
            sharkMovement(cell, adjNeighbors);
        }
        return true;
    }

    private void fishMovement(Cell cell, List<Cell> adjacentNeighbors){
        List<Cell> adjNeigh = checkNumCellsThisCase(EMPTY, adjacentNeighbors);
        if(adjNeigh.size() > 0){
            int whichAdjacentCell = rand.nextInt(adjNeigh.size());
            Cell chosenNextCell = adjNeigh.get(whichAdjacentCell);
            fishReproduceCell(cell, chosenNextCell);
        }
    }

    private void sharkMovement(Cell cell, List<Cell> adjacentNeighbors){
        List<Cell> adjFishNeigh = checkNumCellsThisCase(FISH, adjacentNeighbors);
        List<Cell> adjEmptyNeigh = checkNumCellsThisCase(EMPTY, adjacentNeighbors);
        if(adjFishNeigh.size() > 0){
            int whichAdjacentCell = rand.nextInt(adjFishNeigh.size());
            Cell chosenNextCell = adjFishNeigh.get(whichAdjacentCell);
            int stepsAlive = cell.getMiscellaneousVal().get(0);
            int energy = cell.getMiscellaneousVal().get(1);
            energy += 2;
            cell.setMiscellaneousVal(Arrays.asList(stepsAlive, energy));
            sharkReproduceCell(cell, chosenNextCell);
        } else if(adjEmptyNeigh.size() > 0){
            int whichAdjacentCell = rand.nextInt(adjEmptyNeigh.size());
            Cell chosenNextCell = adjEmptyNeigh.get(whichAdjacentCell);
            sharkReproduceCell(cell, chosenNextCell);
        }
    }

    private void sharkReproduceCell(Cell cell, Cell chosenCell){
        int stepsAlive = cell.getMiscellaneousVal().get(0);
        int energy = cell.getMiscellaneousVal().get(1);
        energy -= 1;
        if(energy > 0) {
            chosenCell.setNextStatus(cell.getCurrentStatus());
            if (stepsAlive <= REPRODUCE_VAL) {
                cell.setNextStatus(EMPTY);
                stepsAlive += 1;
                chosenCell.setMiscellaneousVal(Arrays.asList(stepsAlive, energy));
            } else if (stepsAlive > REPRODUCE_VAL) {
                cell.setNextStatus(cell.getCurrentStatus());
                chosenCell.setMiscellaneousVal(Arrays.asList(STARTING_VAL, STARTING_VAL));
            }
            cell.setMiscellaneousVal(Arrays.asList(STARTING_VAL, STARTING_VAL));
        } else if (energy <= 0){
            chosenCell.setNextStatus(chosenCell.getCurrentStatus());
            cell.setCurrentStatus(EMPTY);
            cell.setMiscellaneousVal(Arrays.asList(STARTING_VAL, STARTING_VAL));
            chosenCell.setMiscellaneousVal(Arrays.asList(stepsAlive, energy));
        }
    }


    private void fishReproduceCell(Cell cell, Cell chosenCell){
        int stepsAlive = cell.getMiscellaneousVal().get(0);
        int energy = cell.getMiscellaneousVal().get(1);
        chosenCell.setNextStatus(cell.getCurrentStatus());
        if(stepsAlive <= REPRODUCE_VAL) {
            cell.setNextStatus(EMPTY);
            stepsAlive += 1;
            chosenCell.setMiscellaneousVal(Arrays.asList(stepsAlive,STARTING_VAL));
        } else if(stepsAlive > REPRODUCE_VAL){
            cell.setNextStatus(cell.getCurrentStatus());
            chosenCell.setMiscellaneousVal(Arrays.asList(STARTING_VAL,STARTING_VAL));
        }
        cell.setMiscellaneousVal(Arrays.asList(STARTING_VAL,STARTING_VAL));
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
