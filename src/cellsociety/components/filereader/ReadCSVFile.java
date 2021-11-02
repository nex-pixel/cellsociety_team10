package cellsociety.components.filereader;

import cellsociety.components.filereader.ReadFile;
import cellsociety.error.GenerateError;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

/***
 * This class will be used to read in a CSV file type. The CSV file will be converted into a 2D array that can be given to grid to decipher into a
 * grid of cells.
 *
 * Dependencies: ReadFile, CSVReader, FileReader
 * Assumptions: The files will be written in a specific format and don't need variable readers.
 * Example: Create a FileReader Object that reads in a CSV file format and converts it into a 2D array to be used in grid creation.
 *
 * @author Norah Tan
 */
public class ReadCSVFile extends ReadFile {
    private CSVReader reader;

    /**
     * Default constructor that uses the super class ReadFile as the constructor
     *
     * @param filename String name of the file that needs to be read in
     */
    public ReadCSVFile (String filename) {
        super(filename);
    }

    /**
     * This method does the actual file reading in by following a certain file formatting guideline to read in the files.
     * It can throw exceptions if a file is of the wrong type or cannot be read due to the assumption that it is in a certain format is
     * not followed.
     *
     * @return returns a 2D array of values that are read in from the file successfully. returns null if not successful
     */
    public int[][] read () {
        try{
            reader = new CSVReader(new FileReader(getFilename()));
        } catch (Exception e){
            String error = String.format("The file type entered may be incorrect, or File cannot be found.");
            System.out.println(error);
        }

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
            String error = String.format("The file inputted does not follow the proper format. Please format correctly and rerun");
            System.out.println(error);
        }
        return null;
    }
}

