package cellsociety.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cell {
    private final int[] xyPosition = new int[2];
    private List<Cell> neighborCells;
    private int currentStatus;
    private int nextStatus;
    private List<Integer> miscellaneousVal;

    public Cell(int statusPassedIn, int xPosition, int yPosition){
        setXyPosition(xPosition, yPosition);
        neighborCells = new ArrayList<>();
        currentStatus = statusPassedIn;
        nextStatus = currentStatus;
        miscellaneousVal = new ArrayList<>();
    }

    public void changeStatus(){
        currentStatus = nextStatus;
    }

    public void clearNeighborCells(){
        neighborCells = new ArrayList<>();
    }

    public void setNextStatus(int newStatus){
        nextStatus = newStatus;
    }

    public void setCurrentStatus(int newCurrentStatus){
        currentStatus = newCurrentStatus;
        nextStatus = newCurrentStatus;
    }

    public void setXyPosition(int xPosition, int yPosition){
        xyPosition[0] = xPosition;
        xyPosition[1] = yPosition;
    }

    public void setMiscellaneousVal(List<Integer> valueList){
        miscellaneousVal = valueList;
    }

    public void setNeighborCells(List<Cell> neighborCellsPassedIn){
        neighborCells = neighborCellsPassedIn;
    }

    public int[] getXyPosition(){
        return xyPosition;
    }

    public int getCurrentStatus(){
        return currentStatus;
    }

    public List<Integer> getMiscellaneousVal(){
        return miscellaneousVal;
    }

    public List<Cell> getNeighborCells(){
        return neighborCells;
    }

    @Override
    public boolean equals(Object obj) {
        final Cell other = (Cell) obj;
        if (Arrays.equals(xyPosition, other.xyPosition) && this.currentStatus == other.currentStatus) {
            return true;
        }
        return false;
    }

    @Override
    public String toString () {
        return xyPosition[0] + " " + xyPosition[1] + " with status " + currentStatus;
    }
}
