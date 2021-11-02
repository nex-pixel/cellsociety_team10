package cellsociety.components.filereader;

import cellsociety.components.filereader.ReadFile;

/***
 * This class will be used to read in a JSON file type, however, the IDE environment requires a separate library addition in order for
 * it to work
 *
 * Dependencies: ReadFile
 * Assumptions: The files will be written in a specific format and don't need variable readers.
 * Example: Create a FileReader Object that reads in a specific file format and converts it into a 2D array to be used in grid creation.
 *
 * @author Norah Tan
 */
public class ReadJSONFile extends ReadFile {

    /**
     * Default constructor that uses the super class ReadFile as the constructor
     *
     * @param filename String name of the file that needs to be read in
     */
    public ReadJSONFile (String filename) {
        super(filename);
    }

    /**
     * This method does the actual file reading in by following a certain file formatting guideline to read in the files.
     * It can throw exceptions if a file is of the wrong type or cannot be read due to the assumption that it is in a certain format is
     * not followed.
     *
     * Return Currently NULL
     * @return returns a 2D array of values that are read in from the file successfully.
     */
    public int[][] read () { return null; }
}
