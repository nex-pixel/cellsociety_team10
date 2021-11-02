package cellsociety.components.filereader;

import cellsociety.components.filereader.ReadCSVFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ReadCSVFileTest {

    private ReadCSVFile myReader;

    @BeforeEach
    public void setUp () {
        myReader = new ReadCSVFile("./data/game_of_life/blinkers.csv");
    }

    @Test
    void readFile () {
        int[][] array = myReader.read();
        int[][] expected = {{0,0,0,0,0,0,0,1,1,1},
                {0,0,0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0,0,0},
                {1,0,0,0,1,0,0,0,0,0},
                {1,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,1,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,1},
                {0,1,1,1,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,1,1,1,0,0,0}};
        assertEquals(10, array.length);
        assertEquals(10, array[0].length);
        assertTrue(Arrays.deepEquals(expected, array));
    }

}
