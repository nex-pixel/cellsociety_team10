package cellsociety.games;

import cellsociety.components.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeModelTest {
    private ResourceBundle myLanguageResources = ResourceBundle.getBundle("cellsociety.resources.languages.English");

    @BeforeEach
    void setUp() {}

    @Test
    void testRandomGridIsMade() {
        GameOfLifeModel randomDefault = new GameOfLifeModel(10,15);
        randomDefault.saveCSVFile("./data/game_of_life/randomDefaultTest.csv", myLanguageResources);

        GameOfLifeModel randomChosen = new GameOfLifeModel(15, 10, 2,2, 2);
        randomChosen.saveCSVFile("./data/game_of_life/randomChosenTest.csv", myLanguageResources);
    }

    @Test
    void testUpdateStillBoat () {
        GameOfLifeModel boat = new GameOfLifeModel("./data/game_of_life/boat.csv");

        GameOfLifeModel board0 = new GameOfLifeModel(boat);
        boat.update();
        GameOfLifeModel board1 = new GameOfLifeModel(boat);

        assertEquals(board0, board1);
    }

    @Test
    void testUpdatePeriodTwoToad () {
        GameOfLifeModel toad = new GameOfLifeModel("./data/game_of_life/toad.csv");
        GameOfLifeModel board0 = new GameOfLifeModel(toad);
        toad.update();
        GameOfLifeModel board1 = new GameOfLifeModel(toad);
        int[][] expectedBoard = {{0,0,0,0,0,0}, {0,0,0,1,0,0}, {0,1,0,0,1,0},{0,1,0,0,1,0}, {0,0,1,0,0,0}, {0,0,0,0,0,0}};
        GameOfLifeModel board2 = new GameOfLifeModel(expectedBoard);
        assertEquals(board2, board1);

        toad.update();
        GameOfLifeModel board3 = new GameOfLifeModel(toad);
        assertEquals(board3, board0);
    }

    @Test
    void testUpdateEdgeCellSame () {
        GameOfLifeModel edgeCellSame = new GameOfLifeModel("./data/game_of_life/alive_edgecell_same.csv");

        GameOfLifeModel board0 = new GameOfLifeModel(edgeCellSame);
        edgeCellSame.update();
        GameOfLifeModel board1 = new GameOfLifeModel(edgeCellSame);

        assertEquals(board0, board1);
    }

    @Test
    void testUpdateEdgeCellDifferent () {
        GameOfLifeModel edgeCellDiff = new GameOfLifeModel("./data/game_of_life/alive_edgecell_different.csv");

        GameOfLifeModel board0 = new GameOfLifeModel(edgeCellDiff);
        edgeCellDiff.update();
        GameOfLifeModel board1 = new GameOfLifeModel(edgeCellDiff);

        assertEquals(board0, board1);
    }

    @Test
    void saveCSV () {
        GameOfLifeModel glider = new GameOfLifeModel("./data/game_of_life/glider.csv");
        glider.saveCSVFile("./data/game_of_life/gliderCopyTest.csv", myLanguageResources);
        GameOfLifeModel gliderCopyTest = new GameOfLifeModel("./data/game_of_life/gliderCopyTest.csv");
        for (Point p: glider.getGrid().getPoints()) {
            assertEquals(glider.getGrid().getBoardCell(p), gliderCopyTest.getGrid().getBoardCell(p));
        }
    }
}
