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
    private int ENERGY_FROM_EATING_FISH;
    private int ENERGY_LOST_FROM_MOVING;
    private Random rand;

    public WaTorWorldModel(String filename){
        super(filename);
        EMPTY = getIntProperty("WatorWorldEmpty");
        FISH = getIntProperty("WatorWorldFish");
        SHARK = getIntProperty("WatorWorldShark");
        SHARK_STARTING_ENERGY = getIntProperty("WatorWorldSharkStartingEnergy");
        STARTING_VAL = getIntProperty("WatorWorldRepStartVal");
        REPRODUCE_VAL = getIntProperty("WatorWorldReproduceNum");
        ENERGY_FROM_EATING_FISH = getIntProperty("WatorWorldEnergyFromFish");
        ENERGY_LOST_FROM_MOVING = getIntProperty("WatorWorldEnergyLostFromMoving");
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
        List<Cell> neighbors = cell.getNeighborCells();
        int stepsAlive = cell.getMiscellaneousVal().get(0);
        int energy = cell.getMiscellaneousVal().get(1);
        if(cell.getCurrentStatus() == FISH){
            fishMovement(cell, neighbors, stepsAlive, energy);
        } else if(cell.getCurrentStatus() == SHARK){
            sharkMovement(cell, neighbors, stepsAlive, energy);
        }
        return true;
    }

    private void fishMovement(Cell cell, List<Cell> neighbors, int stepsAlive, int energy){
        List<Cell> adjNeigh = checkNumCellsThisCase(EMPTY, neighbors);
        if(adjNeigh.size() > 0){
            int whichAdjacentCell = rand.nextInt(adjNeigh.size());
            Cell chosenNextCell = adjNeigh.get(whichAdjacentCell);
            energy = STARTING_VAL;
            reproduceCell(cell, chosenNextCell, stepsAlive, energy);
        }
    }

    private void sharkMovement(Cell cell, List<Cell> neighbors, int stepsAlive, int energy){
        List<Cell> adjFishNeigh = checkNumCellsThisCase(FISH, neighbors);
        List<Cell> adjEmptyNeigh = checkNumCellsThisCase(EMPTY, neighbors);
        if(adjFishNeigh.size() > 0){
            Cell chosenNextCell = adjFishNeigh.get(rand.nextInt(adjFishNeigh.size()));
            sharkReproduceCell(cell, chosenNextCell, stepsAlive, energy + ENERGY_FROM_EATING_FISH);
        } else if(adjEmptyNeigh.size() > 0){
            Cell chosenNextCell = adjEmptyNeigh.get(rand.nextInt(adjEmptyNeigh.size()));
            sharkReproduceCell(cell, chosenNextCell, stepsAlive, energy);
        }
    }

    private void sharkReproduceCell(Cell cell, Cell chosenCell, int stepsAlive, int energy){
        energy -= ENERGY_LOST_FROM_MOVING;
        if(energy > 0) {
            reproduceCell(cell, chosenCell, stepsAlive, energy);
        } else if (energy <= 0){
            chosenCell.setNextStatus(chosenCell.getCurrentStatus());
            cell.setCurrentStatus(EMPTY);
            cell.setMiscellaneousVal(Arrays.asList(STARTING_VAL, STARTING_VAL));
            chosenCell.setMiscellaneousVal(Arrays.asList(stepsAlive, energy));
        }
    }


    private void reproduceCell(Cell cell, Cell chosenCell, int stepsAlive, int energy){
        chosenCell.setNextStatus(cell.getCurrentStatus());
        if(stepsAlive <= REPRODUCE_VAL) {
            cell.setNextStatus(EMPTY);
            stepsAlive += 1;
            chosenCell.setMiscellaneousVal(Arrays.asList(stepsAlive,energy));
        } else if(stepsAlive > REPRODUCE_VAL){
            cell.setNextStatus(cell.getCurrentStatus());
            chosenCell.setMiscellaneousVal(Arrays.asList(STARTING_VAL,STARTING_VAL));
        }
        cell.setMiscellaneousVal(Arrays.asList(STARTING_VAL,STARTING_VAL));
    }


}
