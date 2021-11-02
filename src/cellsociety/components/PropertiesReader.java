package cellsociety.components;

import java.util.ResourceBundle;

/**
 * The purpose of this class is to create an object that will read in the magic values for the class that needs them. It essentially handles
 * all the properties files that need to be read for values by applying the appropriate method for the specific label passed in.
 * Assumptions: The assumption is that the user will want integers that are of certain readable length and format.
 * Dependencies: Resourcebundle
 * Example: Reading in the magic values for applyNeighborMode in HexagonalGrid
 *
 * @author Haseeb Chaudhry
 */
public class PropertiesReader {
    private ResourceBundle myFile;

    /**
     * Create the default constructor with access to full project if no pathway is initialized.
     */
    public PropertiesReader() {
        setNewFilePath("cellsociety_team10");
    }

    /**
     * Create a constructor for the reader to a specific file location to read the data in from
     *
     * @param filepath string filepath to specific File
     */
    public PropertiesReader(String filepath) {
        setNewFilePath(filepath);
    }

    /**
     * This method returns the specific ResourceBundle created to be used to read in the data from a specific file
     *
     * @return return ResourceBundle made of specific file chosen
     */
    public ResourceBundle getMyFile() {
        return myFile;
    }

    /**
     * This method sets a new filepath to a new file that needs to be read in, and make use of the same constructor
     *
     * @param filePath string filepath to specific File
     */
    public void setNewFilePath(String filePath) {
        try {
            myFile = ResourceBundle.getBundle(filePath);
        } catch (Exception e) {
            String error = String.format("The filepath entered does not lead to the a file, filetype, or is not specific");
            System.out.println(error);
        }
    }

    /**
     * This method reads in an int for the specific label in the file.
     *
     * @param label String name of property needed
     * @return int value stored in property in specific file
     */
    public int getIntProperty(String label) {
        int ret = -1000;
        try {
            ret = Integer.parseInt(myFile.getString(label).replaceAll(" ", ""));
        } catch (Exception labelError) {
            String error = String.format("The format of the property is incorrect");
            System.out.println(error);
        }
        return ret;
    }

    /**
     * This method reads in a double for the specific label in the file.
     *
     * @param label String name of property needed
     * @return double value stored in property in specific file
     */
    public double getDoubleProperty(String label) {
        double ret = -0.0000001;
        try {
            ret = Double.parseDouble(myFile.getString(label).replaceAll(" ", ""));
        } catch (Exception labelError) {
            String error = String.format("The format of the property is incorrect");
            System.out.println(error);
        }
        return ret;
    }

    /**
     * This method reads in a list of integer values stored in a property file
     * Assumption: The list is written in a specific format
     *
     * @param label String name of property needed
     * @return in[] of the array of numbers that are read in from the property chosen in specific file
     */
    public int[] getIntListProperty(String label) {
        String[] importedList = myFile.getString(label).replaceAll(" ", "").split(",");
        int[] ret = new int[importedList.length];
        try {
            for (int i = 0; i < importedList.length; i++) {
                ret[i] = Integer.parseInt(importedList[i]);
            }
        } catch (Exception labelError) {
            String error = String.format("The format of the property is incorrect");
            System.out.println(error);
        }
        return ret;
    }
}
