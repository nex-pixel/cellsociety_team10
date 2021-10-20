package cellsociety.view;

import cellsociety.controller.SimulatorController;
import cellsociety.view.SimulatorView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
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
    private SimulatorController mySimulatorController;

    private Group homePageRoot;


    // Constructor of MainMenuView
    public MainMenuView(String language, String cssFilePath){
        myLanguageResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        this.cssFilePath = cssFilePath;
    }

    /**
     * creates mainMenu and returns the scene
     * @return scene of main menu
     */
    public Scene setMenuDisplay(int width, int height) {
        Label titleLabel = new Label("Cell Society");
        titleLabel.setId("title");
        homePageRoot = new Group();
        homePageRoot.getChildren().add(generateSimulatorSelectorPanel());
        Scene scene = new Scene(homePageRoot, width, height);
        return scene;
    }

    private Node generateSimulatorSelectorPanel(){
        HBox simulatorButtonHBox = new HBox();
        //Button simButton = generateButton("Create new simulation",
        //        event -> mySimulatorController.createNewSimulation());
        return simulatorButtonHBox;
    }

    private void loadFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        File file = fileChooser.showOpenDialog(null);
    }

    private void generateFileSelectDrag(){

        StackPane root = new StackPane();
        String text = "Drag and drop files you would like to run";

        root.setOnDragOver(evt -> evt.acceptTransferModes(TransferMode.LINK));
    }

    private Button generateButton(String label, EventHandler<ActionEvent> event) {
        Button button = new Button();
        button.setText(label);
        button.setOnAction(event);
        return button;
    }


    // applies css file to the scene
    private void applyCSS(Scene scene) throws MalformedURLException {
        File styleFile = new File(cssFilePath);
        scene.getStylesheets().add(styleFile.toURI().toURL().toString());
    }
}
