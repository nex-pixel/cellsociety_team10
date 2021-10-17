package cellsociety.simulations;

import cellsociety.components.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeModelTest {
    Game game;

    @BeforeEach
    void setUp() {
        game = new GameOfLifeModel("blinkers.csv");
    }

    @Test
    void update() {
        Game secondgame = new GameOfLifeModel("blinkersexpected.csv");
        game.update();
        Map<Point, Cell> board1 = game.myGrid.getBoard();
        Map<Point, Cell> board2 = secondgame.myGrid.getBoard();
        for(Point point: board2.keySet()){
            assertEquals(board2.get(point), board1.get(point));
        }
    }

    @Test
    void applyRule() {

    }
}