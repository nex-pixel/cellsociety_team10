package cellsociety.view.gridBuilder;

import cellsociety.controller.SimulatorController;
import cellsociety.view.cell.HexagonCell;
import javafx.scene.layout.GridPane;
/**
 * HexagonGridBuilder extends GridBuilder. It creates a grid with hexagons.
 *
 * @author Young Jun
 */
public class HexagonGridBuilder extends GridBuilder {
    private static final double HEXAGON_WIDTH = 40.1;
    private static final double HEXAGON_HEIGHT = 34.7;

    @Override
    public void CreateGrid(SimulatorController mySimulatorController, int gridWidth, int gridHeight, GridPane gamePane) {
        int counter = 0;
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                HexagonCell cell = new HexagonCell(mySimulatorController, j, i);
                cell.setId(DEFAULT_CELL_ID);
                gamePane.getChildren().add(cell);
                setCellLocation(cell, j * (HEXAGON_WIDTH * 3 / 4), i * HEXAGON_HEIGHT + (HEXAGON_HEIGHT / 2) * (counter % 2));
                counter += 1;
            }
            counter = 0;
        }
        setPaneSize(gamePane, (gridWidth + 1) * (HEXAGON_WIDTH * 3 / 4), (gridHeight + 1) * HEXAGON_HEIGHT);
    }
}
