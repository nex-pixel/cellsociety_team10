package cellsociety.components.filereader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ReadTextFileTest {
    private ReadTextFile myReader;

    @BeforeEach
    public void setUp () {
        myReader = new ReadTextFile("./data/game_of_life/blinkers.txt");
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