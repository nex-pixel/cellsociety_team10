package cellsociety.error;

import java.util.ResourceBundle;

public class GenerateError {

    public GenerateError(ResourceBundle resourceBundle, String message){
        Error myError = new Error(resourceBundle);
        myError.prepareError(message);
        myError.showError();
    }
}
