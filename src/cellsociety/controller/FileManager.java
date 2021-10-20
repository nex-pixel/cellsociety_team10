package cellsociety.controller;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileManager {

    private File currentTextFile;

    public FileManager(){

    }

    public void chooseFile(){
        try {
            loadFile();
        } catch (IOException e) {
            e.printStackTrace();
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

}
