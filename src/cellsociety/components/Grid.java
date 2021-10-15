package cellsociety.components;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Grid {

    private int myNumRows, myNumCols;
    private Map<Point, Cell> myBoard;

    public Grid (int[][] states) {
        myNumRows = states.length;
        myNumCols = states[0].length;
        myBoard = new HashMap<>();
        for (int rowIndex = 0; rowIndex < myNumRows; rowIndex++) {
            for (int colIndex = 0; colIndex < myNumCols; colIndex++) {
                Point point = new Point(rowIndex, colIndex);
                Cell cell = new Cell(states[rowIndex][colIndex], rowIndex, colIndex);
                myBoard.put(point, cell);
            }
        }
        initializeNeighbors();
    }

    public int getNumRows () { return myNumRows; }
    public int getMyNumCols () { return myNumCols; }

    private void initializeNeighbors() {
        for (Point currentPoint: myBoard.keySet()) {
            Cell currentCell = myBoard.get(currentPoint);
            int[] rows = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
            int[] cols = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
            int numOfNeighbors = 0;
            for (int i = 0; i < rows.length; i++) {
                int x = currentPoint.x + rows[i];
                int y = currentPoint.y + cols[i];
                if (isInsideBoard(x, y)) {
                    Point neighborPosition = new Point(x, y);
                    currentCell.getNeighborCells().add(myBoard.get(neighborPosition));
                    numOfNeighbors++;
                }
                else {
                    currentCell.getNeighborCells().add(null);
                }
            }

            if (numOfNeighbors == 5) {
                currentCell.edge(true);
            }
            else if (numOfNeighbors == 3) {
                currentCell.corner(true);
            }

        }
    }

    private boolean isInsideBoard (int x, int y) {
        return (x >= 0 && x < myNumRows && y >= 0 && y < myNumCols);
    }
}
