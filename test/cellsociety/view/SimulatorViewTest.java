package cellsociety.view;

import cellsociety.games.Game;
import cellsociety.games.GameOfLifeModel;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulatorViewTest {
    private SimulatorView testSimulator;

    @Test
    void testUpdateSimulation() {
        GameOfLifeModel testGame = new GameOfLifeModel("data/game_of_life/blinkers.csv");
        testSimulator = new SimulatorView(testGame.getMyGrid().getNumRows(),testGame.getMyGrid().getNumCols(), Color.CORAL, Color.BEIGE, Color.BROWN);
        testSimulator.updateSimulation(testGame.getGrid());
        GridPane simulatorGrid = testSimulator.getMyGridView();
        assertEquals(Color.CORAL, ((Rectangle) simulatorGrid.getChildren().get(8)).getFill(),
                "ERROR: Method not updating alive cells properly");
        assertEquals(Color.BEIGE, ((Rectangle) simulatorGrid.getChildren().get(4)).getFill(),
                "ERROR: Method not updating dead cells properly");
    }

    @Test
    void testUpdateSimulationMultipleTimes() {
        GameOfLifeModel testGame = new GameOfLifeModel("data/game_of_life/blinkers.csv");
        GameOfLifeModel testGame2 = new GameOfLifeModel("data/game_of_life/test_update.csv");

        testSimulator = new SimulatorView(testGame.getMyGrid().getNumRows(),testGame.getMyGrid().getNumCols(), Color.CORAL, Color.BEIGE, Color.BROWN);
        testSimulator.updateSimulation(testGame.getGrid());
        testSimulator.updateSimulation(testGame2.getGrid());
        GridPane simulatorGrid = testSimulator.getMyGridView();
        assertEquals(Color.BEIGE, ((Rectangle) simulatorGrid.getChildren().get(60)).getFill(),
                "ERROR: Method not updating dead cells properly");
    }

}