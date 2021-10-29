package cellsociety.components;

import java.awt.*;

public class TriangleGrid extends Grid {

    private int NEIGHBOR_MODE_COMPLETE = 0;
    private int NEIGHBOR_MODE_EDGE = 1;

    public TriangleGrid (int[][] states, int neighborMode, int edgePolicy) {
        super(states, neighborMode, edgePolicy);
    }

    @Override
    protected void initializeNeighbors () {
        for (Point point: getBoard().keySet()) {
            getBoard().get(point).clearNeighborCells();
            Cell cell = getBoard().get(point);

            cell.setEdge(isEdge(cell));
            cell.setCorner(isCorner(cell));

            applyNeighborMode(point);
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

    protected void applyNeighborMode (Point point) {
        if (getNeighborMode() == NEIGHBOR_MODE_COMPLETE) {
            if ((point.x + point.y) % 2 == 0) { // upward triangle
                setNeighborRows(new int[]{-1, -1, -1, 0, 0, 1, 1, 1, 1, 1, 0, 0});
                setNeighborCols(new int[]{-1, 0, 1, 1, 2, 2, 1, 0, -1, -2, -2, -1});
            }
            else {
                setNeighborRows(new int[]{-1, -1, -1, -1, -1, 0, 0, 1, 1, 1, 0, 0});
                setNeighborCols(new int[]{-2, -1, 0, 1, 2, 1, 2, 1, 0, -1, -2, -1});
            }
        }
        else if (getNeighborMode() == NEIGHBOR_MODE_EDGE) {
            if ((point.x + point.y) % 2 == 0) { // upward triangle
                setNeighborRows(new int[]{0, 1, 0});
                setNeighborCols(new int[]{1, 0, -1});
            }
            else {
                setNeighborRows(new int[]{-1, 0, 0});
                setNeighborCols(new int[]{0, 1, -1});
            }

        }

    }
}
