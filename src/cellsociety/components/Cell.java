package cellsociety.components;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cell {
    private final int[] xyPosition = new int[2];
    private List<Cell> neighborCells;
    private boolean isEdge;
    private boolean isCorner;
    private int currentStatus;
    private int nextStatus;

    public Cell(int statusPassedIn, int xPosition, int yPosition){
        xyPosition[0] = xPosition;
        xyPosition[1] = yPosition;
        neighborCells = new ArrayList<>();
        currentStatus = statusPassedIn;
        nextStatus = currentStatus;
        isCorner = false;
        isEdge = false;
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

    public void setCurrentStatus(int newCurrentStatus){currentStatus = newCurrentStatus;}

    public void setEdge(boolean edgeCase){
        isEdge = edgeCase;
    }

    public void setCorner(boolean cornerCase){
        isCorner = cornerCase;
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

    public List<Cell> getNeighborCells(){
        return neighborCells;
    }

    public boolean getEdge(){
        return isEdge;
    }

    public boolean getCorner(){
        return isCorner;
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
