package cellsociety.view.factories.cssFactory;

import cellsociety.error.GenerateError;
import javafx.scene.Scene;
import java.io.File;
import java.util.ResourceBundle;

/**
 * This class handles CSS File updating for a scene
 *
 * @author Ryleigh Byrne
 */
public class CSSFactory {

    private static final String INVALID_CSS_ERROR = "InvalidCSSFile";
    private ResourceBundle myLanguageResources;

    /**
     * Constructor for CSSFactory. Initializes language resource bundle for error handling
     * @param langResourceBundle current language bundle for scene
     */
    public CSSFactory(ResourceBundle langResourceBundle) {
        myLanguageResources = langResourceBundle;
    }

    /**
     * Applies CSS file to scene and updates theme
     * @param scene to be updated
     * @param cssFile that dictates UI theme
     */
    public void applyCSS(Scene scene, String cssFile) {
        try {
            File styleFile = new File(cssFile);
            scene.getStylesheets().add(styleFile.toURI().toURL().toString());
        } catch (Exception e) {
            new GenerateError(myLanguageResources, INVALID_CSS_ERROR);
        }
    }


}
