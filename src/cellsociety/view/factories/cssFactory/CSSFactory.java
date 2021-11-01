package cellsociety.view.factories.cssFactory;

import cellsociety.error.GenerateError;
import javafx.scene.Scene;

import java.io.File;
import java.util.ResourceBundle;

public class CSSFactory {
    private static final String INVALID_CSS_ERROR = "InvalidCSSFile";

    private ResourceBundle myLanguageResources;
    public CSSFactory(ResourceBundle langResourceBundle){
        myLanguageResources = langResourceBundle;
    }

    public void applyCSS(Scene scene, String cssFile) {
        try{
            File styleFile = new File(cssFile);
            scene.getStylesheets().add(styleFile.toURI().toURL().toString());
        }catch(Exception e){
            new GenerateError(myLanguageResources, INVALID_CSS_ERROR);
        }
    }


}
