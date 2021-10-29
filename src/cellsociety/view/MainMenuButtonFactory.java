package cellsociety.view;

import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class MainMenuButtonFactory extends ButtonFactory {
    private ArrayList<String> modelOptions = new ArrayList<>();;
    private String[] cssFileLabelOptions = {"DukeLabel", "UNCLabel", "LightLabel", "DarkLabel"};
    private String[] modelLabelOptions = {"GameOfLife", "SpreadingOfFire", "Schelling's", "Wa-TorWorld", "Percolation"};
    private ResourceBundle myActionEventsResources;
    private ArrayList<String> cssFileOptions = new ArrayList<>();
    private ResourceBundle myLanguageResources;
    private FileManager myFileManager;
    private MainMenuView myMainMenuView;
    private MainController myMainMenuController;

    public MainMenuButtonFactory(MainMenuView menuView, MainController mainMenuController, ResourceBundle langResourceBundle, ResourceBundle actionResourceBundle, FileManager fileManager){
        super();
        myMainMenuView = menuView;
        myLanguageResources = langResourceBundle;
        myActionEventsResources = actionResourceBundle;
        myFileManager = fileManager;
        myMainMenuController = mainMenuController;
        buttonID = "main-menu-button";
        populateOptions(modelOptions, modelLabelOptions);
        populateOptions(cssFileOptions, cssFileLabelOptions);
        populateButtonEvents();
    }

    @Override
    protected void populateButtonEvents(){
        try{
            ArrayList<String> list = Collections.list(myActionEventsResources.getKeys());
            Collections.sort(list);
            for(String key : list){
                EventHandler<ActionEvent> buttonEvent = (EventHandler<ActionEvent>) handleMethod(myActionEventsResources.getString(key)).invoke(MainMenuButtonFactory.this);
                buttonMap.put(myLanguageResources.getString(key), buttonEvent);
            }
        }catch(IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
    }

    private EventHandler<ActionEvent> generateModelTypeEvent(){
        return event -> myMainMenuView.generateChoiceDialogBox(myLanguageResources.getString(modelLabelOptions[0]),
                modelOptions, "modelType", myLanguageResources.getString("ModelContent"));
    }

    private EventHandler<ActionEvent> generateChooseFileEvent(){
        return event -> myFileManager.chooseFile();
    }

    private EventHandler<ActionEvent> generateCSSFileEvent(){
        return event-> myMainMenuView.generateChoiceDialogBox(myLanguageResources.getString(cssFileLabelOptions[0]),
                cssFileOptions, "cssFile", myLanguageResources.getString("ThemeContent"));
    }

    private EventHandler<ActionEvent> generateNewSimEvent(){
        return event -> myMainMenuController.generateNewSimulation(myFileManager.getCurrentTextFile());
    }

    private void populateOptions(ArrayList<String> optionsList, String[] labelList){
        for(String key: labelList){
            optionsList.add(myLanguageResources.getString(key));
        }
    }

}
