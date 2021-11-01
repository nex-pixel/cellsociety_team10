package cellsociety.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TriangleGridTest {

    private TriangleGrid myGrid_Complete_Finite, myGrid_Complete_Torus, myGrid_Complete_Cylinder;
    private TriangleGrid myGrid_Edge_Torus;

    private List<TriangleGrid> myGrids;

    private int EDGE_POLICY_FINITE = 0;
    private int EDGE_POLICY_TORUS = 1;
    private int EDGE_POLICY_CYLINDER = 2;

    private int NEIGHBOR_MODE_COMPLETE = 0;
    private int NEIGHBOR_MODE_EDGE = 1;
    private int NEIGHBOR_MODE_BOTTOM_HALF = 2;

    int[][] passedInGridArray = {{0,1,0},{1,0,1},{1,1,1}};

    @BeforeEach
    public void setUp() {
        myGrid_Complete_Finite = new TriangleGrid(passedInGridArray, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_FINITE);
        myGrid_Complete_Torus = new TriangleGrid(passedInGridArray, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_TORUS);
        myGrid_Complete_Cylinder = new TriangleGrid(passedInGridArray, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_CYLINDER);

        myGrid_Edge_Torus = new TriangleGrid(passedInGridArray, NEIGHBOR_MODE_EDGE, EDGE_POLICY_TORUS);


        myGrids = new ArrayList<>();
        myGrids.add(myGrid_Complete_Finite);
        myGrids.add(myGrid_Complete_Torus);
        myGrids.add(myGrid_Complete_Cylinder);
        myGrids.add(myGrid_Edge_Torus);
    }

    @Test
    void testInitialization (){
        for (TriangleGrid grid: myGrids) {
            for (Point currPoint : grid.getPoints()) {
                Cell myCell = grid.getBoardCell(currPoint);
                assertEquals(passedInGridArray[currPoint.y][currPoint.x], myCell.getCurrentStatus());
            }
        }
    }

    @Test
    void checkCornerNeighbors_Complete_Torus () {
        Cell cornerUpperLeft = myGrid_Complete_Torus.getBoardCell(new Point(0,0));
        List<Cell> expectedNeighbors = Arrays.asList(new Cell(1, 2, 2),
                new Cell(1, 0, 2),
                new Cell(1, 1, 2),
                new Cell(1, 1,0),
                new Cell(0, 2,0),
                new Cell(1, 2,1),
                new Cell(0, 1, 1),
                new Cell(1, 0,1),
                new Cell(1, 2, 1),
                new Cell(0, 1, 1),
                new Cell(1, 1, 0),
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
                new Cell(0, 2,0),
                new Cell(1, 2,1),
                new Cell(0, 1, 1),
                new Cell(1, 0,1),
                new Cell(1, 2, 1),
                new Cell(0, 1, 1),
                new Cell(1, 1, 0),
                new Cell(0, 2, 0));
        assertTrue(expectedNeighbors.equals(cornerUpperLeft.getNeighborCells()));
    }

    @Test
    void checkCornerNeighbors_Edge_Torus () {
        Cell cornerUpperLeft = myGrid_Edge_Torus.getBoardCell(new Point(0,0));
        List<Cell> expectedNeighbors = Arrays.asList(new Cell(1, 1,0),
                new Cell(1, 0,1),
                new Cell(0, 2, 0));
        assertTrue(expectedNeighbors.equals(cornerUpperLeft.getNeighborCells()));
    }

}
