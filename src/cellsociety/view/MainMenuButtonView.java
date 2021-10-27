package cellsociety.view;

import cellsociety.controller.FileManager;
import cellsociety.controller.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class MainMenuButtonView extends ButtonView {
    private ArrayList<String> modelOptions = new ArrayList<>();;
    private Map<String, EventHandler<ActionEvent>> mainMenuButtonMap = new LinkedHashMap<>();
    private String[] buttonLabelOptions = {"ChooseSimulationTypeLabel", "LoadFileLabel", "ChooseColorScheme", "CreateNewSimulationLabel"};
    private ArrayList<EventHandler<ActionEvent>> buttonEventLists = new ArrayList<>();
    private String[] cssFileLabelOptions = {"DukeLabel", "UNCLabel", "LightLabel", "DarkLabel"};
    private String[] modelLabelOptions = {"GameOfLife", "SpreadingOfFire", "Schelling's", "Wa-TorWorld", "Percolation"};
    private ResourceBundle myActionEventsResources;
    private int myModelType;
    private ArrayList<String> cssFileOptions = new ArrayList<>();
    private MainController myMainController;
    private ResourceBundle myLanguageResources;
    private FileManager myFileManager;
    private MainMenuView myMainMenuView;

    public MainMenuButtonView(MainMenuView menuView, MainController mainController, ResourceBundle langResourceBundle, ResourceBundle actionResourceBundle, FileManager fileManager){
        myMainMenuView = menuView;
        myMainController = mainController;
        myLanguageResources = langResourceBundle;
        myActionEventsResources = actionResourceBundle;
        myFileManager = fileManager;
        buttonID = "main-menu-button";
        populateButtonEvents();
    }

    @Override
    protected void populateButtonEvents(){
        try{
            for(String key : buttonLabelOptions){
                EventHandler<ActionEvent> buttonEvent = (EventHandler<ActionEvent>) handleMethod(myActionEventsResources.getString(key)).invoke(MainMenuButtonView.this);
                mainMenuButtonMap.put(myLanguageResources.getString(key), buttonEvent);
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
// TODO: NEED TO UPDATE WAY myModelType IS PASSED TO GENERATE NEW SIMULATION --> SHOULDN'T HAVE TO GO THROUGH GENERATE NEW SIM
    private EventHandler<ActionEvent> generateNewSimEvent(){
        return event ->
                myMainController.generateNewSimulation(myModelType, myFileManager.getCurrentTextFile(), myFileManager);
    }

    @Override
    protected Method handleMethod(String name) {
        try{
            Method m = MainMenuButtonView.class.getDeclaredMethod(name);
            return m;
        }catch(NoSuchMethodException e){
            //TODO: FIX THIS
            e.printStackTrace();
        }
        //TODO: BAD
        return null;
    }

    public Node generateMainMenuPanel(){
        VBox panel = new VBox();
        mainMenuButtonMap.forEach((key,value) -> addButtonToPanel(key,value,panel, buttonID));
        return panel;
    }

}
