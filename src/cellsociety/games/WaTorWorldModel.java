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
        rand = new Random();
        setSharkEnergyValues();
    }

    private void setSharkEnergyValues(){
        for(Point point: getGrid().getPoints()){
            Cell cell = getGrid().getBoardCell(point);
            if(cell.getCurrentStatus() == SHARK){
                cell.setMiscellaneousVal(Arrays.asList(STARTING_VAL, SHARK_STARTING_ENERGY));
            } else {
                cell.setMiscellaneousVal(Arrays.asList(STARTING_VAL, STARTING_VAL));
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
            Cell chosenNextCell = adjNeigh.get(getRandomInt(adjNeigh.size() - 1));
            energy = STARTING_VAL;
            reproduceCell(cell, chosenNextCell, stepsAlive, energy, STARTING_VAL);
        }
    }

    private void sharkMovement(Cell cell, List<Cell> neighbors, int stepsAlive, int energy){
        List<Cell> adjFishNeigh = checkNumCellsThisCase(FISH, neighbors);
        List<Cell> adjEmptyNeigh = checkNumCellsThisCase(EMPTY, neighbors);
        if(adjFishNeigh.size() > 0){
            Cell chosenNextCell = adjFishNeigh.get(adjFishNeigh.size() - 1);
            sharkReproduceCell(cell, chosenNextCell, stepsAlive, energy + ENERGY_FROM_EATING_FISH);
        } else if(adjEmptyNeigh.size() > 0){
            Cell chosenNextCell = adjEmptyNeigh.get(adjEmptyNeigh.size() - 1);
            sharkReproduceCell(cell, chosenNextCell, stepsAlive, energy);
        }
    }

    private int getRandomInt(int max){
        return (int)(Math.random() * (max + 1));
    }

    private void sharkReproduceCell(Cell cell, Cell chosenCell, int stepsAlive, int energy){
        energy -= ENERGY_LOST_FROM_MOVING;
        if(energy > 0) {
            reproduceCell(cell, chosenCell, stepsAlive, energy, SHARK_STARTING_ENERGY);
        } else if (energy <= 0){
            chosenCell.setNextStatus(chosenCell.getCurrentStatus());
            cell.setCurrentStatus(EMPTY);
            cell.setMiscellaneousVal(Arrays.asList(STARTING_VAL, STARTING_VAL));
            chosenCell.setMiscellaneousVal(Arrays.asList(stepsAlive, energy));
        }
    }


    private void reproduceCell(Cell cell, Cell chosenCell, int stepsAlive, int energy, int startingEnergy){
        chosenCell.setNextStatus(cell.getCurrentStatus());
        if(stepsAlive <= REPRODUCE_VAL) {
            cell.setNextStatus(EMPTY);
            stepsAlive += 1;
            chosenCell.setMiscellaneousVal(Arrays.asList(stepsAlive,energy));
        } else if(stepsAlive > REPRODUCE_VAL){
            cell.setNextStatus(cell.getCurrentStatus());
            chosenCell.setMiscellaneousVal(Arrays.asList(STARTING_VAL, startingEnergy));
        }
        cell.setMiscellaneousVal(Arrays.asList(STARTING_VAL, startingEnergy));
    }

    @Override
    public void changeCellOnClick(Point point) {
        Cell cell = getGrid().getBoardCell(point);
        cell.setCurrentStatus((cell.getCurrentStatus() + 1) % 3);
        if (cell.getCurrentStatus() == FISH){
            cell.setMiscellaneousVal(Arrays.asList(STARTING_VAL,STARTING_VAL));
        } else if (cell.getCurrentStatus() == SHARK){
            cell.setMiscellaneousVal((Arrays.asList(STARTING_VAL, SHARK_STARTING_ENERGY)));
        }
        update();
    }

    @Override
    protected void populateGameConditions () {
        super.populateGameConditions();
        EMPTY = getIntProperty("WatorWorldEmpty");
        FISH = getIntProperty("WatorWorldFish");
        SHARK = getIntProperty("WatorWorldShark");
        SHARK_STARTING_ENERGY = getIntProperty("WatorWorldSharkStartingEnergy");
        STARTING_VAL = getIntProperty("WatorWorldRepStartVal");
        REPRODUCE_VAL = getIntProperty("WatorWorldReproduceNum");
        ENERGY_FROM_EATING_FISH = getIntProperty("WatorWorldEnergyFromFish");
        ENERGY_LOST_FROM_MOVING = getIntProperty("WatorWorldEnergyLostFromMoving");
    }
}
