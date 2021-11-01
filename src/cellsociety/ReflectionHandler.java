package cellsociety;

import cellsociety.error.GenerateError;

import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class ReflectionHandler {

    private ResourceBundle myLanguageResources;
    private String LANG_KEY = "language";
    private String INVALID_METHOD = "InvalidMethod";

    public ReflectionHandler(ResourceBundle langResources){
        myLanguageResources = langResources;
    }

    public Method handleMethod(String name, String aClass) {
        try{
            Class<?> thisClass = Class.forName(aClass);
            Method m = thisClass.getDeclaredMethod(name);
            return m;
        }catch(NoSuchMethodException | ClassNotFoundException e){
            new GenerateError(myLanguageResources.getString(LANG_KEY), INVALID_METHOD);
            return null;
        }
    }
}
