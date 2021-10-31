package cellsociety.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class SimulatorControllerTest {
    private SimulatorController testSimulatorController;

    @BeforeEach
    void setUp() {
        ResourceBundle englishResource = ResourceBundle.getBundle("src/cellsociety/resources/English.properties");
        FileManager testFileManager = new FileManager(englishResource);
       // testSimulatorController = new SimulatorController(testFileManager,
        //        "src/cellsociety/resources/style_sheets/Duke.css", englishResource);
    }

    @Test
    void createNewSimulation() {
    }

    @Test
    void generateNewGame() {
    }
}