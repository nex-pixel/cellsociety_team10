package cellsociety.components;

import java.awt.*;

public class SquareGrid extends Grid {

    private int[] myNeighborRows; //To determine neighbor cell locations by using integer displacement in that direction
    private int[] myNeighborCols; //same as above but for columns
    private int NEIGHBOR_MODE_COMPLETE = 0;
    private int NEIGHBOR_MODE_EDGE = 1;


    public SquareGrid (int[][] states, int neighborMode, int edgePolicy) {
        super(states, neighborMode, edgePolicy);
    }

    @Override
    protected void initializeNeighbors () {
        applyNeighborMode();
        for (Point point: getBoard().keySet()) {
            getBoard().get(point).clearNeighborCells();
            Cell currentCell = getBoard().get(point);

            int numOfNeighbors = 0;
            for (int i = 0; i < myNeighborRows.length; i++) {
                int x = point.x + myNeighborCols[i];
                int y = point.y + myNeighborRows[i];

                Point neighborPosition = applyEdgePolicy(x, y);

                if (isInsideBoard(neighborPosition)) {
                    Cell c = getBoard().get(neighborPosition);
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

    private void applyNeighborMode () {
        if (getNeighborMode() == NEIGHBOR_MODE_COMPLETE) {
            myNeighborRows = new int[]{-1, -1, -1, 0, 1, 1, 1, 0};
            myNeighborCols = new int[]{-1, 0, 1, 1, 1, 0, -1, -1};
        }
        else {
            myNeighborRows = new int[]{-1, 0, 1, 0};
            myNeighborCols = new int[]{0, 1, 0, -1};
        }
    }
}
