package cellsociety.view.gridBuilder;

import cellsociety.controller.SimulatorController;
import cellsociety.view.cell.SquareCell;
import javafx.scene.layout.GridPane;

public class SquareGridBuilder extends GridBuilder{

    @Override
    public void CreateGrid(SimulatorController mySimulatorController, int gridWidth, int gridHeight, GridPane gamePane) {
        for(int i = 0; i < gridWidth; i ++){
            for(int j = 0; j < gridHeight; j++){
                SquareCell cell = new SquareCell(mySimulatorController, i, j);
                cell.setId("default-cell");
                gamePane.add(cell, i, j);
            }
        }
    }
}
