package cellsociety.controller;

import cellsociety.error.GenerateError;
import cellsociety.games.Game;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class FileManager {
    private static final String INVALID_FILE = "InvalidFileError";
    private static final String INVALID_SAVE = "InvalidSaveFile";
    private static final String SAVE_CSV_LABEL = "SaveCSVLabel";
    // Things to remember
    private File currentTextFile;
    private ResourceBundle myLanguageResources;


    public FileManager(ResourceBundle resourceBundle) {
        myLanguageResources = resourceBundle;
    }

    public void chooseFile() {
        try {
            loadFile();
        } catch (IOException e) {
            new GenerateError(myLanguageResources, INVALID_FILE);
        }
    }

    public void loadFile() throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            currentTextFile = file;
        }
    }

    public void saveCSVFile(Game myGame) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText(myLanguageResources.getString(SAVE_CSV_LABEL));
        try {
            String fileName = dialog.showAndWait().get();
            myGame.saveCSVFile(fileName, myLanguageResources);
        } catch (Exception e) {
            new GenerateError(myLanguageResources, INVALID_SAVE);
        }
    }

    public void checkFileValidity(File file) {
        if (file == null) {
            new GenerateError(myLanguageResources, INVALID_FILE);
        }
    }

    public File getCurrentTextFile() {
        return currentTextFile;
    }
}
