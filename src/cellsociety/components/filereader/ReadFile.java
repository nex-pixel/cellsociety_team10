package cellsociety.components.filereader;

/***
 * This class creates an abstract ReadFile object that can be used in its subclasses to create specific file readers such as
 * a sub class to read a JSON or one to read CSV.
 *
 * The class is call by sub grid classes to create 2D array from different file types
 * Dependencies: NONE
 * Assumptions: The files will be written in a specific format and don't need variable readers.
 * Example: Create a ReadFile Object that reads in a specific file format and converts it into a 2D array to be used in grid creation.
 *
 * @author Norah Tan
 */

public abstract class ReadFile {

    private String myFilename;

    public ReadFile (String filename) {
        myFilename = filename;
    }

    public String getFilename () { return myFilename; }

    public abstract int[][] read ();


}
