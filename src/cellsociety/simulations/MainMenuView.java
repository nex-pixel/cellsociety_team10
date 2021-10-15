package cellsociety.simulations;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainMenuView {
    private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety/resources/";
    private static ResourceBundle myLanguageResources;
    private Stage window;
    private String cssFilePath;
    private SimulatorView simulation1;
    private Map<Integer[], Integer> sampleCellStatus; // for testing SimulatorView TODO: Delete.

    // Constructor of MainMenuView
    public MainMenuView(String language, String cssFilePath){
        myLanguageResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        this.cssFilePath = cssFilePath;
        simulation1 = new SimulatorView(20, 20, Color.CORAL, Color.BEIGE,Color.BROWN);
        sampleCellStatus = new HashMap<>();// for testing SimulatorView TODO: Delete.
        sampleCellStatus.put(new Integer[] {2,1}, 1 );// for testing SimulatorView TODO: Delete.
        sampleCellStatus.put(new Integer[] {8,14}, 1 );// for testing SimulatorView TODO: Delete.
        sampleCellStatus.put(new Integer[] {16,9}, 0 );// for testing SimulatorView TODO: Delete.
        sampleCellStatus.put(new Integer[] {7,6}, 0 );// for testing SimulatorView TODO: Delete.


    }

    /**
     * creates mainMenu and returns the scene
     * @param stage primary stage
     * @return scene of main menu
     */
    public Scene setMenuDisplay(Stage stage) {
        window = stage;
        Label titleLabel = new Label("Cell Society");
        titleLabel.setId("title");
        HBox homePageRoot = new HBox();
        return simulation1.getUpdatedGrid(sampleCellStatus, 200, 200);// for testing SimulatorView TODO: Delete.

    }

    // applies css file to the scene
    private void applyCSS(Scene scene) throws MalformedURLException {
        File styleFile = new File(cssFilePath);
        scene.getStylesheets().add(styleFile.toURI().toURL().toString());
    }
}
