package cellsociety.games;

import cellsociety.components.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SegregationModelTest {

    @BeforeEach
    void setUp() {}

    @Test
    void testConstructor () {
        SegregationModel game = new SegregationModel(10, 10, 0.2, 0.5, 0.3);
        int[][] grid = game.toGridArray();
        System.out.println(Arrays.deepToString(grid));
    }

    @Test
    void testDefaultConstructor () {
        int[][] states = {{1,0,2}, {0,1,0}, {2,2,1}};
        SegregationModel game = new SegregationModel(states, 0.3);
        int[][] grid = game.toGridArray();
        assertTrue(Arrays.deepEquals(grid, states));
        assertTrue(game.isSatisfied(game.getGrid().get(new Point(0,0))));
    }

    @Test
    void testIsSatisfied () {
        int[][] states = {{1,2,2}, {2,1,0}, {2,2,1}};
        SegregationModel game = new SegregationModel(states, 0.4);
        assertFalse(game.isSatisfied(game.getGrid().get(new Point(0,0))));
    }

    @Test
    void testUpdate () {
        int[][] states = {{1,0,2}, {0,1,0}, {2,2,1}};
        SegregationModel game = new SegregationModel(states, 0.3);
        game.update();
        int[][] grid = game.toGridArray();
        assertTrue(grid[0][2] == 0);
        assertTrue(grid[0][0] == 1 && grid[1][1] == 1 && grid[2][0] == 2 && grid[2][1] == 2 && grid[2][2] == 1);
    }

    @Test
    void testEmptyCells () {
        int[][] states = {{1,0,2}, {0,1,0}, {2,2,1}};
        SegregationModel game = new SegregationModel(states, 0.3);
        game.update();
        List<Cell> emptyCells = game.getEmptyCells();
        assertTrue(emptyCells.contains(game.getGrid().get(new Point(2, 0))));
        System.out.println(emptyCells);
    }


}