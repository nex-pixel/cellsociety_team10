package cellsociety.components;


import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class Grid {
    private int myNumRows, myNumCols;
    private Map<Point, Cell> myBoard;
    private int[] myNeighborRows; //To determine neighbor cell locations by using integer displacement in that direction
    private int[] myNeighborCols; //same as above but for columns
    private int myEdgePolicy;
    private int myNeighborMode;
    private int EDGE_POLICY_FINITE = 0;
    private int EDGE_POLICY_TORUS = 1;
    private int EDGE_POLICY_CYLINDER = 2;


    public Grid (int[][] states, int neighborMode, int edgePolicy) {
        myNumRows = states.length;
        myNumCols = states[0].length;
        myNeighborMode = neighborMode;
        myEdgePolicy = edgePolicy;
        myBoard = new HashMap<>();

        initializeBoard(states);
        initializeNeighbors();
    }

    protected void initializeBoard (int[][] states) {
        for (int rowIndex = 0; rowIndex < myNumRows; rowIndex++) {
            for (int colIndex = 0; colIndex < myNumCols; colIndex++) {
                Point point = new Point(colIndex, rowIndex);
                Cell cell = new Cell(states[rowIndex][colIndex], colIndex, rowIndex);
                myBoard.put(point, cell);
            }
        }
    }

    public int getNumRows () { return myNumRows; }
    public int getNumCols () { return myNumCols; }
    public int getEdgePolicy () { return myEdgePolicy; }
    public int getNeighborMode () { return myNeighborMode; }

    public void setNeighborRows (int[] rows) { myNeighborRows = rows; }
    public int[] getNeighborRows () { return myNeighborRows; }
    public void setNeighborCols (int[] cols) { myNeighborCols = cols; }
    public int[] getNeighborCols () { return myNeighborCols; }

    public Set<Point> getPoints (){ return myBoard.keySet(); }
    public Cell getBoardCell (Point point){ return myBoard.get(point); }

    public Cell getXYBoardCell (int x, int y){
        Point point = new Point(x, y);
        return myBoard.get(point);
    }

    protected Map<Point, Cell> getBoard () { return myBoard; }

    protected void initializeNeighbors () {
        for (Point point: getBoard().keySet()) {
            getBoard().get(point).clearNeighborCells();
            Cell cell = getBoard().get(point);

            cell.setEdge(isEdge(cell));
            cell.setCorner(isCorner(cell));

            applyNeighborMode(point);
            iterativelyAddNeighbors(point, cell);
        }
    }

    private void iterativelyAddNeighbors(Point point, Cell cell) {
        for (int i = 0; i < getNeighborRows().length; i++) {
            int x = point.x + getNeighborCols()[i];
            int y = point.y + getNeighborRows()[i];

            Point neighborPosition = applyEdgePolicy(x, y);
            if (isInsideBoard(neighborPosition)) {
                Cell c = getBoard().get(neighborPosition);
                cell.getNeighborCells().add(c);
            } else {
                cell.getNeighborCells().add(null);
            }
        }
    }

    protected abstract void applyNeighborMode (Point point);

    protected Point applyEdgePolicy (int x, int y) {
        if (myEdgePolicy != EDGE_POLICY_FINITE) {
            x = Math.floorMod(x, myNumCols);
        }
        if (myEdgePolicy == EDGE_POLICY_TORUS) {
            y = Math.floorMod(y, myNumRows);
        }
        return new Point(x, y);
    }

    protected boolean isEdge (Cell c) {
        if (isCorner(c)) return false;

        int x = c.getXyPosition()[0];
        int y = c.getXyPosition()[1];

        if (x == 0 || x == myNumCols - 1 || y == 0 || y == myNumCols - 1)  return true;

        return false;
    }

    protected boolean isCorner (Cell c) {
        int x = c.getXyPosition()[0];
        int y = c.getXyPosition()[1];
        if (x == 0 || x == myNumCols - 1) {
            if (y == 0 || y == myNumCols - 1) {
                return true;
            }
        }
        return false;
    }

    protected boolean isInsideBoard (Point point) {
        int x = point.x;
        int y = point.y;
        return (x >= 0 && x < myNumCols && y >= 0 && y < myNumRows);
    }

    //expand the board with empty cells to each side depending on what's written
    public void expandGrid (int left, int top, int right, int bottom){
        //ToDo: Grid is now abstract so "new Grid" doesn't work now
        Grid newGrid = new SquareGrid(new int[myNumRows + top + bottom][myNumRows + left + right], myNeighborMode, myEdgePolicy);
        for(Point point: myBoard.keySet()){
            Cell movedCell = myBoard.get(point);
            movedCell.setXyPosition(point.x + left, point.y + top);
            Point movedPoint = point;
            movedPoint.setLocation(point.x + left, point.y + top);

            newGrid.myBoard.put(movedPoint, movedCell);
        }
        this.myBoard = newGrid.myBoard;
        initializeNeighbors();

        this.myNumRows += left + right;
        this.myNumCols += top + bottom;
    }

//    private Grid clearNeighborsForCells(Grid passedInGrid){
//        for(Point point: passedInGrid.myBoard.keySet()){
//            passedInGrid.myBoard.get(point).clearNeighborCells();
//        }
//        return passedInGrid;
//    }


}
