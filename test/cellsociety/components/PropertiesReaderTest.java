package cellsociety.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesReaderTest {
    private PropertiesReader myReader;

    @BeforeEach
    void setup(){
        myReader = new PropertiesReader("cellsociety.resources.gameData.NeighborPositionData");
    }

    @Test
    void setNewFilePath() {
        myReader.setNewFilePath("cellsociety.resources.gameData.GameData");
        ResourceBundle expected = ResourceBundle.getBundle("cellsociety.resources.gameData.GameData");

        assertEquals(myReader.getMyFile(), expected);
    }

    @Test
    void getIntProperty() {
        myReader.setNewFilePath("cellsociety.resources.gameData.GameData");
        int actual = myReader.getIntProperty("WatorWorldShark");
        int expected = 2;

        //excessive whitespace
        int acutal_second = myReader.getIntProperty("SegregationEmpty");
        int expected_second = 0;

        assertEquals(expected, actual);
        assertEquals(expected_second, acutal_second);
    }

    @Test
    void getIntListProperty() {
        //normal int array
        int[] actual = myReader.getIntListProperty("TriangleGrid_Complete_Rows_Upward");
        int[] expected = new int[]{-1, -1, -1, 0, 0, 1, 1, 1, 1, 1, 0, 0};

        //weird whitespace formatting
        int[] actual_second = myReader.getIntListProperty("HexagonGrid_Complete_Cols");
        int[] expected_second = new int[]{-1, 1, 2, 1, -1, -2};

        assertTrue(Arrays.equals(expected, actual));
        assertTrue(Arrays.equals(expected_second, actual_second));
    }

    @Test
    void getDoubleProperty() {
        //normal double
        myReader.setNewFilePath("cellsociety.resources.gameData.GameData");
        double actual = myReader.getDoubleProperty("SpreadingFireProbGrow");
        double expected = 0.00;

        //excessive whitespace
        double acutal_second = myReader.getDoubleProperty("SpreadingFireProbSpread");
        double expected_second = 0.20;

        assertEquals(expected, actual);
        assertEquals(expected_second, acutal_second);
    }
}