package cellsociety;

import java.lang.reflect.Method;

public class ReflectionHandler {

    public Method handleMethod(String name, String aClass) {
        try {
            Class<?> thisClass = Class.forName(aClass);
            Method m = thisClass.getDeclaredMethod(name);
            return m;
        } catch (NoSuchMethodException e) {
            String error = String.format("The method: %s could not be generated. Double check method you are trying to call's name", name);
            System.out.println(error);
            return null;
        }catch(ClassNotFoundException e){
            String error = String.format("The class: %s could not be generated. Double check class you are trying to call's name", name);
            System.out.println(error);
            return null;
        }
    }
}
