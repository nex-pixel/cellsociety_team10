package cellsociety.games;

import cellsociety.components.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class PercolationModelTest {
    private ResourceBundle myLanguageResources = ResourceBundle.getBundle("cellsociety.resources.languages.English");


    @Test
    void testRandomGridIsMade() {
        PercolationModel randomDefault = new PercolationModel(10,15);
        randomDefault.saveCSVFile("./data/percolation/randomDefaultTest.csv", myLanguageResources);

        PercolationModel randomChosen = new PercolationModel(15, 10, 2,2, 2);
        randomChosen.saveCSVFile("./data/percolation/randomChosenTest.csv", myLanguageResources);
    }

    @Test
    void testOpenCells () {
        PercolationModel game = new PercolationModel("./data/percolation/long_pipe.csv");
        List<Cell> expectedOpenCells = Arrays.asList(new Cell(1, 7, 0),
                new Cell(1, 1, 0));
        assertTrue(expectedOpenCells.equals(game.getOpenCells()));
    }

    @Test
    void testUpdateLongPipe () {
        PercolationModel gameLongPipe = new PercolationModel("./data/percolation/long_pipe.csv");
        gameLongPipe.update();
        assertTrue(gameLongPipe.getOpenCells().size() == 0);
    }

    @Test
    void testClickCellBlockToPercolated () {
        PercolationModel gameLongPipe = new PercolationModel("./data/percolation/long_pipe.csv");
        gameLongPipe.changeCellOnClick(2, 1);
        assertTrue(gameLongPipe.getCellStatus(2, 1) == 2);
    }

    @Test
    void testClickCellBlockToOpen () {
        PercolationModel gameLongPipe = new PercolationModel("./data/percolation/volcano.csv");
        gameLongPipe.changeCellOnClick(0, 2);
        assertTrue(gameLongPipe.getCellStatus(0, 2) == 1);
    }


}
