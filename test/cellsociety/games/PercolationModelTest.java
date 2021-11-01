package cellsociety.games;

import cellsociety.components.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PercolationModelTest {

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
        gameLongPipe.changeCellOnClick(new Point(2, 1));
        assertTrue(gameLongPipe.getCellStatus(2, 1) == 2);
    }

    @Test
    void testClickCellBlockToOpen () {
        PercolationModel gameLongPipe = new PercolationModel("./data/percolation/volcano.csv");
        gameLongPipe.changeCellOnClick(new Point(0, 2));
        assertTrue(gameLongPipe.getCellStatus(0, 2) == 1);
    }
    

}
