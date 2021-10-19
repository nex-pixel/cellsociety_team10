package cellsociety.view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.awt.Point;
import cellsociety.components.Cell;


import java.util.Map;

public class SimulatorView {
    private GridPane myGrid;
    private Color deadColor;
    private Color aliveColor;
    private Color defaultColor;
    private int gridWidth;
    private int gridHeight;

    public SimulatorView(int gridWidth, int gridHeight, Color deadColor, Color aliveColor, Color defaultColor){
        myGrid = new GridPane();
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.deadColor = deadColor;
        this.aliveColor = aliveColor;
        this.defaultColor = defaultColor;
        setDefaultGrid();
    }

    // fills the grid with squareCells of defaultColor
    private void setDefaultGrid(){
        for(int i = 0; i < gridWidth; i ++){
            for(int j = 0; j < gridHeight; j++){
                squareCell cell = new squareCell();
                cell.setWidth(10); // TODO: Needs to change based on the size of the stage
                cell.setHeight(10); // TODO: need refactoring?
                cell.setFill(defaultColor);
                myGrid.add(cell, i, j);
            }
        }
    }

    /**
     * updates the cells based on the values in Map
     * dead cells are colored with deadColor, alive cells are colored with aliveColor
     * @param cellStatus Map containing the status of cells
     * @return scene with updated cell status
     */
    public Scene getUpdatedGrid(Map<Point, Cell> cellStatus, int sceneWidth, int sceneHeight){
        for(Point coordinate: cellStatus.keySet()){
            int gridNumber = (int) (coordinate.getX() * gridWidth + coordinate.getY());
            Node currNode = myGrid.getChildren().get(gridNumber);
            myGrid.getChildren().remove(currNode);
            squareCell currCell = (squareCell) currNode;
            if(cellStatus.get(coordinate).getCurrentStatus() == 0){ // TODO: assumed dead is 0
                currCell.setFill(deadColor);
            }else if(cellStatus.get(coordinate).getCurrentStatus() == 1){ //TODO assumed alive is 1
                currCell.setFill(aliveColor);
            }
            myGrid.getChildren().add(gridNumber, currCell);
        }
        return new Scene(myGrid, sceneWidth, sceneHeight);
    }
}
