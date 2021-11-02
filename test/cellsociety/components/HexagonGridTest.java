package cellsociety.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HexagonGridTest {
    private HexagonGrid myGrid_Complete_Finite_OddRows, myGrid_Complete_Torus_OddRows, myGrid_BottomHalf_Torus_OddRows;
    private HexagonGrid myGrid_Edge_Torus_EvenRows, myGrid_BottomHalf_Torus_EvenRows, myGrid_Complete_Cylinder_EvenRows;
    private List<HexagonGrid> myGridsOdd, myGridsEven;
    private int EDGE_POLICY_FINITE = 0;
    private int EDGE_POLICY_TORUS = 1;
    private int EDGE_POLICY_CYLINDER = 2;

    private int NEIGHBOR_MODE_COMPLETE = 0;
    private int NEIGHBOR_MODE_EDGE = 1;
    private int NEIGHBOR_MODE_BOTTOM_HALF = 2;

    int[][] gridArrayOddRows = {{0,1,0},{1,0,1},{1,1,1}};
    int[][] gridArrayEvenRows = {{0,1,0},{1,0,1},{1,1,1},{0,0,1}};
    int[][] expandedGridArrayOdd = {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,1,0,0,0},{0,0,1,0,1,0,0},{0,0,1,1,1,0,0},
            {0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};
    int[][] expandedGridArrayEven = {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,1,0,0,0},{0,0,1,0,1,0,0},{0,0,1,1,1,0,0},
            {0,0,0,0,1,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};

    @BeforeEach
    public void setUp () {
        myGrid_Complete_Finite_OddRows = new HexagonGrid(gridArrayOddRows, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_FINITE);
        myGrid_Complete_Torus_OddRows = new HexagonGrid(gridArrayOddRows, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_TORUS);
        myGrid_BottomHalf_Torus_OddRows = new HexagonGrid(gridArrayOddRows, NEIGHBOR_MODE_BOTTOM_HALF, EDGE_POLICY_TORUS);
        myGrid_Edge_Torus_EvenRows = new HexagonGrid(gridArrayEvenRows, NEIGHBOR_MODE_EDGE, EDGE_POLICY_TORUS);
        myGrid_BottomHalf_Torus_EvenRows = new HexagonGrid(gridArrayEvenRows, NEIGHBOR_MODE_BOTTOM_HALF, EDGE_POLICY_TORUS);
        myGrid_Complete_Cylinder_EvenRows = new HexagonGrid(gridArrayEvenRows, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_CYLINDER);

        myGridsOdd = new ArrayList<>();
        myGridsOdd.add(myGrid_Complete_Finite_OddRows);
        myGridsOdd.add(myGrid_Complete_Torus_OddRows);
        myGridsOdd.add( myGrid_BottomHalf_Torus_OddRows);

        myGridsEven = new ArrayList<>();
        myGridsEven.add(myGrid_Edge_Torus_EvenRows);
        myGridsEven.add(myGrid_BottomHalf_Torus_EvenRows);
        myGridsEven.add(myGrid_Complete_Cylinder_EvenRows);
    }

    @Test
    void testInitializationOdd (){
        for (HexagonGrid grid: myGridsOdd) {
            for (Point currPoint : grid.getPoints()) {
                Cell myCell = grid.getBoardCell(currPoint);
                if (currPoint.y % 2 == 0) {
                    assertEquals(gridArrayOddRows[currPoint.y][(currPoint.x - 1)/2], myCell.getCurrentStatus());
                }
                else {
                    assertEquals(gridArrayOddRows[currPoint.y][currPoint.x / 2], myCell.getCurrentStatus());
                }
            }
        }
    }

    @Test
    void testInitializationEven (){
        for (HexagonGrid grid: myGridsEven) {
            for (Point currPoint : grid.getPoints()) {
                Cell myCell = grid.getBoardCell(currPoint);
                if (currPoint.y % 2 == 0) {
                    assertEquals(gridArrayEvenRows[currPoint.y][(currPoint.x - 1)/2], myCell.getCurrentStatus());
                }
                else {
                    assertEquals(gridArrayEvenRows[currPoint.y][currPoint.x / 2], myCell.getCurrentStatus());
                }
            }
        }
    }


    @Test
    void checkCornerNeighbors_Complete_Finite_Odd (){
        Cell cornerUpperLeft = myGrid_Complete_Finite_OddRows.getBoardCell(new Point(1,0));
        List<Cell> expectedNeighbors = Arrays.asList(
                new Cell(1, 3,0),
                new Cell(0, 2, 1),
                new Cell(1, 0,1));
        assertTrue(expectedNeighbors.equals(cornerUpperLeft.getNeighborCells()));
    }

    @Test
    void checkCornerNeighbors_Complete_Torus_Odd (){
        Cell cornerUpperLeft = myGrid_Complete_Torus_OddRows.getBoardCell(new Point(1,0));
        List<Cell> expectedNeighbors = Arrays.asList(
                new Cell(1, 3,0),
                new Cell(0, 2, 1),
                new Cell(1, 0,1),
                new Cell(0, 5, 0));
        assertTrue(expectedNeighbors.equals(cornerUpperLeft.getNeighborCells()));
    }

    @Test
    void checkCornerNeighbors_Edge_Torus_Even (){
        Cell cornerUpperLeft = myGrid_Edge_Torus_EvenRows.getBoardCell(new Point(1,0));
        List<Cell> expectedNeighbors = Arrays.asList(
                new Cell(0, 0, 3),
                new Cell(0, 2, 3),
                new Cell(1, 3,0),
                new Cell(0, 2, 1),
                new Cell(1, 0,1),
                new Cell(0, 5, 0));
        assertTrue(expectedNeighbors.equals(cornerUpperLeft.getNeighborCells()));
    }

    @Test
    void checkCornerNeighbors_BottomHalf_Torus_Even (){
        Cell cornerUpperLeft = myGrid_BottomHalf_Torus_EvenRows.getBoardCell(new Point(1,0));
        List<Cell> expectedNeighbors = Arrays.asList(
                new Cell(1, 3,0),
                new Cell(0, 2, 1),
                new Cell(1, 0,1),
                new Cell(0, 5, 0));
        assertTrue(expectedNeighbors.equals(cornerUpperLeft.getNeighborCells()));
    }

    @Test
    void checkCornerNeighbors_BottomHalf_Torus_Odd (){
        Cell cornerUpperLeft = myGrid_BottomHalf_Torus_OddRows.getBoardCell(new Point(1,2));
        List<Cell> expectedNeighbors = Arrays.asList(
                new Cell(1, 3,2),
                new Cell(1, 5,2));
        assertTrue(expectedNeighbors.equals(cornerUpperLeft.getNeighborCells()));
    }

    @Test
    void checkCornerNeighbors_Complete_Cylinder_Even (){
        Cell cornerUpperLeft = myGrid_Complete_Cylinder_EvenRows.getBoardCell(new Point(1,0));
        List<Cell> expectedNeighbors = Arrays.asList(
                new Cell(1, 3,0),
                new Cell(0, 2, 1),
                new Cell(1, 0,1),
                new Cell(0, 5, 0));
        assertTrue(expectedNeighbors.equals(cornerUpperLeft.getNeighborCells()));
    }

    @Test
    void checkChangeOfNeighborModeOddRows () {
        neighborModeCheckSpecificRowType(gridArrayOddRows);
    }

    @Test
    void checkChangeOfNeighborModeEvenRows () {
        neighborModeCheckSpecificRowType(gridArrayEvenRows);
    }

    private void neighborModeCheckSpecificRowType(int[][] array) {
        //Complete to edge
        Grid originalGrid = new HexagonGrid(array, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_FINITE);
        originalGrid.changeNeighborMode(NEIGHBOR_MODE_EDGE);
        Grid expectedGridEdge = new HexagonGrid(array, NEIGHBOR_MODE_EDGE, EDGE_POLICY_FINITE);
        checkPointCellValues(expectedGridEdge, originalGrid);

        //edge to bottom half
        originalGrid.changeNeighborMode(NEIGHBOR_MODE_BOTTOM_HALF);
        Grid expectedGridBottomHalf = new HexagonGrid(array, NEIGHBOR_MODE_BOTTOM_HALF, EDGE_POLICY_FINITE);
        checkPointCellValues(expectedGridBottomHalf, originalGrid);

        //bottom half to complete
        originalGrid.changeNeighborMode(NEIGHBOR_MODE_COMPLETE);
        Grid expectedGridComplete = new HexagonGrid(array, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_FINITE);
        checkPointCellValues(expectedGridComplete, originalGrid);
    }

    @Test
    void checkChangeOfEdgePolicyOddRows(){
        edgePolicyCheckSpecificRowType(gridArrayOddRows);
    }

    @Test
    void checkChangeOfEdgePolicyEvenRows(){
        edgePolicyCheckSpecificRowType(gridArrayEvenRows);
    }

    private void edgePolicyCheckSpecificRowType(int[][] gridArrayOddRows) {
        //Finite to cylinder
        Grid originalGrid = new HexagonGrid(gridArrayOddRows, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_FINITE);
        originalGrid.changeEdgePolicy(EDGE_POLICY_CYLINDER);
        Grid expectedGridCylinder = new HexagonGrid(gridArrayOddRows, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_CYLINDER);
        checkPointCellValues(expectedGridCylinder, originalGrid);

        //Cylinder to torus
        originalGrid.changeEdgePolicy(EDGE_POLICY_TORUS);
        Grid expectedGridTorus = new HexagonGrid(gridArrayOddRows, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_TORUS);
        checkPointCellValues(expectedGridTorus, originalGrid);

        //torus to cylinder
        originalGrid.changeEdgePolicy(EDGE_POLICY_FINITE);
        Grid expectedGridFinite = new HexagonGrid(gridArrayOddRows, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_FINITE);
        checkPointCellValues(expectedGridFinite, originalGrid);
    }

    @Test
    void checkCompleteChangeNeighborEdge(){
        //Odd number of rows was more risky so better check
        Grid expectedGrid = myGrid_Complete_Finite_OddRows;
        Grid originalGrid = new HexagonGrid(gridArrayOddRows, NEIGHBOR_MODE_BOTTOM_HALF, EDGE_POLICY_TORUS);
        originalGrid.changeNeighborMode(NEIGHBOR_MODE_COMPLETE);
        originalGrid.changeEdgePolicy(EDGE_POLICY_FINITE);
        checkPointCellValues(expectedGrid, originalGrid);
    }

    @Test
    void checkGridExpansion(){
        HexagonGrid expandedGridOdd = new HexagonGrid(expandedGridArrayOdd, 0, 0);
        for (HexagonGrid grid: myGridsOdd) {
            grid.expandGrid(2, 2, 2, 2);
            checkPointCellValues(expandedGridOdd, grid);
        }

        HexagonGrid expandedGridEven = new HexagonGrid(expandedGridArrayEven, 0, 0);
        for (HexagonGrid grid: myGridsEven) {
            grid.expandGrid(2, 2, 2, 2);
            checkPointCellValues(expandedGridEven, grid);
        }
    }

    private void checkPointCellValues(Grid expandedGrid, Grid grid) {
        for (Point currPoint : grid.getPoints()) {
            Cell myCell = grid.getBoardCell(currPoint);
            assertEquals(expandedGrid.getBoardCell(currPoint).getCurrentStatus(), myCell.getCurrentStatus());
        }
    }

}
