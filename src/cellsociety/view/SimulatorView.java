package cellsociety.view;

import cellsociety.controller.FileManager;
import cellsociety.controller.SimulatorController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.awt.Point;
import cellsociety.components.Cell;
import javafx.scene.text.Text;

import java.util.Map;
import java.util.TreeMap;

public class SimulatorView {
    private GridPane myGridView;
    private Color deadColor;
    private Color aliveColor;
    private Color defaultColor;
    private int gridWidth;
    private int gridHeight;
    private SimulatorController mySimulatorController;
    private FileManager myFileManager;
    private Map<String, javafx.event.EventHandler<ActionEvent>> buttonActionMap;

    public SimulatorView(int gridWidth, int gridHeight, Color deadColor, Color aliveColor, Color defaultColor){
        myGridView = new GridPane();
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.deadColor = deadColor;
        this.aliveColor = aliveColor;
        this.defaultColor = defaultColor;
        setDefaultGrid();
        setButtonActionMap();
    }

    // fills the grid with squareCells of defaultColor
    private void setDefaultGrid(){
        for(int i = 0; i < gridWidth; i ++){
            for(int j = 0; j < gridHeight; j++){
                squareCell cell = new squareCell();
                cell.setWidth(40); // TODO: Needs to change based on the size of the stage
                cell.setHeight(40); // TODO: need refactoring?
                cell.setFill(defaultColor);
                //cell.setId("default-cell");
                // ^ will need to call maincontroller to apply css file or pass css file to here
                myGridView.add(cell, i, j);
            }
        }
    }

    private void setButtonActionMap(){
        buttonActionMap = new TreeMap<>();
        buttonActionMap.put("Pause", event -> mySimulatorController.pause());
        buttonActionMap.put("Play", event -> mySimulatorController.play());
        buttonActionMap.put("Step", event -> mySimulatorController.step());
        buttonActionMap.put("Save", event -> saveFile());
        buttonActionMap.put("Load", event -> loadSimulationFromCSV());
    }

    /**
     * updates the cells based on the values in Map
     * dead cells are colored with deadColor, alive cells are colored with aliveColor
     * @param cellStatus Map containing the status of cells
     * @return scene with updated cell status
     */
    public void updateSimulation(Map<Point, Cell> cellStatus){
        for(Point coordinate: cellStatus.keySet()){
            int gridNumber = (int) (coordinate.getX() * gridHeight + coordinate.getY());
            Node currNode = myGridView.getChildren().get(gridNumber);
            myGridView.getChildren().remove(currNode);
            squareCell currCell = (squareCell) currNode;
            updateCellColor(currCell, cellStatus.get(coordinate).getCurrentStatus());
            myGridView.getChildren().add(gridNumber, currCell);
        }
    }

    private void updateCellColor(squareCell cell, int state){
        switch (state) {
            case 0 -> cell.setFill(deadColor);
            case 1 -> cell.setFill(aliveColor);
            case 2 -> cell.setFill(defaultColor);
        }
    }

    /**
     * This method creates a new grid for the new simulation to be displayed
     * @param gridWidth width of the new gridPane
     * @param gridHeight height of the new gridPane
     */
    public void updateToNewSimulation(int gridWidth, int gridHeight){
        myGridView = new GridPane();
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        setDefaultGrid();
    }

    /**
     * getter for getMyGridView
     * @return getMyGridView
     */
    public GridPane getMyGridView(){
        return myGridView;
    }

    /**
     * Returns a scene of the simulation with the control buttons
     * @param simulatorController a SimulatorController for this simulation
     * @return VBox containing gridpane of the simulation and control buttons
     */
    public VBox returnSimulation(SimulatorController simulatorController){
        HBox buttonBox = new HBox();
        mySimulatorController = simulatorController;
        for(String action : buttonActionMap.keySet()){
            buttonBox.getChildren().add(generateButton(action, buttonActionMap.get(action)));
        }
        buttonBox.getChildren().addAll(new Text("Speed"), makeSlider("Speed", 0.1, 5.0));
        VBox simulationBox = new VBox();
        simulationBox.getChildren().addAll(myGridView, buttonBox);
        return simulationBox;
    }

    private void loadSimulationFromCSV(){
        //TODO users should be able to choose model type
        myFileManager.chooseFile();
        mySimulatorController.loadNewSimulationFromCSV(myFileManager.getCurrentTextFile());
    }



    private void saveFile(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Enter File Name");
        String fileName = dialog.showAndWait().get();
        mySimulatorController.saveCSVFile(fileName);
    }

    private Button generateButton(String label, EventHandler<ActionEvent> event) {
        javafx.scene.control.Button button = new javafx.scene.control.Button();
        button.setText(label);
        button.setOnAction(event);
        return button;
    }

    private Node makeSlider(String text, double minVal, double maxVal) {

        Slider lengthSlider = new Slider(minVal, maxVal, 1);
        lengthSlider.setShowTickMarks(true);
        lengthSlider.setShowTickLabels(true);
        lengthSlider.setMajorTickUnit(1);
        lengthSlider.setMaxWidth(100);
        lengthSlider.valueProperty().addListener((obs, oldval, newVal) ->
                mySimulatorController.setAnimationSpeed(newVal.intValue()));

        return lengthSlider;
    }

}
