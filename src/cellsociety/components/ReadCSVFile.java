package cellsociety.components;

import com.opencsv.CSVReader;

import java.io.FileReader;


public class ReadCSVFile extends ReadFile {

    public ReadCSVFile (String filename) {
        super(filename);
    }

    public int[][] read () {
        try {
            CSVReader reader = new CSVReader(new FileReader(getFilename()));
            String[] nextLine;
            int numOfRows = 0, numOfCols = 0;

            // read the first line
            nextLine = reader.readNext();
            numOfRows = Integer.valueOf(nextLine[1]);
            numOfCols = Integer.valueOf(nextLine[0]);
            int[][] array = new int[numOfRows][numOfCols];

            // read one line at a time
            for (int rowIndex = 0; rowIndex < numOfRows; rowIndex++) {
                nextLine = reader.readNext();
                for (int colIndex = 0; colIndex < numOfCols; colIndex++) {
                    array[rowIndex][colIndex] = Integer.valueOf(nextLine[colIndex]);
                }
            }
            return array;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

