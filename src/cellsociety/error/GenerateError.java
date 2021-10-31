package cellsociety.error;

import java.util.ResourceBundle;

public class GenerateError {

    public GenerateError(String language, String message){
        Error myError = new Error(language);
        myError.prepareError(message);
        myError.showError();
    }
}
