package cellsociety.view;

import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import cellsociety.error.GenerateError;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ResourceBundle;

public class NewSimulatorButtonFactory extends MainMenuButtonFactory{
    private SimulatorView mySimulatorView;
    private MainController myMainMenuController;


    public NewSimulatorButtonFactory(ResourceBundle langResourceBundle) {
        super(langResourceBundle);
        myMainMenuController = new MainController(langResourceBundle);

    }

    @Override
    protected EventHandler<ActionEvent> generateNewSimEvent(){
        return event -> {
            try {
            } catch (Exception e) {
            }
        };
    }
}


