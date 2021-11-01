package cellsociety.error;

import java.util.ResourceBundle;

public class GenerateError {

    private String LANG_KEY = "language";

    public GenerateError(ResourceBundle langResources, String message) {
        String language = langResources.getString(LANG_KEY);
        Error myError = new Error(language);
        myError.prepareError(message);
        myError.showError();
    }
}
