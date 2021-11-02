package cellsociety.error;


import java.util.ResourceBundle;

import javafx.scene.control.Alert;

public class Error {

    private final String KEY_UNAVAILABLE = "InvalidMessagePassed";
    private final String ERROR_RESOURCE_PATH = "cellsociety.error.resources.languages.";
    private ResourceBundle myResourceBundle;
    private String myMessage;

    public Error(String language) {
        myResourceBundle = ResourceBundle.getBundle(ERROR_RESOURCE_PATH + language);
        setMessageToDefault();
    }

    public void prepareError(String message) {
        myMessage =  (myResourceBundle.containsKey(message)) ? message : KEY_UNAVAILABLE;
    }

    public void showError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(myMessage);
        alert.setContentText(getMyMessage());
        alert.showAndWait();
    }

    public String getMyMessage() {
        return myResourceBundle.getString(myMessage);
    }

    private void setMessageToDefault() {
        myMessage = null;
    }

}
