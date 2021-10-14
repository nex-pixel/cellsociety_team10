package cellsociety.simulations;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ResourceBundle;

public class MainMenuView {
    private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety/resources/";
    private static ResourceBundle myLanguageResources;
    private Stage window;
    private String cssFilePath;

    // Constructor of MainMenuView
    public MainMenuView(String language, String cssFilePath){
        myLanguageResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        this.cssFilePath = cssFilePath;
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
        return null; // TODO: finish setting up menu display
    }

    //applies css file to the scene
    private void applyCSS(Scene scene) throws MalformedURLException {
        File styleFile = new File(cssFilePath);
        scene.getStylesheets().add(styleFile.toURI().toURL().toString());
    }
}
