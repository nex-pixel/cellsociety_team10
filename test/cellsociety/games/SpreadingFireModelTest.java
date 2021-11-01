package cellsociety.games;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class SpreadingFireModelTest {

    private ResourceBundle myLanguageResources = ResourceBundle.getBundle("cellsociety.resources.languages.English");

    @BeforeEach
    void setUp() {
    }

    @Test
    void testRandomGridIsMade() {
        SpreadingFireModel randomDefault = new SpreadingFireModel(10,15);
        randomDefault.saveCSVFile("./data/spreading_fire/randomDefaultTest.csv", myLanguageResources);

        SpreadingFireModel randomChosen = new SpreadingFireModel(15, 10, 2,2, 2);
        randomChosen.saveCSVFile("./data/spreading_fire/randomChosenTest.csv", myLanguageResources);
    }

    @Test
    void testFullForestOneTreeNoGrowth(){
        SpreadingFireModel actual = new SpreadingFireModel("data/spreading_fire/one_burning_tree_full_forest.csv");
        actual.setProbOfFire(1.0);
        actual.setProbGrowNewTrees(0.0);
        actual.update();
        SpreadingFireModel expected = new SpreadingFireModel("data/spreading_fire/expected_oneBurn_fullFor_noGrowth.csv");

        assertEquals(expected, actual);
    }

    @Test
    void testSparseForestOneTreeNoGrowth(){
        SpreadingFireModel actual = new SpreadingFireModel("data/spreading_fire/one_burning_tree_sparse_forest.csv");
        actual.setProbOfFire(1.0);
        actual.setProbGrowNewTrees(0.0);
        actual.update();
        SpreadingFireModel expected = new SpreadingFireModel("data/spreading_fire/expected_oneBurn_sparseFor_noGrowth.csv");

        assertEquals(expected, actual);
    }

    @Test
    void testSparseForestMultiTreeNoGrowth(){
        SpreadingFireModel actual = new SpreadingFireModel("data/spreading_fire/multiple_burning_trees_sparse_forest.csv");
        actual.setProbOfFire(1.0);
        actual.setProbGrowNewTrees(0.0);
        actual.update();
        SpreadingFireModel expected = new SpreadingFireModel("data/spreading_fire/expected_multiBurn_sparseFor_noGrowth.csv");

        assertEquals(expected, actual);
    }

    @Test
    void testSparseForestMultiTreeGrowth(){
        SpreadingFireModel actual = new SpreadingFireModel("data/spreading_fire/multiple_burning_trees_sparse_forest.csv", true);
        actual.setProbOfFire(1.0);
        actual.setProbGrowNewTrees(1.0);
        actual.update();
        SpreadingFireModel expected = new SpreadingFireModel("data/spreading_fire/expected_multiBurn_sparseFor_Growth.csv", true);

        assertEquals(expected, actual);

        actual.update();
        SpreadingFireModel expected2 = new SpreadingFireModel("data/spreading_fire/expected_multiBurn_sparseFor_Growth2.csv", true);

        assertEquals(expected2, actual);
    }

    @Test
    void saveCSV () {
        GameOfLifeModel sparseForest = new GameOfLifeModel("data/spreading_fire/multiple_burning_trees_sparse_forest.csv");
        sparseForest.saveCSVFile("data/spreading_fire/copyof_multiple_burning_trees_sparse_forest.csv", myLanguageResources);
        GameOfLifeModel sparseForestCopy = new GameOfLifeModel("data/spreading_fire/copyof_multiple_burning_trees_sparse_forest.csv");
        for (Point p: sparseForest.getGrid().getPoints()) {
            assertEquals(sparseForest.getGrid().getBoardCell(p), sparseForestCopy.getGrid().getBoardCell(p));
        }
    }
}