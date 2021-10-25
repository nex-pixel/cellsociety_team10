package cellsociety.view;

import cellsociety.controller.SimulatorController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.awt.Point;
import cellsociety.components.Cell;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimulatorView {
    private GridPane myGridView;
    private int myGridWidth;
    private int myGridHeight;
    private SimulatorController mySimulatorController;
    private String myCSSFile;
    private Map<String, EventHandler<ActionEvent>> simulatorButtonMap = new LinkedHashMap<>();
    private String simulatorButtonID = "simulator-button";

    public SimulatorView(int gridWidth, int gridHeight, String cssFile){
        myGridView = new GridPane();
        myGridWidth = gridWidth;
        myGridHeight = gridHeight;
        myCSSFile = cssFile;
        populateSimulatorButtonMap();
        setDefaultGrid();
    }

    // fills the grid with squareCells of defaultColor
    private void setDefaultGrid(){
        for(int i = 0; i < myGridWidth; i ++){
            for(int j = 0; j < myGridHeight; j++){
                squareCell cell = new squareCell();
                cell.setWidth(40); // TODO: Needs to change based on the size of the stage
                cell.setHeight(40); // TODO: need refactoring?
                cell.setId("default-cell");
                myGridView.add(cell, i, j);
            }
        }
    }

    /**
     * updates the cells based on the values in Map
     * dead cells are colored with deadColor, alive cells are colored with aliveColor
     * @param cellStatus Map containing the status of cells
     * @return scene with updated cell status
     */
    public void updateSimulation(Map<Point, Cell> cellStatus){
        for(Point coordinate: cellStatus.keySet()){
            int gridNumber = (int) (coordinate.getX() * myGridHeight + coordinate.getY());
            Node currNode = myGridView.getChildren().get(gridNumber);
            myGridView.getChildren().remove(currNode);
            squareCell currCell = (squareCell) currNode;
            if(cellStatus.get(coordinate).getCurrentStatus() == 0){ // TODO: assumed dead is 0
                currCell.setId("dead-cell");
            }else if(cellStatus.get(coordinate).getCurrentStatus() == 1){ //TODO assumed alive is 1
                currCell.setId("alive-cell");
            }else if(cellStatus.get(coordinate).getCurrentStatus() == 2) {
                currCell.setId("default-cell");
            }
            myGridView.getChildren().add(gridNumber, currCell);
        }
    }

    /**
     * This method creates a new grid for the new simulation to be displayed
     * @param gridWidth width of the new gridPane
     * @param gridHeight height of the new gridPane
     */
    public void updateToNewSimulation(int gridWidth, int gridHeight){
        myGridView = new GridPane();
        myGridWidth = gridWidth;
        myGridHeight = gridHeight;
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
        mySimulatorController = simulatorController;

        HBox buttonBox = generateSimulatorButtonBox();
        buttonBox.getChildren().add(makeSlider("Speed", 0.1, 5.0));

        VBox simulationBox = new VBox();
        simulationBox.getChildren().addAll(myGridView, buttonBox);

        applyCSS(simulationBox, myCSSFile);

        return simulationBox;
    }

    private void populateSimulatorButtonMap(){
        simulatorButtonMap.put("Pause", event -> mySimulatorController.pause());
        simulatorButtonMap.put("Play", event -> mySimulatorController.play());
        simulatorButtonMap.put("Step", event -> mySimulatorController.step());
        simulatorButtonMap.put("Save", event -> mySimulatorController.saveCSVFile());
        simulatorButtonMap.put("Load", event -> mySimulatorController.loadNewCSV());
    }

    private HBox generateSimulatorButtonBox(){
        HBox buttonBox = new HBox();
        simulatorButtonMap.forEach((key,value) -> addButtonToPanel(key,value,buttonBox));
        return buttonBox;
    }

    private void addButtonToPanel(String label, EventHandler<ActionEvent> event, HBox panel){
        Button button = generateButton(label,
                event);
        button.setId(simulatorButtonID);
        panel.getChildren().add(button);
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

    private void applyCSS(VBox scene, String cssFile) {
        try{
            File styleFile = new File(cssFile);
            scene.getStylesheets().add(styleFile.toURI().toURL().toString());
        }catch(Exception e){
            //new GenerateError(myLanguageResources, INVALID_CSS_ERROR);
        }
    }
}
