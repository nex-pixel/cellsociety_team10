package cellsociety.controller;

import javafx.stage.Stage;
import cellsociety.view.SimulatorView;

import java.util.Map;

public class SimulatorController {

    private SimulatorView mySimulatorView;
    private Map<Integer[], Integer> mySampleCellStatus;

    public SimulatorController(SimulatorView simulation, Map<Integer[], Integer> sampleCellStatus){
        // eventually this will be a super class
        mySimulatorView = simulation;
        mySampleCellStatus = sampleCellStatus;

    }

    public void createNewSimulation(Stage stage){
        mySimulatorView.getUpdatedGrid(mySampleCellStatus, 200, 200);
    }
}

