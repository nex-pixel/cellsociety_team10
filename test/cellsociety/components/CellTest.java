package cellsociety.components;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {
    private int[] testCellVal = {1, 3, 6};
    private Cell testCell;
    private List<Cell> testNeighbors = Arrays.asList(new Cell(1, 1,0),
            new Cell(0, 2, 0), null, null, null,
            new Cell(1, 2,2), new Cell(1, 1,2),
            new Cell(0, 1,1));

    @BeforeEach
    void setUp() {
        testCell = new Cell(testCellVal[0], testCellVal[1], testCellVal[2]);
        testCell.setNextStatus(0);
        testCell.setNeighborCells(testNeighbors);
    }


    @Test
    void changeStatus() {
        testCell.setNextStatus(0);
        testCell.changeStatus();
        assertEquals(0, testCell.getCurrentStatus());
    }

    @Test
    void clearNeighborCells() {
        testCell.setNeighborCells(testNeighbors);
        testCell.clearNeighborCells();
        int expectedNumNeighbors = 0;
        assertEquals(expectedNumNeighbors, testCell.getNeighborCells().size());
    }

    @Test
    void setNeighborCells() {
        assertTrue(testNeighbors.equals(testCell.getNeighborCells()));
    }

    @Test
    void getXyPosition() {
        int[] testCellPos = testCell.getXyPosition();
        assertEquals(testCellVal[1], testCellPos[0]);
        assertEquals(testCellVal[2], testCellPos[1]);
    }

    @Test
    void getCurrentStatus() {
        assertEquals(testCellVal[0], testCell.getCurrentStatus());
    }

    @Test
    void testEquals() {
        Cell newTestCell = new Cell(1, 3, 6);
        //should be same cell as above
        assertTrue(newTestCell.equals(testCell));
    }
}
