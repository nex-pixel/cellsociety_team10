package cellsociety.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SquareGridTest {
    private SquareGrid myGrid_Complete_Finite, myGrid_Complete_Torus, myGrid_Complete_Cylinder;
    private SquareGrid myGrid_Edge_Finite, myGrid_Edge_Torus;
    private SquareGrid myGrid_BottomHalf_Torus;
    private List<SquareGrid> myGrids, myGridsComplete;
    private int EDGE_POLICY_FINITE = 0;
    private int EDGE_POLICY_TORUS = 1;
    private int EDGE_POLICY_CYLINDER = 2;

    private int NEIGHBOR_MODE_COMPLETE = 0;
    private int NEIGHBOR_MODE_EDGE = 1;
    private int NEIGHBOR_MODE_BOTTOM_HALF = 2;

    int[][] passedInGridArray = {{0,1,0},{1,0,1},{1,1,1}};
    int[][] expandedGridArray = {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,1,0,0,0},{0,0,1,0,1,0,0},{0,0,1,1,1,0,0},
            {0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};

    @BeforeEach
    public void setUp () {
        myGrid_Complete_Finite = new SquareGrid(passedInGridArray, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_FINITE);
        myGrid_Complete_Torus = new SquareGrid(passedInGridArray, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_TORUS);
        myGrid_Complete_Cylinder = new SquareGrid(passedInGridArray, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_CYLINDER);

        myGrid_Edge_Finite = new SquareGrid(passedInGridArray, NEIGHBOR_MODE_EDGE, EDGE_POLICY_FINITE);
        myGrid_Edge_Torus = new SquareGrid(passedInGridArray, NEIGHBOR_MODE_EDGE, EDGE_POLICY_TORUS);
        myGrid_BottomHalf_Torus = new SquareGrid(passedInGridArray, NEIGHBOR_MODE_BOTTOM_HALF, EDGE_POLICY_TORUS);

        myGrids = new ArrayList<>();
        myGrids.add(myGrid_Complete_Finite);
        myGrids.add(myGrid_Complete_Torus);
        myGrids.add(myGrid_Complete_Cylinder);
        myGrids.add(myGrid_Edge_Finite);
        myGrids.add(myGrid_Edge_Torus);
        myGrids.add(myGrid_BottomHalf_Torus);

        myGridsComplete = new ArrayList<>();
        myGridsComplete.add(myGrid_Complete_Finite);
        myGridsComplete.add(myGrid_Complete_Torus);
        myGridsComplete.add(myGrid_Complete_Cylinder);
    }

    @Test
    void testInitialization (){
        for (SquareGrid grid: myGrids) {
            for (Point currPoint : grid.getPoints()) {
                Cell myCell = grid.getBoardCell(currPoint);
                assertEquals(passedInGridArray[currPoint.y][currPoint.x], myCell.getCurrentStatus());
            }
        }
    }

    @Test
    void checkCornerNeighbors_Complete_Finite (){
        Cell cornerUpperLeft = myGrid_Complete_Finite.getBoardCell(new Point(0,0));
        List<Cell> expectedNeighbors = Arrays.asList(null,
                null,
                null,
                new Cell(1, 1,0),
                new Cell(0, 1, 1),
                new Cell(1, 0,1),
                null,
                null);
        assertTrue(expectedNeighbors.equals(cornerUpperLeft.getNeighborCells()));
    }

    @Test
    void checkCornerNeighbors_Complete_Torus () {
        Cell cornerUpperLeft = myGrid_Complete_Torus.getBoardCell(new Point(0,0));
        List<Cell> expectedNeighbors = Arrays.asList(new Cell(1, 2, 2),
                new Cell(1, 0, 2),
                new Cell(1, 1, 2),
                new Cell(1, 1,0),
                new Cell(0, 1, 1),
                new Cell(1, 0,1),
                new Cell(1, 2, 1),
                new Cell(0, 2, 0));
        assertTrue(expectedNeighbors.equals(cornerUpperLeft.getNeighborCells()));
    }

    @Test
    void checkCornerNeighbors_Complete_Cylinder () {
        Cell cornerUpperLeft = myGrid_Complete_Cylinder.getBoardCell(new Point(0,0));
        List<Cell> expectedNeighbors = Arrays.asList(null,
                null,
                null,
                new Cell(1, 1,0),
                new Cell(0, 1, 1),
                new Cell(1, 0,1),
                new Cell(1, 2, 1),
                new Cell(0, 2, 0));
        assertTrue(expectedNeighbors.equals(cornerUpperLeft.getNeighborCells()));
    }

    @Test
    void checkCornerNeighbors_Edge_Finite () {
        Cell cornerUpperLeft = myGrid_Edge_Finite.getBoardCell(new Point(0,0));
        List<Cell> expectedNeighbors = Arrays.asList(null,
                new Cell(1, 1,0),
                new Cell(1, 0,1),
                null);
        assertTrue(expectedNeighbors.equals(cornerUpperLeft.getNeighborCells()));
    }

    @Test
    void checkCornerNeighbors_Edge_TORUS () {
        Cell cornerUpperLeft = myGrid_Edge_Torus.getBoardCell(new Point(0,0));
        List<Cell> expectedNeighbors = Arrays.asList(new Cell(1, 0, 2),
                new Cell(1, 1,0),
                new Cell(1, 0,1),
                new Cell(0, 2, 0));
        assertTrue(expectedNeighbors.equals(cornerUpperLeft.getNeighborCells()));
    }

    @Test
    void checkCornerNeighbors_BottomHalf_Torus () {
        Cell cornerUpperLeft = myGrid_BottomHalf_Torus.getBoardCell(new Point(0,0));
        List<Cell> expectedNeighbors = Arrays.asList(new Cell(1, 1,0),
                new Cell(0, 1, 1),
                new Cell(1, 0,1),
                new Cell(1, 2, 1),
                new Cell(0, 2, 0));
        assertTrue(expectedNeighbors.equals(cornerUpperLeft.getNeighborCells()));
    }

    @Test
    void checkEdgeNeighbors_Complete_Finite () {
        Cell edgeCell = myGrid_Complete_Finite.getBoardCell(new Point(2,1));
        List<Cell> expectedNeighbors = Arrays.asList(new Cell(1, 1,0),
                new Cell(0, 2, 0),
                null,
                null,
                null,
                new Cell(1, 2,2),
                new Cell(1, 1,2),
                new Cell(0, 1,1));
        assertTrue(expectedNeighbors.equals(edgeCell.getNeighborCells()));
    }

    @Test
    void checkEdgeNeighbors_Complete_Torus () {
        Cell edgeCell = myGrid_Complete_Torus.getBoardCell(new Point(2,1));
        List<Cell> expectedNeighbors = Arrays.asList(new Cell(1, 1,0),
                new Cell(0, 2, 0),
                new Cell(0, 0, 0),
                new Cell(1, 0, 1),
                new Cell(1, 0, 2),
                new Cell(1, 2, 2),
                new Cell(1, 1, 2),
                new Cell(0, 1, 1));
        assertTrue(expectedNeighbors.equals(edgeCell.getNeighborCells()));
    }

    @Test
    void checkEdgeNeighbors_Complete_Cylinder () {
        Cell edgeCell = myGrid_Complete_Cylinder.getBoardCell(new Point(2,1));
        List<Cell> expectedNeighbors = Arrays.asList(new Cell(1, 1,0),
                new Cell(0, 2, 0),
                new Cell(0, 0, 0),
                new Cell(1, 0, 1),
                new Cell(1, 0, 2),
                new Cell(1, 2, 2),
                new Cell(1, 1, 2),
                new Cell(0, 1, 1));
        assertTrue(expectedNeighbors.equals(edgeCell.getNeighborCells()));
    }

    @Test
    void checkCenterNeighbors () {
        for (SquareGrid grid: myGridsComplete) {
            Cell centerCell = grid.getBoardCell(new Point(1, 1));
            List<Cell> expectedNeighbors = Arrays.asList(new Cell(0, 0, 0),
                    new Cell(1, 1, 0),
                    new Cell(0, 2, 0),
                    new Cell(1, 2, 1),
                    new Cell(1, 2, 2),
                    new Cell(1, 1, 2),
                    new Cell(1, 0, 2),
                    new Cell(1, 0, 1));
            assertTrue(expectedNeighbors.equals(centerCell.getNeighborCells()));
        }
    }

    @Test
    void checkChangeOfNeighborMode (){
        Grid originalGridFinite = myGrid_Complete_Finite;
        originalGridFinite.changeNeighborMode(NEIGHBOR_MODE_EDGE);
        Grid expectedGridFinite = myGrid_Edge_Finite;
        assertEquals(expectedGridFinite, originalGridFinite);

    }

    @Test
    void checkGridExpansion(){
        for (SquareGrid grid: myGrids) {
            grid.expandGrid(2, 2, 2, 2);
            for (Point currPoint : grid.getPoints()) {
                Cell myCell = grid.getBoardCell(currPoint);
                assertEquals(expandedGridArray[currPoint.y][currPoint.x], myCell.getCurrentStatus());
            }
        }
    }
}