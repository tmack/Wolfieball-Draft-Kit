package wdk.gui;


import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import wdk.WDK_PropertyType;
import static wdk.WDK_StartupConstants.PATH_CSS;
import static wdk.WDK_StartupConstants.PATH_IMAGES;
import wdk.controller.DraftController;
import wdk.data.Draft;
import wdk.data.Player;
import wdk.file.JsonDraftFileManager;

/**
 *
 * @author Terrell Mack
 */
public class WDK_GUI extends BorderPane {

    Stage primaryStage;
    
    JsonDraftFileManager draftFileManger;
    
    Draft draft;
    DraftController draftController;
    ToolBar draftToolbar;
    
    BorderPane screensPane;
    VBox screensToolbar;
    
    FantasyTeamsPane fantasyTeamsScreen;
    PlayersPane playersScreen;
    DraftPane draftScreen;
    FantasyStandingsPane standingsScreen;
    TeamsPane leagueScreen;
    
    Button newDraftButton;
    Button saveDraftButton;
    Button loadDraftButton;
    Button exportDraftButton;
    Button exitButton;
    
    Button playersButton;
    Button teamsButton;
    Button draftButton;
    Button standingsButton;
    Button leagueButton;
    
    static final String PRIMARY_STYLE_SHEET = PATH_CSS + "wdk_style.css";
    static final String CLASS_BORDERED_PANE = "bordered_pane";
    static final String CLASS_SCREENS_LABEL = "screens_label";
    static final String CLASS_HEADING_LABEL = "heading_label";
    static final String CLASS_SUBHEADING_LABEL = "subheading_label";
    static final String CLASS_SCREENS_PANE = "screens_pane";
    static final String CLASS_PROMPT_LABEL = "prompt_label";
    
    public WDK_GUI(Stage primaryStage, String stageTitle) {

        initDraftKit();
        draftFileManger = new JsonDraftFileManager();
        this.primaryStage = primaryStage;
        Scene scene = new Scene(this, 1030, 700);

        scene.getStylesheets().add(PRIMARY_STYLE_SHEET);

        primaryStage.setTitle(stageTitle);
        primaryStage.setScene(scene);
        primaryStage.show();

       }
     
    public void initDraftKit() {
        
        initButtons();
        initEventHandlers();
        initToolBars();
        initWDKPane();
        
    }
     
    public void initWDKPane() {
        
        this.setStyle("-fx-background-color: #C60000");
        this.setTop(draftToolbar);
 
    }
    
    public void initScreensPane() {
        
        initScreens();
        screensPane = new BorderPane();
        screensPane.setLeft(screensToolbar);
        screensPane.setCenter(fantasyTeamsScreen);
        
    }
    
    public void initScreens() {
        
        playersScreen = new PlayersPane(draft, primaryStage); 
        fantasyTeamsScreen = new FantasyTeamsPane(draft, primaryStage);
        draftScreen = new DraftPane(draft);
        standingsScreen = new FantasyStandingsPane(draft);
        leagueScreen = new TeamsPane(draft);
    }
    
    public void initButtons() {
        
        newDraftButton = initChildButton(WDK_PropertyType.NEW_DRAFT_ICON, WDK_PropertyType.NEW_DRAFT_TOOLTIP, false);
        loadDraftButton = initChildButton(WDK_PropertyType.LOAD_DRAFT_ICON, WDK_PropertyType.LOAD_DRAFT_TOOLTIP, false);
        saveDraftButton = initChildButton(WDK_PropertyType.SAVE_DRAFT_ICON, WDK_PropertyType.SAVE_DRAFT_TOOLTIP, false);
        exportDraftButton = initChildButton(WDK_PropertyType.EXPORT_DRAFT_ICON, WDK_PropertyType.EXPORT_DRAFT_TOOLTIP, false);
        exitButton = initChildButton(WDK_PropertyType.EXIT_ICON, WDK_PropertyType.EXIT_TOOLTIP, false);
    
        playersButton = initChildButton(WDK_PropertyType.PLAYER_ICON, WDK_PropertyType.PLAYERS_TOOLTIP, false);
        teamsButton = initChildButton(WDK_PropertyType.HOME_SCREEN_ICON, WDK_PropertyType.FANTASY_TEAMS_TOOLTIP, false);
        draftButton = initChildButton(WDK_PropertyType.SUMMARY_ICON, WDK_PropertyType.DRAFT_TOOLTIP, false);
        standingsButton = initChildButton(WDK_PropertyType.STANDINGS_ICON, WDK_PropertyType.FANTASY_STANDINGS_TOOLTIP, false);
        leagueButton = initChildButton(WDK_PropertyType.MLB_ICON, WDK_PropertyType.MLB_TOOLTIP, false);
        
    }
    
    public void initEventHandlers() {
        
        newDraftButton.setOnAction(e -> {
          
        newDraftButton.setDefaultButton(false);    
        saveDraftButton.setDisable(false);
        loadDraftButton.setDisable(false);
        exportDraftButton.setDisable(false);  
          
          draftController = new DraftController();
          draft = draftController.handleNewDraftRequest();
          

          initScreensPane();
          initScreens();
       
          this.setCenter(screensPane);

        });
        
       saveDraftButton.setOnAction(e -> {
        FileChooser saveFileChooser = new FileChooser();
        saveFileChooser.setTitle("Save Draft:");    

        FileChooser.ExtensionFilter extensions = new FileChooser.ExtensionFilter("JSON", "*.json");
        saveFileChooser.getExtensionFilters().add(extensions);
        File file = saveFileChooser.showSaveDialog(primaryStage);
            
            try {
                draftFileManger.saveDraft(draft, file.getPath());
                } catch (IOException ex) {
                    Logger.getLogger(WDK_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            
       
       });
       
        loadDraftButton.setOnAction(e -> {
            
            FileChooser loadFileChooser = new FileChooser();
            loadFileChooser.setTitle("Load Draft:");    
            File file = loadFileChooser.showOpenDialog(primaryStage);

    
         try {
             
             if(draft == null) {
             newDraftButton.fire();
             }
        
                draftFileManger.loadDraft(draft, file.getPath());
            } catch (IOException ex) {
                Logger.getLogger(WDK_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
         
               fantasyTeamsScreen = new FantasyTeamsPane(draft, primaryStage);
               screensPane.setCenter(fantasyTeamsScreen);
       });
     
        exitButton.setOnAction(e -> {
         System.exit(0);

        });
        
        playersButton.setOnAction(e -> {
           //playersScreen.reloadViewablePlayers();
           playersScreen = new PlayersPane(draft, primaryStage); 
           screensPane.setCenter(playersScreen);
        });
        
        teamsButton.setOnAction(e -> {
            fantasyTeamsScreen = new FantasyTeamsPane(draft, primaryStage);
           screensPane.setCenter(fantasyTeamsScreen);
        });
        
        draftButton.setOnAction(e -> {
            draftScreen.reloadDraftTable();
           screensPane.setCenter(draftScreen);
        });
        
        standingsButton.setOnAction(e -> {
            standingsScreen = new FantasyStandingsPane(draft);
            //standingsScreen.reloadFantasyStandingsTable(); 
            screensPane.setCenter(standingsScreen);
        
        });
        
         leagueButton.setOnAction(e -> {
           screensPane.setCenter(leagueScreen);
        });
         
    }
    
    // INIT A BUTTON AND ADD IT TO A CONTAINER IN A TOOLBAR
    private Button initChildButton(WDK_PropertyType icon, WDK_PropertyType tooltip, boolean disabled) {
       
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = "file:" + PATH_IMAGES + props.getProperty(icon.toString());
        Image buttonImage = new Image(imagePath);
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
        button.setTooltip(buttonTooltip);
        return button;
    }
    
    public void initToolBars() {
        
        draftToolbar = new ToolBar(
        newDraftButton,
        loadDraftButton,
        saveDraftButton,
        exportDraftButton,
        exitButton      
        );
        
        newDraftButton.setDefaultButton(true);
        saveDraftButton.setDisable(true);
        exportDraftButton.setDisable(true);
        
        Label screensLabel = new Label("Screens");
        screensLabel.getStyleClass().add(CLASS_SCREENS_LABEL);
        
        screensToolbar = new VBox( 
        screensLabel,
        teamsButton,
        playersButton,        
        standingsButton,
        draftButton,
        leagueButton
        );
        screensToolbar.setSpacing(7);
        
        screensToolbar.setPadding(new Insets(5, 10, 10, 10));
        screensToolbar.setStyle("-fx-background-color: #800000");
        screensToolbar.getStyleClass().add(CLASS_SCREENS_PANE);
    }
}
