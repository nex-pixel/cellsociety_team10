package cellsociety.components;


import java.awt.*;
import java.util.*;

public abstract class Grid {
    private int myNumRows, myNumCols;
    private int myMaxRow, myMaxCol;
    private Map<Point, Cell> myBoard;
    private int[] myNeighborRows; //To determine neighbor cell locations by using integer displacement in that direction
    private int[] myNeighborCols; //same as above but for columns
    private int myEdgePolicy;
    private int myNeighborMode;
    private int EDGE_POLICY_FINITE = 0;
    private int EDGE_POLICY_TORUS = 1;
    private int EDGE_POLICY_CYLINDER = 2;
    protected PropertiesReader myReader;
    private final String neighborDataFile = "cellsociety.resources.gameData.NeighborPositionData";


    public Grid(int[][] states, int neighborMode, int edgePolicy) {
        populateNeighborData();
        myNumRows = states.length;
        myNumCols = states[0].length;
        myNeighborMode = neighborMode;
        myEdgePolicy = edgePolicy;
        myBoard = new HashMap<>();

        myMaxRow = myNumRows;
        assignMaxCol();

        initializeBoard(states);
        initializeNeighbors();
        populateNeighborData();
    }

    protected void initializeBoard(int[][] states) {
        for (int rowIndex = 0; rowIndex < myNumRows; rowIndex++) {
            for (int colIndex = 0; colIndex < myNumCols; colIndex++) {
                Point point = new Point(colIndex, rowIndex);
                Cell cell = new Cell(states[rowIndex][colIndex], colIndex, rowIndex);
                myBoard.put(point, cell);
            }
        }
    }

    public void changeEdgePolicy(int newEdgePolicy){
        myEdgePolicy = newEdgePolicy;
        initializeNeighbors();
    }

    public void changeNeighborMode(int newNeighborMode){
        myNeighborMode = newNeighborMode;
        initializeNeighbors();
    }

    public int getNumRows() {
        return myNumRows;
    }

    public int getNumCols() {
        return myNumCols;
    }

    public void setMyNumRows(int newNumRows) {
        myNumRows = newNumRows;
    }

    public void setMyNumCols(int newNumCols) {
        myNumCols = newNumCols;
    }

    protected void setMaxCol (int maxCol) { myMaxCol = maxCol; }
    protected void assignMaxCol () { setMaxCol(myNumCols); }

    public int getEdgePolicy() {
        return myEdgePolicy;
    }

    public int getNeighborMode() {
        return myNeighborMode;
    }

    public void setNeighborRows(int[] rows) {
        myNeighborRows = rows;
    }

    public int[] getNeighborRows() {
        return myNeighborRows;
    }

    public void setNeighborCols(int[] cols) {
        myNeighborCols = cols;
    }

    public int[] getNeighborCols() {
        return myNeighborCols;
    }

    public Set<Point> getPoints() {
        return myBoard.keySet();
    }

    public Cell getBoardCell(int x, int y) {
        return myBoard.get(new Point(x, y));
    }

    public Cell getBoardCell(Point point) {
        return myBoard.get(point);
    }

    public int getCellStatus(int x, int y){
        return myBoard.get(getPoint(x, y)).getCurrentStatus();
    }

    protected Point getPoint(int x, int y){
        return new Point(x, y);
    }

    public Cell getXYBoardCell(int x, int y) {
        Point point = new Point(x, y);
        return myBoard.get(point);
    }

    protected Map<Point, Cell> getBoard() {
        return myBoard;
    }

    protected void setBoard(Map<Point, Cell> passedInBoard) {
        this.myBoard = passedInBoard;
    }

    protected void initializeNeighbors() {
        for (Point point : myBoard.keySet()) {
            myBoard.get(point).clearNeighborCells();
            Cell cell = myBoard.get(point);
            applyNeighborMode(point);
            iterativelyAddNeighbors(point, cell);
        }
    }

    // Will also preserve the order by using LinkedHashSet
    private void removeDuplicatesInNeighborCells (Cell cell) {
        cell.setNeighborCells(new ArrayList<>(
                new LinkedHashSet<>(cell.getNeighborCells())));
    }

    private void iterativelyAddNeighbors(Point point, Cell cell) {
        for (int i = 0; i < myNeighborRows.length; i++) {
            int x = point.x + myNeighborCols[i];
            int y = point.y + myNeighborRows[i];

            Point neighborPosition = applyEdgePolicy(x, y);
            if (myBoard.containsKey(neighborPosition) && isInsideBoard(neighborPosition.x, neighborPosition.y)) {
                Cell c = myBoard.get(neighborPosition);
                cell.getNeighborCells().add(c);
            }
//            else {
//                cell.getNeighborCells().add(null);
//            }
        }
        removeDuplicatesInNeighborCells(cell);
    }

    protected abstract void applyNeighborMode(Point point);

    protected Point applyEdgePolicy(int x, int y) {
        if (myEdgePolicy != EDGE_POLICY_FINITE) {
            x = Math.floorMod(x, myMaxCol);
        }
        if (myEdgePolicy == EDGE_POLICY_TORUS) {
            y = Math.floorMod(y, myMaxRow);
        }
        return new Point(x, y);
    }

    protected boolean isInsideBoard(int x, int y) {
        return (x >= 0 && x < myNumCols && y >= 0 && y < myNumRows);
    }

    //expand the board with empty cells to each side depending on what's written
    public abstract void expandGrid(int left, int top, int right, int bottom);

//    private Grid clearNeighborsForCells(Grid passedInGrid){
//        for(Point point: passedInGrid.myBoard.keySet()){
//            passedInGrid.myBoard.get(point).clearNeighborCells();
//        }
//        return passedInGrid;
//    }

    protected void initializeNewGridasOriginal(int multiplicationFactor, int left, int top, int right, int bottom, int myNumRows, int myNumCols, Set<Point> points, Grid newGrid) {
        for (Point point : points) {
            Cell movedCell = getBoardCell(point);
            movedCell.setXyPosition(point.x + (left * multiplicationFactor), point.y + top);
            Point movedPoint = point;
            movedPoint.setLocation(point.x + (left * multiplicationFactor), point.y + top);

            newGrid.getBoard().put(movedPoint,movedCell);
        }
        setBoard(newGrid.getBoard());
        initializeNeighbors();

        setMyNumRows(myNumRows + top + bottom);
        setMyNumCols(myNumCols + right + left);
    }

    protected void populateNeighborData() {
        myReader = new PropertiesReader(neighborDataFile);
        EDGE_POLICY_FINITE = myReader.getIntProperty("EDGE_POLICY_FINITE");
        EDGE_POLICY_TORUS = myReader.getIntProperty("EDGE_POLICY_TORUS");
        EDGE_POLICY_CYLINDER = myReader.getIntProperty("EDGE_POLICY_CYLINDER");
    }

    protected void setRowColValues(String row, String col) {
        setNeighborRows(myReader.getIntListProperty(row));
        setNeighborCols(myReader.getIntListProperty(col));
    }


}
