package cellsociety.components;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadCSVFile extends ReadFile {

    public ReadCSVFile (String filename) {
        super(filename);
    }

    public int[][] read () {
        try {
            // parse a CSV file into CSVReader class constructor
            CSVReader reader = new CSVReader(new FileReader(getFilename()));
            String[] nextLine;
            List<List<String>> twoDimList = new ArrayList<>();

            int numOfRows = 0, numOfCols = 0;

            // read the first line
            if ((nextLine = reader.readNext()) != null) {
                numOfRows = Integer.valueOf(nextLine[0]);
                numOfCols = Integer.valueOf(nextLine[1]);
            }

            // read one line at a time
            for (int rowIndex = 0; rowIndex < numOfRows; rowIndex++) {
                twoDimList.add(new ArrayList<>());
                if ((nextLine = reader.readNext()) != null) {
                    for (int colIndex = 0; colIndex < numOfCols; colIndex++) {
                        twoDimList.get(rowIndex).add(nextLine[colIndex]);
                    }
                }
            }
            // convert the 2d ArrayList to 2d array
            int[][] array = twoDimList.stream()
                    .map(u -> u.toArray(new String[0]))
                    .toArray(int[][]::new);
            return array;

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}
