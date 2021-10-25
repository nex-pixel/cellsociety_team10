package cellsociety.controller;

import cellsociety.error.GenerateError;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

public class FileManager {

    private File currentTextFile;
    private ResourceBundle myLanguageResources;
    private String INVALID_FILE = "InvalidFileError";

    public FileManager(ResourceBundle resourceBundle){
        myLanguageResources = resourceBundle;
    }

    public void chooseFile(){
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

    public void checkFileValidity(File file){
        if(file == null){
            new GenerateError(myLanguageResources, INVALID_FILE);
        }
    }

    public File getCurrentTextFile() {
        return currentTextFile;
    }
}
