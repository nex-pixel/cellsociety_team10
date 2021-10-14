package cellsociety.simulations;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.HashMap;
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

    private void setDefaultGrid(){
        for(int i = 0; i < gridWidth; i ++){
            for(int j = 0; j < gridHeight; j++){
                squareCell cell = new squareCell();
                cell.setFill(defaultColor);
                myGrid.add(cell, i, j);
            }
        }
    }

    public GridPane getUpdatedGrid(Map<Integer[], Integer> cellStatus){
        for(Integer[] coordinate: cellStatus.keySet()){
            Node currNode = myGrid.getChildren().get(coordinate[0]*gridWidth + coordinate[1]);
            myGrid.getChildren().remove(currNode);
            squareCell currCell = (squareCell) currNode;
            if(cellStatus.get(coordinate) == 0){
                currCell.setFill(deadColor);
            }else if(cellStatus.get(coordinate )== 1){
                currCell.setFill(aliveColor);
            }
            myGrid.add(currCell, coordinate[0], coordinate[1]);
        }
        return myGrid;
    }
}
