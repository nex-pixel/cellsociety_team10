package cellsociety.components;

import org.assertj.core.internal.bytebuddy.build.ToStringPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {
    private Grid myGrid;
    int[][] passedInGridArray = {{0,1,0},{1,0,1},{1,1,1}};

    @BeforeEach
    public void setUp() {
        myGrid = new Grid(passedInGridArray);
    }

    @Test
    void checkStatus(){
        int i = 0;
        int j = 0;
        for(Point currPoint: myGrid.getCells().keySet()){
            currPoint.getX();
            currPoint.getY();
            Cell myCell = myGrid.getCells().get(currPoint);
            assertEquals(passedInGridArray[i][j], myCell.getCurrentStatus());
            i++;
            if(i >= passedInGridArray[0].length){
                i = 0;
                j++;
            }
        }
    }

    @Test
    void checkNeighbors(){
        Cell cornerNeighborCheck = myGrid.getCells().get(new Point(0,0));
        List<Cell> expectedNeighbors = Arrays.asList(null, null, null, null, new Cell(1, 1,0),
                null, new Cell(0, 1, 1), new Cell(1, 0,1));
        assertEquals(expectedNeighbors, cornerNeighborCheck.getNeighborCells());
        Cell edgeNeighborCheck = myGrid.getCells().get(new Point(2,1));
        expectedNeighbors = Arrays.asList(new Cell(1, 1,0),
                new Cell(0, 2, 0), null, null, null,
                new Cell(1, 2,2), new Cell(1, 1,2),
                new Cell(0, 1,1));
        assertEquals(expectedNeighbors, edgeNeighborCheck.getNeighborCells());
        Cell centerNeighborCheck = myGrid.getCells().get(new Point(1,1));
        expectedNeighbors = Arrays.asList(new Cell(0, 0,0),
                new Cell(1, 1,0), new Cell(0, 2,0),
                new Cell(1, 2,1), new Cell(1, 2,2),
                new Cell(1, 1,2), new Cell(1, 0,2),
                new Cell(1, 0,1));
        assertEquals(expectedNeighbors, cornerNeighborCheck.getNeighborCells());
    }

}