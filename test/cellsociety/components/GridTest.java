package cellsociety.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {
    private Grid myGrid;
    int[][] passedInGridArray = {{0,1,0},{1,0,1},{1,1,1}};
    int[][] expandedGridArray = {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,1,0,0,0},{0,0,1,0,1,0,0},{0,0,1,1,1,0,0},
            {0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};

    @BeforeEach
    public void setUp () {
        myGrid = new Grid(passedInGridArray);
    }

    @Test
    void testInitialization (){
        for(Point currPoint: myGrid.getPoints()){
            Cell myCell = myGrid.getBoardCell(currPoint);
            assertEquals(passedInGridArray[currPoint.y][currPoint.x], myCell.getCurrentStatus());
        }
    }

    @Test
    void checkCornerNeighbors (){
        Cell cornerUpperLeft = myGrid.getBoardCell(new Point(0,0));
        List<Cell> expectedNeighbors = Arrays.asList(null, null, null, new Cell(1, 1,0),
                new Cell(0, 1, 1), new Cell(1, 0,1), null, null);
        assertTrue(expectedNeighbors.equals(cornerUpperLeft.getNeighborCells()));
    }

    @Test
    void checkEdgeNeighbors () {
        Cell edgeCell = myGrid.getBoardCell(new Point(2,1));
        List<Cell> expectedNeighbors = Arrays.asList(new Cell(1, 1,0),
                new Cell(0, 2, 0), null, null, null,
                new Cell(1, 2,2), new Cell(1, 1,2),
                new Cell(0, 1,1));
        assertTrue(expectedNeighbors.equals(edgeCell.getNeighborCells()));
    }

    @Test
    void checkCenterNeighbors () {
        Cell centerCell = myGrid.getBoardCell(new Point(1,1));
        List<Cell> expectedNeighbors = Arrays.asList(new Cell(0, 0,0),
                new Cell(1, 1,0), new Cell(0, 2,0),
                new Cell(1, 2,1), new Cell(1, 2,2),
                new Cell(1, 1,2), new Cell(1, 0,2),
                new Cell(1, 0,1));
        assertTrue(expectedNeighbors.equals(centerCell.getNeighborCells()));
    }

    @Test
    void checkGridExpansion(){
        myGrid.expandGrid(2,2,2,2);
        for(Point currPoint: myGrid.getPoints()){
            Cell myCell = myGrid.getBoardCell(currPoint);
            assertEquals(expandedGridArray[currPoint.y][currPoint.x], myCell.getCurrentStatus());
        }
    }
}