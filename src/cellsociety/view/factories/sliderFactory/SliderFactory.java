package cellsociety.view.factories.sliderFactory;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Slider;

public class SliderFactory {
    private static final int SLIDER_MAX_WIDTH = 100;

    private double sliderValue;

    public SliderFactory(double value){
        sliderValue = value;
    }

    public Slider makeSlider(double minVal, double maxVal, ChangeListener listener ) {
        Slider lengthSlider = new Slider(minVal, maxVal, sliderValue);
        setSliderProperties(lengthSlider);
        lengthSlider.valueProperty().addListener(listener);
        return lengthSlider;
    }

    private void setSliderProperties(Slider lengthSlider){
        lengthSlider.setShowTickMarks(true);
        lengthSlider.setShowTickLabels(true);
        lengthSlider.setMajorTickUnit(1);
        lengthSlider.setMaxWidth(SLIDER_MAX_WIDTH);
    }

}
