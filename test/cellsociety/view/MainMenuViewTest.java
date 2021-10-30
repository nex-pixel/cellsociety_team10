package cellsociety.view;

import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.input.KeyCode;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuViewTest extends DukeApplicationTest {
    MainMenuView testView;
    private Labeled testLabel;
    private Labeled testLabel2;
    private Labeled testLabel3;
    private ResourceBundle langResourceBundle = ResourceBundle.getBundle("cellsociety.resources.English");
    private ResourceBundle eventResourceBundle = ResourceBundle.getBundle("cellsociety.resources.ActionEvents");

    @Override
    public void start(Stage stage) throws IOException {
        //testView = new MainMenuView(langResourceBundle, eventResourceBundle);
        stage.show();
        testLabel2 = lookup("Select a type of simulation to run").query();
        testLabel3 = lookup("Load File").query();
    }

    @Test
    void testLanguageButton(){
        Button languageButton = lookup("Select a language").query();
        clickOn(languageButton);
        Button okButton = lookup("OK").query();
        clickOn(okButton);
        //TODO: Our project currently only supports English. Add assertion and test the method once multiple languages are supported
    }

    @Test
    void testSimulationButton(){
        Button simulationButton = lookup("Select a type of simulation to run").query();
        clickOn(simulationButton);
        Button okButton = lookup("OK").query();
        clickOn(okButton);
        //TODO: Our project currently only supports one simulation. Add assertion and test the method once multiple simulations are supported
    }

    @Test
    void testLoadButton(){
        Button loadButton = lookup("Load File").query();
        clickOn(loadButton);
        press(KeyCode.RIGHT);
        for(int i = 0; i < 5; i++){
            press(KeyCode.DOWN);
        }
        press(KeyCode.RIGHT);
        press(KeyCode.DOWN);
        press(KeyCode.RIGHT);
        press(KeyCode.ENTER);
        //TODO: find a way to choose a file
    }

    @Test
    void testRunSimulationButton(){
        Button simulationButton = lookup("Create New Simulation").query();
        clickOn(simulationButton);
    }

}