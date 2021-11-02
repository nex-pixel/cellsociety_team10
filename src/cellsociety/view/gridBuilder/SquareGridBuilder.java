package cellsociety.view.gridBuilder;

import cellsociety.controller.SimulatorController;
import cellsociety.view.cell.SquareCell;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

/**
 * SquareGridBuilder extends GridBuilder. It creates a grid with squares.
 *
 * @author Young Jun
 */
public class SquareGridBuilder extends GridBuilder {
    private static final double SQUARE_WIDTH = 40.0;
    private static final double PADDING = 0.1;


    @Override
    public void CreateGrid(SimulatorController mySimulatorController, int gridWidth, int gridHeight, GridPane gamePane) {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                SquareCell cell = new SquareCell(mySimulatorController, i, j);
                cell.setId(DEFAULT_CELL_ID);
                gamePane.getChildren().add(cell);
                setCellLocation(cell, i * SQUARE_WIDTH + PADDING, j * SQUARE_WIDTH + PADDING);
            }
        }
        setPaneSize(gamePane, gridWidth * SQUARE_WIDTH, gridHeight * SQUARE_WIDTH);
    }
}
