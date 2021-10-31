package cellsociety.components;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PropertiesReader {
    private ResourceBundle myFile;

    public PropertiesReader(){
        setNewFilePath("cellsociety_team10");
    }

    public PropertiesReader(String filepath){
        setNewFilePath(filepath);
    }

    public ResourceBundle getMyFile() {
        return myFile;
    }

    public void setNewFilePath(String filePath){
        try{
            myFile = ResourceBundle.getBundle(filePath);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getIntProperty(String label) {
        int ret = -1000;
        try{
            ret = Integer.parseInt(myFile.getString(label).replaceAll(" ", ""));
        } catch (Exception labelError){
            labelError.printStackTrace();
        }
        return ret;
    }

    public double getDoubleProperty(String label){
        double ret = -0.0000001;
        try{
            ret = Double.parseDouble(myFile.getString(label).replaceAll(" ", ""));
        } catch (Exception labelError){
            labelError.printStackTrace();
        }
        return ret;
    }

    public int[] getIntListProperty(String label){
        String[] importedList = myFile.getString(label).replaceAll(" ", "").split(",");
        int[] ret = new int[importedList.length];
        try{
            for(int i = 0; i < importedList.length; i++){
                ret[i] = Integer.parseInt(importedList[i]);
            }
        } catch (Exception labelError){
            labelError.printStackTrace();
        }
        return ret;
    }
}
