package cellsociety;


import cellsociety.controller.MainController;
import cellsociety.view.MainMenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {
    public static final String PROGRAM_TITLE = "Cell Society";

    /**
     * A method to test (and a joke :).
     */
    public double getVersion () {
        return 0.001;
    }

    /**
     * Start of the program.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(PROGRAM_TITLE);
        MainController mainController = new MainController(primaryStage);
        mainController.startMainMenu();
    }
}
