package cellsociety;


import cellsociety.view.MainMenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    public static final String PRIMARY_LANGUAGE = "English";
    public static final String PROGRAM_TITLE = "Cell Society";
    private static final String CSS_FILE_PATH = "cellsociety/resources/GameStyleSheet.css";
    private static final int WIDTH = 800; // TODO: make not a magic value
    private static final int HEIGHT = 600; // TODO: make not a magic value

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
        MainMenuView mainMenu = new MainMenuView(PRIMARY_LANGUAGE, CSS_FILE_PATH);
        Scene mainScene = mainMenu.setMenuDisplay(primaryStage, WIDTH, HEIGHT);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle(PROGRAM_TITLE);
        primaryStage.show();
    }
}
