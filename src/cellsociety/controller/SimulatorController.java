package cellsociety.controller;

import cellsociety.components.Grid;
import cellsociety.components.ReadCSVFile;
import cellsociety.view.SimulatorView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimulatorController {

    private List<SimulatorView> mySimulations;
    private List<Grid> myGrids;

    private Map<Integer[], Integer> mySampleCellStatus;

    public SimulatorController(List<String> fileNames, int gridWidth, int gridHeight, Color deadColor,
                               Color aliveColor, Color defaultColor){
        mySimulations = new ArrayList<>();
        myGrids = new ArrayList<>();

        for(String name : fileNames){
            ReadCSVFile decodedFile = new ReadCSVFile(name);
            Grid grid = new Grid(decodedFile.read());
            myGrids.add(grid);
            mySimulations.add(new SimulatorView(gridWidth, gridHeight, deadColor, aliveColor, defaultColor));
        }
    }

    public void createNewSimulation(){
    }
}

