package cellsociety.error;

import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorTest {
    private final String ERROR_RESOURCE_PATH = "cellsociety.error.resources.languages.";
    private ResourceBundle myResources;

    private Error myError;

    private void setLanguage(String lang){
        myResources = ResourceBundle.getBundle(ERROR_RESOURCE_PATH + lang);
        myError = new Error(lang);
    }

    @Test
    void TestEnglish() {
        setLanguage("English");
        for (String eachKey : myResources.keySet()) {
            myError.prepareError(eachKey);
            assertEquals(myError.getMyMessage(), myResources.getString(eachKey));
        }
    }

    @Test
    void TestSpanish() {
        setLanguage("Spanish");
        for (String eachKey : myResources.keySet()) {
            myError.prepareError(eachKey);
            assertEquals(myError.getMyMessage(), myResources.getString(eachKey));
        }
    }

    @Test
    void TestNonExistentMessageEnglish() {
        setLanguage("English");
        myError.prepareError("SampleNonexistentKey");
        assertEquals(myError.getMyMessage(), myResources.getString("InvalidMessagePassed"));
    }

    @Test
    void TestNonExistentMessageSpanish() {
        setLanguage("Spanish");
        myError.prepareError("SampleNonexistentKey");
        assertEquals(myError.getMyMessage(), myResources.getString("InvalidMessagePassed"));
    }
}
