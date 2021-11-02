package cellsociety.view;

import cellsociety.controller.MainController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.io.IOException;
import java.util.ResourceBundle;


class MainMenuViewTest extends DukeApplicationTest {
    private Scene testScene;

    private ResourceBundle langResourceBundle;
    private MainController mainController;
    private MainMenuView testView;
    private Stage myStage;


    @Override
    public void start(Stage stage) throws IOException {
        langResourceBundle = ResourceBundle.getBundle("cellsociety.resources.languages.English");
        testView = new MainMenuView(langResourceBundle);
        myStage = stage;
        mainController = new MainController(stage, langResourceBundle);
        myStage.setScene(testView.setMenuDisplay(new MainController(myStage, langResourceBundle),500, 500 ));
        myStage.show();
    }


    @Test
    void testSetMenuDisplayWidth(){
        Scene testScene = testView.setMenuDisplay(mainController, 500, 600);
        Assertions.assertEquals(500, testScene.getWidth(), "setMenuDisplay creates a scene with a wrong width");
    }

    @Test
    void testSetMenuDisplayHeight(){
        Scene testScene = testView.setMenuDisplay(mainController, 500, 600);
        Assertions.assertEquals(600, testScene.getHeight(), "setMenuDisplay creates a scene with a wrong width");
    }

    @Test
    void testSetMenuDisplay(){
        Button gameTypeButton = lookup("Select a type of simulation to run").query();
        clickOn(gameTypeButton);
        clickOn("OK");
        Button colorButton = lookup("Choose color scheme").query();
        clickOn(colorButton);
        clickOn("OK");
        Button cellTypeButton = lookup("Choose Cell Type").query();
        clickOn(cellTypeButton);
        clickOn("OK");
        Button neighborButton = lookup("Choose Neighbor Mode").query();
        clickOn(neighborButton);
        clickOn("OK");
        Button edgeButton = lookup("Choose Edge Policy").query();
        clickOn(edgeButton);
        clickOn("OK");
        Button newButton = lookup("Create New Simulation").query();
        clickOn(newButton);
    }
}

