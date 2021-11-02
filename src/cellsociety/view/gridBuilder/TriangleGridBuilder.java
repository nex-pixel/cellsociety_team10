package cellsociety.view.gridBuilder;


import cellsociety.controller.SimulatorController;
import cellsociety.view.cell.TriangleCell;
import javafx.scene.layout.GridPane;

/**
 * TriangleGridBuilder extends GridBuilder. It creates a grid with triangles.
 *
 * @author Young Jun
 */
public class TriangleGridBuilder extends GridBuilder {
    private static final double TRIANGLE_WIDTH = 50.2;
    private static final double TRIANGLE_HEIGHT = 43.4;

    @Override
    public void CreateGrid(SimulatorController mySimulatorController, int gridWidth, int gridHeight, GridPane gamePane) {
        int counter = 0;
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                TriangleCell cell = new TriangleCell(mySimulatorController, counter % 2, j, i);
                cell.setId(DEFAULT_CELL_ID);
                gamePane.getChildren().add(cell);
                setCellLocation(cell, j * (TRIANGLE_WIDTH / 2), i * TRIANGLE_HEIGHT);
                counter += 1;
            }
        }
        setPaneSize(gamePane, (gridWidth + 1) * (TRIANGLE_WIDTH / 2), gridHeight * TRIANGLE_HEIGHT);
    }
}
