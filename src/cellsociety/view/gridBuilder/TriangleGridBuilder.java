package cellsociety.view.gridBuilder;


import cellsociety.controller.SimulatorController;
import cellsociety.view.cell.TriangleCell;
import javafx.scene.layout.GridPane;

public class TriangleGridBuilder extends GridBuilder {
    @Override
    public void CreateGrid(SimulatorController mySimulatorController, int gridWidth, int gridHeight, GridPane gamePane) {
        int counter = 0;
        for(int i = 0; i < gridHeight; i ++){
            for(int j = 0; j < gridWidth; j++){
                TriangleCell cell = new TriangleCell(mySimulatorController, counter % 2, j, i);
                cell.setId("default-cell");
                gamePane.getChildren().add(cell);
                setCellLocation(cell,j*25,  i*43.30125);
                counter += 1;
            }
        }
        setPaneSize(gamePane, (gridWidth+1)*25, gridHeight*43.30125);
    }
}
