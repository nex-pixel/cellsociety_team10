package cellsociety.games;

import cellsociety.components.Cell;

public class WaTorWorldModel extends Game{
    //neighbors only north south east and west count
    private final int EMPTY = 0;
    private final int FISH = 1;
    private final int SHARK = 2;

    public WaTorWorldModel(String filename){
        super(filename);
    }

    @Override
    public void update() {

    }

    @Override
    protected boolean applyRule(Cell cell) {
        return false;
    }
}
