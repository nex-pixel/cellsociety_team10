package cellsociety.simulations;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ResourceBundle;

public class mainMenuView {
    private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety/resources/";
    private static final String CSS_FILE_PATH = "cellsociety/resources/GameStyleSheet.css";
    private static ResourceBundle myLanguageResources;
    private Stage window;


    public mainMenuView(String language){
        myLanguageResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);

    }

    public Scene setMenuDisplay(Stage stage) {
        this.window = stage;
        Label titleLabel = new Label("Cell Society");
        titleLabel.setId("title");
        HBox homePageRoot = new HBox();

    }

    private void applyCSS(Scene scene) throws MalformedURLException {
        File styleFile = new File(CSS_FILE_PATH);
        scene.getStylesheets().add(styleFile.toURI().toURL().toString());
    }
}
