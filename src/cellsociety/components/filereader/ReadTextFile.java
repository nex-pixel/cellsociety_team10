package cellsociety.components.filereader;

import com.opencsv.CSVReader;

import java.io.File;
import java.util.Scanner;

/***
 * This class will be used to read in a Text file type. The text file will be converted into a 2D array that can be given to grid to decipher into a
 * grid of cells.
 *
 * Dependencies: ReadFile, File, Scanner
 * Assumptions: The files will be written in a specific format and don't need variable readers.
 * Example: Create a FileReader Object that reads in a Text file format and converts it into a 2D array to be used in grid creation.
 *
 * @author Haseeb Chaudhry
 */
public class ReadTextFile extends ReadFile {
    private File file;
    private Scanner scanner;

    /**
     * Default constructor that uses the super class ReadFile as the constructor
     *
     * @param filename String name of the file that needs to be read in
     */
    public ReadTextFile(String filename) {
        super(filename);
    }

    /**
     * This method does the actual file reading in by following a certain file formatting guideline to read in the files.
     * It can throw exceptions if a file is of the wrong type or cannot be read due to the assumption that it is in a certain format is
     * not followed.
     *
     * @return returns a 2D array of values that are read in from the file successfully, return null if not successful
     */
    public int[][] read() {
        try {
            file = new File(getFilename());
        } catch (Exception e) {
            String error = String.format("The file type entered may be incorrect, or File cannot be found.");
            System.out.println(error);
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
            String error = String.format("The file inputted does not follow the proper format. Please format correctly and rerun");
            System.out.println(error);
        }
        return null;
    }
}
