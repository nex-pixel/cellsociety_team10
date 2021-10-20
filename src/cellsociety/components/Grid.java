package cellsociety.components;


import javax.rmi.ssl.SslRMIClientSocketFactory;
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
            System.out.println(myBoard.get(new Point(2,2)));
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
                    Cell c = myBoard.get(neighborPosition);
                    currentCell.getNeighborCells().add(c);
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
        return (x >= 0 && x < myNumCols && y >= 0 && y < myNumRows);
    }

    //TODO: Double Check Code Below
    //expand the board with empty cells to each side depending on what's written
    public void expandGrid(int left, int top, int right, int bottom){
        int[][] newTwoDArray = createArrayForNewGrid(myNumRows + top + bottom, myNumCols + left + right);
        Grid newGrid = new Grid(newTwoDArray);
        newGrid = clearNeighborsForCells(newGrid);
        for(Point point: myBoard.keySet()){
            Cell movedCell = myBoard.get(point);
            movedCell.setXyPosition(point.x + left, point.y + top);
            Point movedPoint = point;
            movedPoint.setLocation(point.x + left, point.y + top);

            newGrid.myBoard.put(movedPoint, movedCell);
        }

        newGrid = padAroundGrid(newGrid,0, top, 0, myNumCols + right + left);
        newGrid = padAroundGrid(newGrid, top, myNumRows + top, 0, left);
        newGrid = padAroundGrid(newGrid, top + myNumRows, myNumRows + top + bottom, 0, myNumCols + right + left);
        newGrid = padAroundGrid(newGrid, top, myNumRows + top, left + myNumCols, myNumCols + left + right);
        this.myBoard = newGrid.myBoard;
        initializeNeighbors();

        this.myNumRows += left + right;
        this.myNumCols += top + bottom;
    }

    private Grid padAroundGrid(Grid passedInGrid, int startingRowVal, int endingRowVal, int startingColVal, int endingColVal) {
        for (int expandedRow = startingRowVal; expandedRow < endingRowVal; expandedRow++) {
            for (int expandedCol = startingColVal; expandedCol < endingColVal; expandedCol++) {
                Point newPoint = new Point(expandedCol, expandedRow);
                Cell newCell = new Cell(0, expandedCol, expandedRow);
                passedInGrid.myBoard.put(newPoint, newCell);
            }
        }
        return passedInGrid;
    }

    private int[][] createArrayForNewGrid(int numRows, int numsCols){
        int[][] retarray = new int[numRows][numsCols];
        for(int rowIndex = 0; rowIndex < numRows; rowIndex++){
            for(int colIndex = 0; colIndex < numsCols; colIndex++){
                retarray[rowIndex][colIndex] = 0;
            }
        }
        return retarray;
    }

    private Grid clearNeighborsForCells(Grid passedInGrid){
        for(Point point: passedInGrid.myBoard.keySet()){
            passedInGrid.myBoard.get(point).clearNeighborCells();
        }
        return passedInGrid;
    }
}
