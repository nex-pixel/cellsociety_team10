package cellsociety.components;

public class SquareGrid extends Grid {

    public SquareGrid (int[][] states, int neighborMode, int edgePolicy) {
        super(states, neighborMode, edgePolicy);
    }

    @Override
    protected void initializeNeighbors () {
        
    }
}
