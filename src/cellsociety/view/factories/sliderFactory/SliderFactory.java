package cellsociety.view.factories.sliderFactory;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Slider;

/**
 * Class to generate and control slider features
 *
 * @author Ryleigh Byrne, Young Jun
 */
public class SliderFactory {
    private static final int SLIDER_MAX_WIDTH = 100;
    private double sliderValue;

    /**
     * Constructor for slider factor
     * @param value the default value represented on the slider
     */
    public SliderFactory(double value) {
        sliderValue = value;
    }

    /**
     * Generates slider based on given parameters
     * @param minVal minimum value on the slider
     * @param maxVal maximum value on the slider
     * @param listener listener that handles what value the slider is currently at
     * @return slider to be displayed in a scene
     */
    public Slider makeSlider(double minVal, double maxVal, ChangeListener listener) {
        Slider lengthSlider = new Slider(minVal, maxVal, sliderValue);
        setSliderProperties(lengthSlider);
        lengthSlider.valueProperty().addListener(listener);
        return lengthSlider;
    }

    // sets tick and width properties of the slider
    private void setSliderProperties(Slider lengthSlider) {
        lengthSlider.setShowTickMarks(true);
        lengthSlider.setShowTickLabels(true);
        lengthSlider.setMajorTickUnit(1);
        lengthSlider.setMaxWidth(SLIDER_MAX_WIDTH);
    }

}
