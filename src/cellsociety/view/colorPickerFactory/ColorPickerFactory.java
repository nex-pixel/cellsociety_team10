package cellsociety.view.colorPickerFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.text.Text;


public class ColorPickerFactory {

    public ColorPickerFactory(){

    }

    public ColorPicker generateColorPicker(String label, EventHandler<ActionEvent> event){
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setOnAction(event);
        Text colorPickerText = new Text(label);
        colorPickerText.setFill(colorPicker.getValue());
        return colorPicker;
    }
}
