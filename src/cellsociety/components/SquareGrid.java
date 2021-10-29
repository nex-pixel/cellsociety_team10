package cellsociety.components;

import java.awt.*;

public class SquareGrid extends Grid {


    private int NEIGHBOR_MODE_COMPLETE = 0;
    private int NEIGHBOR_MODE_EDGE = 1;


    public SquareGrid (int[][] states, int neighborMode, int edgePolicy) {
        super(states, neighborMode, edgePolicy);
    }

    @Override
    protected void initializeNeighbors () {
        for (Point point: getBoard().keySet()) {
            getBoard().get(point).clearNeighborCells();
            Cell cell = getBoard().get(point);

            cell.setEdge(isEdge(cell));
            cell.setCorner(isCorner(cell));

            applyNeighborMode();
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
    }

    private void applyNeighborMode () {
        if (getNeighborMode() == NEIGHBOR_MODE_COMPLETE) {
            setNeighborRows(new int[]{-1, -1, -1, 0, 1, 1, 1, 0});
            setNeighborCols(new int[]{-1, 0, 1, 1, 1, 0, -1, -1});
        }
        else if (getNeighborMode() == NEIGHBOR_MODE_EDGE) {
            setNeighborRows(new int[]{-1, 0, 1, 0});
            setNeighborCols(new int[]{0, 1, 0, -1});
        }
    }

}
