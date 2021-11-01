package cellsociety.view.gridBuilder;

import cellsociety.controller.SimulatorController;
import cellsociety.view.cell.HexagonCell;
import javafx.scene.layout.GridPane;

public class HexagonGridBuilder extends GridBuilder{
    @Override
    public void CreateGrid(SimulatorController mySimulatorController, int gridWidth, int gridHeight, GridPane gamePane) {
        int counter = 0;
        for(int i = 0; i < gridHeight; i ++){
            for(int j = 0; j < gridWidth; j++){
                HexagonCell cell = new HexagonCell(mySimulatorController, j, i);
                cell.setId("default-cell");
                gamePane.getChildren().add(cell);
                setCellLocation(cell, j*30.1, i*34.64 + 17.321*(counter%2));
                counter += 1;
            }
            counter = 0;
        }
        setPaneSize(gamePane,(gridWidth+1)*30,(gridHeight+1)*34.64);
    }
}
