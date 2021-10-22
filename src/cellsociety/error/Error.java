package cellsociety.error;


import java.util.ResourceBundle;
import javafx.scene.control.Alert;

public class Error{

    private final String KEY_UNAVAILABLE = "InvalidMessagePassed";
    private ResourceBundle myResourceBundle;
    private String myMessage;

    public Error(ResourceBundle resourceBundle) {
        myResourceBundle = resourceBundle;
        setMessageToDefault();
    }

    public void prepareError(String message) {
        myMessage = (myResourceBundle.containsKey(message)) ? message : KEY_UNAVAILABLE;
    }

    public void showError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(myMessage);
        alert.setContentText(getMyMessage());
        alert.showAndWait();
    }

    private String getMyKey() {
        return myMessage;
    }

    private String getMyMessage() {
        return myResourceBundle.getString(this.getMyKey());
    }

    private void setMessageToDefault() {
        myMessage = null;
    }

}
