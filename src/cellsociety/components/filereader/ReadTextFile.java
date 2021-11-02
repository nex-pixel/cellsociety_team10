package cellsociety.components.filereader;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ReadTextFile extends ReadFile {
    private File file;
    private Scanner scanner;

    public ReadTextFile(String filename) {
        super(filename);
    }

    public int[][] read() {
        try {
            file = new File(getFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String nextLine;
            String[] inputtedLine;
            int numOfRows = 0, numOfCols = 0;

            // read the first line
            scanner = new Scanner(file);
            inputtedLine = scanner.nextLine().split(",");
            numOfRows = Integer.valueOf(inputtedLine[0]);
            numOfCols = Integer.valueOf(inputtedLine[1]);
            int[][] array = new int[numOfRows][numOfCols];

            // read one line at a time
            for (int rowIndex = 0; rowIndex < numOfRows; rowIndex++) {
                nextLine = scanner.nextLine();
                inputtedLine = nextLine.split(",");
                for (int colIndex = 0; colIndex < numOfCols; colIndex++) {
                    array[rowIndex][colIndex] = Integer.valueOf(inputtedLine[colIndex]);
                }
            }
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
