package cellsociety.components;

import java.awt.*; // TODO remove *
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
                Point point = new Point(colIndex, rowIndex);
                Cell cell = new Cell(states[rowIndex][colIndex], colIndex, rowIndex);
                myBoard.put(point, cell);
            }
        }
        initializeNeighbors();
    }
    
    public int getNumRows () { return myNumRows; }
    public int getNumCols () { return myNumCols; }

    public Map<Point, Cell> getBoard () { return myBoard; }

    private void initializeNeighbors() {
        for (Point currentPoint: myBoard.keySet()) {
            Cell currentCell = myBoard.get(currentPoint);
            //removed 0 in cols and rows because a cell can't be a neighbor of itself
            int[] rows = {-1, -1, -1, 0, 1, 1, 1, 0}; //To determine neighbor cell locations by using integer displacement in that direction
            int[] cols = {-1, 0, 1, 1, 1, 0, -1, -1}; //same as above but for columns
            int numOfNeighbors = 0;
            for (int i = 0; i < rows.length; i++) {
                int x = currentPoint.x + cols[i];
                int y = currentPoint.y + rows[i];
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
                currentCell.setEdge(true);
            }
            else if (numOfNeighbors == 3) {
                currentCell.setCorner(true);
            }

        }
    }

    private boolean isInsideBoard (int x, int y) {
        return (x >= 0 && x < myNumRows && y >= 0 && y < myNumCols);
    }

    //TODO: EDIT THE WAY COLUMN AND ROW ARE ADDED LOOK AT INITIAL GRID CODE
    //expand the board with empty cells to each side depending on what's written
    public void expandGrid(int left, int top, int right, int bottom){
        for(Point point: myBoard.keySet()){
            point.setLocation(point.getX() + left, point.getY() + top);
        }

        clearNeighborsForCells();
        padAroundGrid(0, top, 0, myNumCols + right + left);
        padAroundGrid(top, myNumRows + top, 0, left);
        padAroundGrid(top + myNumRows, myNumRows + top + bottom, 0, myNumCols + right + left);
        padAroundGrid(top, myNumRows + top, left + myNumCols, myNumCols + left + right);
        initializeNeighbors();

        myNumRows += left + right;
        myNumCols += top + bottom;
    }

    private void padAroundGrid(int startingRowVal, int endingRowVal, int startingColVal, int endingColVal) {
        for (int expandedRow = startingRowVal; expandedRow < endingRowVal; expandedRow++) {
            for (int expandedCol = startingColVal; expandedCol < endingColVal; expandedCol++) {
                Point newPoint = new Point(expandedRow, expandedCol);
                Cell newCell = new Cell(0, expandedRow, expandedCol);
                myBoard.put(newPoint, newCell);
            }
        }
    }

    private void clearNeighborsForCells(){
        for(Point point: myBoard.keySet()){
            myBoard.get(point).clearNeighborCells();
        }
    }

}
