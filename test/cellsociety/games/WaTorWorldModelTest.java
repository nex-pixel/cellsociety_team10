package cellsociety.games;

import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class WaTorWorldModelTest {
    private ResourceBundle myLanguageResources = ResourceBundle.getBundle("cellsociety.resources.languages.English");

    @Test
    void testRandomGridIsMade() {
        WaTorWorldModel randomDefault = new WaTorWorldModel(10,15);
        randomDefault.saveCSVFile("./data/wator_world/randomDefaultTest.csv", myLanguageResources);

        WaTorWorldModel randomChosen = new WaTorWorldModel(15, 10, 2,2, 2);
        randomChosen.saveCSVFile("./data/wator_world/randomChosenTest.csv", myLanguageResources);
    }

    @Test
    void applyRule() {
    }

    @Test
    void testApplyRule() {
    }

    @Test
    void changeCellOnClick() {
    }
}