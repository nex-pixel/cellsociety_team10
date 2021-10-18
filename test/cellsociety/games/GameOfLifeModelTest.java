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
    void applyRule() {

    }
}