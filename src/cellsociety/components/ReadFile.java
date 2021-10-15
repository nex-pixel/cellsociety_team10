package cellsociety.components;

public abstract class ReadFile {

    private String myFilename;

    public ReadFile (String filename) {
        myFilename = filename;
    }

    public String getFilename () { return myFilename; }

    public abstract int[][] read ();

}
