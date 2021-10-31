package cellsociety.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HexagonGridTest {
    private HexagonGrid myGrid_Complete_Finite, myGrid_Complete_Torus, myGrid_Complete_Cylinder;
    private HexagonGrid myGrid_Edge_Finite, myGrid_Edge_Torus;
    private HexagonGrid myGrid_BottomHalf_Torus;
    private List<HexagonGrid> myGrids, myGridsComplete;
    private int EDGE_POLICY_FINITE = 0;
    private int EDGE_POLICY_TORUS = 1;
    private int EDGE_POLICY_CYLINDER = 2;

    private int NEIGHBOR_MODE_COMPLETE = 0;
    private int NEIGHBOR_MODE_EDGE = 1;
    private int NEIGHBOR_MODE_BOTTOM_HALF = 2;

    int[][] gridArrayOddRows = {{0,1,0},{1,0,1},{1,1,1}};
    int[][] gridArrayEvenRows = {{0,1,0},{1,0,1},{1,1,1},{0,0,1}};
    int[][] expandedGridArray = {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,1,0,0,0},{0,0,1,0,1,0,0},{0,0,1,1,1,0,0},
            {0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};

    @BeforeEach
    public void setUp () {
        myGrid_Complete_Finite = new HexagonGrid(gridArrayOddRows, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_FINITE);
//        myGrid_Complete_Torus = new HexagonGrid(passedInGridArray, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_TORUS);
//        myGrid_Complete_Cylinder = new HexagonGrid(passedInGridArray, NEIGHBOR_MODE_COMPLETE, EDGE_POLICY_CYLINDER);
//
//        myGrid_Edge_Finite = new HexagonGrid(passedInGridArray, NEIGHBOR_MODE_EDGE, EDGE_POLICY_FINITE);
//        myGrid_Edge_Torus = new HexagonGrid(passedInGridArray, NEIGHBOR_MODE_EDGE, EDGE_POLICY_TORUS);
//        myGrid_BottomHalf_Torus = new HexagonGrid(passedInGridArray, NEIGHBOR_MODE_BOTTOM_HALF, EDGE_POLICY_TORUS);

        myGrids = new ArrayList<>();
        myGrids.add(myGrid_Complete_Finite);
//        myGrids.add(myGrid_Complete_Torus);
//        myGrids.add(myGrid_Complete_Cylinder);
//        myGrids.add(myGrid_Edge_Finite);
//        myGrids.add(myGrid_Edge_Torus);
//        myGrids.add(myGrid_BottomHalf_Torus);
//
//        myGridsComplete = new ArrayList<>();
//        myGridsComplete.add(myGrid_Complete_Finite);
//        myGridsComplete.add(myGrid_Complete_Torus);
//        myGridsComplete.add(myGrid_Complete_Cylinder);
    }

    @Test
    void testInitialization (){
        for (HexagonGrid grid: myGrids) {
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

}
