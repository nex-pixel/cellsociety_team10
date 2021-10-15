package cellsociety.simulations;

import cellsociety.components.Cell;

import java.awt.*;
import java.util.Map;

public class GameOfLife extends Game {

    public GameOfLife (String filename) {
        super(filename);
    }

    public void update () {
        Map<Point, Cell> board = getGrid().getBoard();
        for (Point point: board.keySet()) {
            board.get(point).observe();
        }
        for (Point point: board.keySet()) {
            board.get(point).changeStatus();
        }
    }
}
