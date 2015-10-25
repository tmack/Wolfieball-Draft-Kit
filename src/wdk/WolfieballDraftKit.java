package wdk;

import javafx.application.Application;
import javafx.stage.Stage;
import wdk.gui.WDK_GUI;
import properties_manager.PropertiesManager;
import static wdk.WDK_StartupConstants.*;
import xml_utilities.InvalidXMLFileFormatException;


/**
 * Wolfieball Draft Kit Desktop Application
 * @author Terrell Mack
 */
public class WolfieballDraftKit extends Application {
    
    WDK_GUI gui;
    
    @Override
    public void start(Stage primaryStage) {
        
        loadProperties();
        gui = new WDK_GUI(primaryStage, "Wolfieball Draft Kit");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
      public boolean loadProperties() {
        try {
            // LOAD THE SETTINGS FOR STARTING THE APP
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
            props.loadProperties(PROPERTIES_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME);
            return true;
       } catch (InvalidXMLFileFormatException ixmlffe) {
            // SOMETHING WENT WRONG INITIALIZING THE XML FILE
           // ErrorHandler eH = ErrorHandler.getErrorHandler();
            //eH.handlePropertiesFileError();
            return false;
        }        
    }
    
    
}
