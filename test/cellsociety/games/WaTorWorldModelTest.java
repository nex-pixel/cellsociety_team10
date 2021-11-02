package cellsociety.games;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.Arrays;
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
    void testFishReproduce() {
        WaTorWorldModel waTorWorldModel = new WaTorWorldModel("./data/wator_world/test/one_fish_one_empty.csv");
        waTorWorldModel.update();
        assertEquals(1, waTorWorldModel.getGrid().getBoardCell(0,0).getCurrentStatus());
        for(int i = 0;i < 14; i++){
            waTorWorldModel.update();
        }

        assertEquals(1, waTorWorldModel.getGrid().getBoardCell(0,0).getCurrentStatus());
        assertEquals(1, waTorWorldModel.getGrid().getBoardCell(1,0).getCurrentStatus());
        assertTrue(Arrays.asList(0,0).equals(waTorWorldModel.getGrid().getBoardCell(0,0).getMiscellaneousVal()));
        assertTrue(Arrays.asList(0,0).equals(waTorWorldModel.getGrid().getBoardCell(1,0).getMiscellaneousVal()));
    }

    @Test
    void testSharkEnergyLoss(){
        WaTorWorldModel waTorWorldModel = new WaTorWorldModel("./data/wator_world/test/one_shark_one_empty.csv");
        waTorWorldModel.update();
        assertEquals(2, waTorWorldModel.getGrid().getBoardCell(0,0).getCurrentStatus());
        for(int i = 0;i < 9; i++){
            waTorWorldModel.update();
        }
        assertEquals(0, waTorWorldModel.getGrid().getBoardCell(0,0).getCurrentStatus());
        assertEquals(0, waTorWorldModel.getGrid().getBoardCell(0,0).getCurrentStatus());
    }

    @Test
    void testSharkReproduce(){
        WaTorWorldModel waTorWorldModel = new WaTorWorldModel("./data/wator_world/test/one_shark_one_empty.csv");
        //check shark moved
        waTorWorldModel.update();
        assertEquals(2, waTorWorldModel.getGrid().getBoardCell(0,0).getCurrentStatus());

        //starve shark
        for(int i = 0;i < 8; i++){
            waTorWorldModel.update();
        }

        //feed shark two fish
        waTorWorldModel.getGrid().getBoardCell(1,0).setCurrentStatus(1);
        waTorWorldModel.update();
        waTorWorldModel.getGrid().getBoardCell(0,0).setCurrentStatus(1);

        //move until reproduce
        for (int i = 0; i < 5; i++){
            waTorWorldModel.update();
        }

        //check if two sharks exist with energy values
        assertEquals(2, waTorWorldModel.getGrid().getBoardCell(0,0).getCurrentStatus());
        assertEquals(2, waTorWorldModel.getGrid().getBoardCell(1,0).getCurrentStatus());

        //show shark steps until reproduce and energy don't change if they stand still
        for(int i = 0; i < 7; i++){
            waTorWorldModel.update();
        }
        assertTrue(Arrays.asList(0,3).equals(waTorWorldModel.getGrid().getBoardCell(0,0).getMiscellaneousVal()));
        assertTrue(Arrays.asList(0,10).equals(waTorWorldModel.getGrid().getBoardCell(1,0).getMiscellaneousVal()));
    }

    @Test
    void testIfSharkEatsFishOverEmptySpace(){
        WaTorWorldModel waTorWorldModel = new WaTorWorldModel("./data/wator_world/test/one_shark_one_fish_one_empty.csv");
        //check shark moved to eat fish and then check the energy value it has as well as number steps since alive
        waTorWorldModel.update();
        assertEquals(2, waTorWorldModel.getGrid().getBoardCell(0,0).getCurrentStatus());
        assertEquals(0, waTorWorldModel.getGrid().getBoardCell(1,0).getCurrentStatus());
        assertEquals(0, waTorWorldModel.getGrid().getBoardCell(2,0).getCurrentStatus());
        assertTrue(Arrays.asList(1,13).equals(waTorWorldModel.getGrid().getBoardCell(0,0).getMiscellaneousVal()));

        //check shark moved to empty and then check the shark's values
        waTorWorldModel.update();
        assertEquals(0, waTorWorldModel.getGrid().getBoardCell(0,0).getCurrentStatus());
        assertEquals(2, waTorWorldModel.getGrid().getBoardCell(1,0).getCurrentStatus());
        assertEquals(0, waTorWorldModel.getGrid().getBoardCell(2,0).getCurrentStatus());
        assertTrue(Arrays.asList(2,12).equals(waTorWorldModel.getGrid().getBoardCell(1,0).getMiscellaneousVal()));
    }

    @Test
    void changeCellOnClick() {
        WaTorWorldModel waTorWorldModel = new WaTorWorldModel("./data/wator_world/test/oneempty.csv");
        assertEquals(0, waTorWorldModel.getGrid().getBoardCell(0,0).getCurrentStatus());
        waTorWorldModel.changeCellOnClick(0,0);
        assertEquals(1, waTorWorldModel.getGrid().getBoardCell(0,0).getCurrentStatus());
        waTorWorldModel.changeCellOnClick(0,0);
        assertEquals(2, waTorWorldModel.getGrid().getBoardCell(0,0).getCurrentStatus());
        waTorWorldModel.changeCellOnClick(0,0);
        assertEquals(0, waTorWorldModel.getGrid().getBoardCell(0,0).getCurrentStatus());
    }
}