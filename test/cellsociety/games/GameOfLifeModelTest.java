package cellsociety.games;

import cellsociety.components.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeModelTest {

    @BeforeEach
    void setUp() {}

    @Test
    void testUpdateStillBoat () {
        Game boat = new GameOfLifeModel("./data/game_of_life/boat.csv");

        int[][] board0 = boat.toGridArray();
        boat.update();
        int[][] board1 = boat.toGridArray();

        assertTrue(Arrays.deepEquals(board0, board1));

    }

    @Test
    void testUpdatePeriodTwoToad () {
        Game toad = new GameOfLifeModel("./data/game_of_life/toad.csv");
        int[][] board0 = toad.toGridArray();
        toad.update();
        int[][] board1 = toad.toGridArray();
        int[][] board2 = {{0,0,0,0,0,0}, {0,0,0,1,0,0}, {0,1,0,0,1,0},{0,1,0,0,1,0}, {0,0,1,0,0,0}, {0,0,0,0,0,0}};
        assertTrue(Arrays.deepEquals(board2, board1));

        toad.update();
        int[][] board3 = toad.toGridArray();
        assertTrue(Arrays.deepEquals(board3, board0));
    }

    @Test
    void testUpdateEdgeCellSame () {
        Game edgeCellSame = new GameOfLifeModel("./data/game_of_life/alive_edgecell_same.csv");

        int[][] board0 = edgeCellSame.toGridArray();
        edgeCellSame.update();
        int[][] board1 = edgeCellSame.toGridArray();

        assertTrue(Arrays.deepEquals(board0, board1));
    }

    @Test
    void testUpdateEdgeCellDifferent () {
        Game edgeCellDiff = new GameOfLifeModel("./data/game_of_life/alive_edgecell_different.csv");

        int[][] board0 = edgeCellDiff.toGridArray();
        edgeCellDiff.update();
        int[][] board1 = edgeCellDiff.toGridArray();

        assertFalse(Arrays.deepEquals(board0, board1));
    }

    @Test
    void saveCSV () {
        Game glider = new GameOfLifeModel("./data/game_of_life/glider.csv");
        glider.saveCSVFile("./data/game_of_life/gliderCopyTest.csv");
        Game gliderCopyTest = new GameOfLifeModel("./data/game_of_life/gliderCopyTest.csv");
        for (Point p: glider.getGrid().keySet()) {
            assertEquals(glider.getGrid().get(p), gliderCopyTest.getGrid().get(p));
        }
    }
}