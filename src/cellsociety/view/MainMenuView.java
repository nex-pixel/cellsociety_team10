package cellsociety.view;

import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.error.GenerateError;
import cellsociety.view.factories.buttonFactory.MainMenuButtonFactory;
import cellsociety.view.factories.sliderFactory.SliderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class MainMenuView {
    private static ResourceBundle myLanguageResources;
    private FileManager myFileManager;
    private Pane homePageRoot;
    private MainController myMainController;
    private String INVALID_CSS_ERROR = "InvalidCSSFile";
    private String homePageRootID = "home-page-root";
    private MainMenuButtonFactory myMainMenuButtonView;
    private String LANG_KEY;
    private double segregationThreshold;

    public MainMenuView(ResourceBundle languageResourceBundle){
        myLanguageResources = languageResourceBundle;
        myFileManager = new FileManager(myLanguageResources);
    }

    /**
     * creates mainMenu and returns the scene
     * @return scene of main menu
     */
    public Scene setMenuDisplay(MainController mainController, int width, int height) {
        myMainController = mainController;
        myMainMenuButtonView = new MainMenuButtonFactory(this, myMainController, myLanguageResources, myFileManager);
        setLabel("Cell Society", "title");
        initializeHomePageRoot();
        Scene scene = new Scene(homePageRoot, width, height);
        return scene;
    }

    private void initializeHomePageRoot(){
        homePageRoot = new TilePane();
        homePageRoot.getChildren().addAll(myMainMenuButtonView.generateButtonPanel());
        homePageRoot.setId(homePageRootID);
    }

    private void setLabel(String label, String id){
        Label titleLabel = new Label(label);
        titleLabel.setId(id);
    }

    public void applyCSS(Scene scene, String cssFile) {
        try{
            File styleFile = new File(cssFile);
            scene.getStylesheets().add(styleFile.toURI().toURL().toString());
        }catch(Exception e){
            new GenerateError(myLanguageResources.getString(LANG_KEY), INVALID_CSS_ERROR);
        }
    }

    public double getSegregationThreshold(){
        SliderFactory sliderFactory = new SliderFactory(0.5);
        Slider slider = sliderFactory.makeSlider(0.0, 1.0,
                (obs, oldVal, newVal) -> setSegregationThreshold((double)newVal));
        Scene popUp = new Scene(new HBox(new Text(myLanguageResources.getString("Threshold")), slider));
        Stage stage = new Stage();
        stage.setScene(popUp);
        stage.showAndWait();
        return segregationThreshold;
    }

    private void setSegregationThreshold(double value){
        segregationThreshold = value;
    }
}

