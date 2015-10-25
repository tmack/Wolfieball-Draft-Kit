package wdk.gui;

import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import wdk.WDK_PropertyType;
import static wdk.WDK_StartupConstants.PATH_IMAGES;
import wdk.data.Draft;
import wdk.data.Player;
import wdk.data.Players;
import static wdk.gui.WDK_GUI.CLASS_HEADING_LABEL;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

/**
 * A players screen, used to interact with player information from a draft
 * @author Terrell Mack
 */
public class PlayersPane extends BorderPane {
    
    // Players to displayed in table
    ObservableList<Player> viewablePlayers;
    Draft draft;
    
    // Players Screen Componenets 
    Label title;
    Button addPlayerButton;
    Button removePlayerButton;
    Label searchLabel;
    TextField searchTextField;
    TableView<Player> playersTable;
    ToolBar playersToolBar;
    VBox menuPane;
    
    // Dialog used to confirm actions with user
    YesNoCancelDialog confirmDialog;
    AddPlayerDialog addPlayerDialog;
    EditPlayerDialog editPlayerDialog;
    
    PropertiesManager props;
   
    // All the buttons for selecting a players position
    HBox playerTypeRadioButtons;   
    ToggleGroup playerTypeGroup;
    RadioButton allRadioButton;
    RadioButton cRadioButton;
    RadioButton b1RadioButton;
    RadioButton ciRadioButton;
    RadioButton b2RadioButton;
    RadioButton b3RadioButton;
    RadioButton miRadioButton;
    RadioButton ssRadioButton;
    RadioButton ofRadioButton;
    RadioButton uRadioButton;
    RadioButton pRadioButton;
    
    String currentPosition;
    
    // all the columns for displaying player data
    TableColumn firstNameCol;
    TableColumn lastNameCol;
    TableColumn teamCol;
    TableColumn positionCol;
    TableColumn birthYearCol;
    TableColumn valueCol; // the estimated value of a player
    TableColumn notesCol;
    
    // these are meant to be temporary couldnt figure out how to make an array
    TableColumn stat1Col;
    TableColumn stat2Col;
    TableColumn stat3Col;
    TableColumn stat4Col;
    TableColumn stat5Col;
    
   // permanent columns
    ArrayList<TableColumn> statsCol;
    
   /**
    * Creates a blank player pane
    */
    public PlayersPane() {
        
        // SETS TITLE OF SCREEN
        title = new Label("Available Players");
        title.getStyleClass().add(CLASS_HEADING_LABEL); 
               
        // SETS UP TABLE WHERE PLAYERS WILL BE DISPLAYED 
        playersTable = new TableView<Player>();
        playersTable.setEditable(true); // player notes will be editable from table

        currentPosition = "";
        // initiate list of players to be displayed in table
        viewablePlayers = FXCollections.emptyObservableList();

        // GIVES ACCESS TO ICONS AND TOOLTIPS FOR BUTTONS
        props = PropertiesManager.getPropertiesManager();
     
        // INITIALS ALL THE BUTTONS
        initButtons();
        
        // AND THE SEARCH FIELD TEXTFIELD
        initSearchField();
        
        // ADDS ALL THE BUTTONS TO A TOOLBAR
        playersToolBar = new ToolBar(
                playerTypeRadioButtons,
                addPlayerButton,
                removePlayerButton,
                searchLabel,
                searchTextField
        );
        
        // MENU PANE HAS A TOP SECTION FOR TITLE AND BOTTOM FOR PANE  
        menuPane = new VBox();
        menuPane.getChildren().add(title);
        menuPane.getChildren().add(playersToolBar);
        
       this.setPadding(new Insets(5, 10, 15, 10));
       
        // PUTS IT ALL TOGETHER
        this.setTop(menuPane);
        this.setCenter(playersTable);
    }
    
    /**
    * Creates a player pane with details from a draft
    * @param draft: Draft to populate this pane
    */
    public PlayersPane(Draft draft, Stage primaryStage) {
       this();
       this.draft = draft;
       draft.calcEstimateValues();
       confirmDialog = new YesNoCancelDialog(primaryStage);
       addPlayerDialog = new AddPlayerDialog(primaryStage); 
       editPlayerDialog = new EditPlayerDialog(primaryStage); 
       reloadViewablePlayers();
       initPlayerTable();
       
    }
    
    public void reloadViewablePlayers() {
      viewablePlayers = FXCollections.observableArrayList(draft.getFullRoster());
    }
    
    private void initButtons() {
        
        // SETS UP BUTTON FOR ADDING A NEW PLAYER
        String imagePath = "file:" + PATH_IMAGES + props.getProperty(WDK_PropertyType.ADD_ICON.toString());
        Image buttonImage = new Image(imagePath);
        addPlayerButton = new Button();
        addPlayerButton.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(WDK_PropertyType.ADD_PLAYER_TOOLTIP.toString()));
        addPlayerButton.setTooltip(buttonTooltip);
        
        addPlayerButton.setOnAction(e-> {
     
            
            Player newPlayer = addPlayerDialog.showAddPlayerDialog();
            if(newPlayer != null) {
             
                if(newPlayer.getPositions().contains("P")) {
                    draft.getRoster().get(0).add(newPlayer);
                } else {
                    draft.getRoster().get(1).add(newPlayer);
                }
            
     
             if(newPlayer.getPositions().contains(currentPosition) || currentPosition.contentEquals("All")) {
              //viewablePlayers = FXCollections.observableArrayList(draft.getPlayers(currentPosition));
            //reloadPlayerTable(viewablePlayers);
                 viewablePlayers.add(newPlayer);
                 reloadFromSearchField();
                
             } 
             else if(currentPosition.contentEquals("CI")) {
             
             if(newPlayer.getPositions().contains("1B") || newPlayer.getPositions().contains("3B")) {
                 viewablePlayers.add(newPlayer);
                 reloadFromSearchField();
             }
                 
                 
             }
              else if(currentPosition.contentEquals("MI")) {
             
             if(newPlayer.getPositions().contains("2B") || newPlayer.getPositions().contains("SS")) {
                 viewablePlayers.add(newPlayer);
                 reloadFromSearchField();
             }
                 
                 
             }
                else if(currentPosition.contentEquals("U")) {
             
             if(newPlayer.getPositions().contains("2B") || newPlayer.getPositions().contains("SS")
                    || newPlayer.getPositions().contains("1B") || newPlayer.getPositions().contains("3B")
                    || newPlayer.getPositions().contains("C") || newPlayer.getPositions().contains("OF")) {
                 viewablePlayers.add(newPlayer);
                 reloadFromSearchField();
             }
                 
                 
             }
             

            } 
        });
        
        
        // SETS UP BUTTON FOR REMOVING A PLAYER
        imagePath = "file:" + PATH_IMAGES + props.getProperty(WDK_PropertyType.MINUS_ICON.toString());
        buttonImage = new Image(imagePath);
        removePlayerButton = new Button();
        removePlayerButton.setGraphic(new ImageView(buttonImage));
        buttonTooltip = new Tooltip(props.getProperty(WDK_PropertyType.REMOVE_PLAYER_TOOLTIP.toString()));
        
        removePlayerButton.setTooltip(buttonTooltip);
     
        removePlayerButton.setOnAction(e-> {
            if(!(playersTable.getSelectionModel().isEmpty())) { 
                confirmDialog.show("Remove: "  + 
                playersTable.getSelectionModel().getSelectedItem().getFirstName() + " " 
                + playersTable.getSelectionModel().getSelectedItem().getLastName() + "?");
                playersTable.setItems(viewablePlayers);
                

          
        String selection = confirmDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) { 
          
            
            draft.removePlayer(playersTable.getSelectionModel().getSelectedItem());
            viewablePlayers.remove(playersTable.getSelectionModel().getSelectedItem());
     
        }
       
        } 
        
        
        });
        
        
    
    playerTypeRadioButtons = new HBox();
    playerTypeRadioButtons.setPadding(new Insets(5,5,5,5));
    
    // initiate all the buttons
    allRadioButton = new RadioButton("All");
    cRadioButton = new RadioButton("C");
    b1RadioButton = new RadioButton("1B");
    ciRadioButton = new RadioButton("CI");
    b2RadioButton = new RadioButton("2B");
    b3RadioButton = new RadioButton("3B");
    miRadioButton = new RadioButton("MI");
    ssRadioButton = new RadioButton("SS");
    ofRadioButton = new RadioButton("OF");
    uRadioButton = new RadioButton("U");
    pRadioButton = new RadioButton("P");
    
    // put them in a pane
    playerTypeRadioButtons.getChildren().addAll(allRadioButton
    ,cRadioButton, b1RadioButton,ciRadioButton,b2RadioButton, 
    b3RadioButton,miRadioButton, ssRadioButton, ofRadioButton,
    uRadioButton, pRadioButton);
    
    // group them together
    playerTypeGroup = new ToggleGroup();
    allRadioButton.setToggleGroup(playerTypeGroup);
    cRadioButton.setToggleGroup(playerTypeGroup);
    b1RadioButton.setToggleGroup(playerTypeGroup);
    ciRadioButton.setToggleGroup(playerTypeGroup);
    b2RadioButton.setToggleGroup(playerTypeGroup);
    b3RadioButton.setToggleGroup(playerTypeGroup);
    miRadioButton.setToggleGroup(playerTypeGroup);
    ssRadioButton.setToggleGroup(playerTypeGroup);
    ofRadioButton.setToggleGroup(playerTypeGroup);
    uRadioButton.setToggleGroup(playerTypeGroup);
    pRadioButton.setToggleGroup(playerTypeGroup);
    
    playerTypeGroup.selectToggle(allRadioButton);
   
    allRadioButton.setOnAction(e -> {
        if (allRadioButton.isSelected()) {
            viewablePlayers = FXCollections.observableArrayList(draft.getPlayers("All"));
             reloadPlayerTable(viewablePlayers);
             reloadFromSearchField();
             setStatColumns("All");
             currentPosition = "All";
        }
    });
    
     cRadioButton.setOnAction(e -> {
        if (cRadioButton.isSelected()) {
            viewablePlayers = FXCollections.observableArrayList(draft.getPlayers("C"));
            reloadPlayerTable(viewablePlayers);
            reloadFromSearchField();
            setStatColumns("Hitters");
            currentPosition = "C";
        }
    });
     
       ssRadioButton.setOnAction(e -> {
        if (ssRadioButton.isSelected()) {
            viewablePlayers = FXCollections.observableArrayList(draft.getPlayers("SS"));
            reloadPlayerTable(viewablePlayers);
             reloadFromSearchField();
              setStatColumns("Hitters");
              currentPosition = "SS";
        }
    });
       
         b1RadioButton.setOnAction(e -> {
        if (b1RadioButton.isSelected()) {
            viewablePlayers = FXCollections.observableArrayList(draft.getPlayers("1B"));
            reloadPlayerTable(viewablePlayers);
             reloadFromSearchField();
              setStatColumns("Hitters");
              currentPosition = "1B";
        }
    });
         
         b2RadioButton.setOnAction(e -> {
        if (b2RadioButton.isSelected()) {
            viewablePlayers = FXCollections.observableArrayList(draft.getPlayers("2B"));
            reloadPlayerTable(viewablePlayers);
             reloadFromSearchField();
              setStatColumns("Hitters");
              currentPosition = "2B";
        }
    });
    
         b3RadioButton.setOnAction(e -> {
        if (b3RadioButton.isSelected()) {
            viewablePlayers = FXCollections.observableArrayList(draft.getPlayers("3B"));
            reloadPlayerTable(viewablePlayers);
             reloadFromSearchField();
              setStatColumns("Hitters");
              currentPosition = "3B";
        }
    });
         
         ofRadioButton.setOnAction(e -> {
        if (ofRadioButton.isSelected()) {
            viewablePlayers = FXCollections.observableArrayList(draft.getPlayers("OF"));
            reloadPlayerTable(viewablePlayers);
             reloadFromSearchField();
              setStatColumns("Hitters");
              currentPosition = "OF";
        }
    });
         
     ciRadioButton.setOnAction(e -> {
        if (ciRadioButton.isSelected()) {
            viewablePlayers = FXCollections.observableArrayList(draft.getPlayers("CI"));
            reloadPlayerTable(viewablePlayers);
             reloadFromSearchField();
              setStatColumns("Hitters");
              currentPosition = "CI";
        }
    });
     
     miRadioButton.setOnAction(e -> {
        if (miRadioButton.isSelected()) {
            viewablePlayers = FXCollections.observableArrayList(draft.getPlayers("MI"));
            reloadPlayerTable(viewablePlayers);
             reloadFromSearchField();
              setStatColumns("Hitters");
              currentPosition = "MI";
        }
    });
     
     uRadioButton.setOnAction(e -> {
        if (uRadioButton.isSelected()) {
            viewablePlayers = FXCollections.observableArrayList(draft.getPlayers("U"));
            reloadPlayerTable(viewablePlayers);
             reloadFromSearchField();
              setStatColumns("Hitters");
              currentPosition = "U";
        }
    });
     
     pRadioButton.setOnAction(e -> {
        if (pRadioButton.isSelected()) {
            viewablePlayers = FXCollections.observableArrayList(draft.getPlayers("P"));
            reloadPlayerTable(viewablePlayers);
             reloadFromSearchField();
              setStatColumns("Pitchers");
              currentPosition = "P";
        }
    });
     
     playerTypeRadioButtons.setPadding(new Insets(5,5,0,5));
     playerTypeRadioButtons.setSpacing(15);
     
    }
    
    private void initSearchField() {
      
      searchLabel = new Label("Search: ");
      searchTextField = new TextField();
        
      // THIS LISTENER CHANGES IN THE SEARCH TEXT FIELD
        searchTextField.textProperty().addListener((ObservableValue<? extends String> observable, 
                String oldValue, String newValue) -> {
            reloadFromSearchField();
      });
   
    
   }
    
    private void reloadFromSearchField() {
        
              Players newPlayerList = new Players(); 
            
           // IF THE TEXT FIELD IS EMPTY, TABLE SHOWS ALL PLAYERS
           if(searchTextField.getCharacters().length() == 0) {
                reloadPlayerTable(viewablePlayers);
              
           }
            
           // SEARCHES PLAYER NAMES IN TABLE FOR MATCHES TO SEARCH TEXTFIELD
           else {
               
                  for (Player viewablePlayer : viewablePlayers) {
                      if (viewablePlayer.getLastName().contains(searchTextField.getCharacters()) || viewablePlayer.getFirstName().contains(searchTextField.getCharacters())) {
                          newPlayerList.add(viewablePlayer);
                      }
                      // RELOADS TABLE WITH MATCHES TO TEXTFIELD
                      draft.calcEstimateValues();
                      ObservableList newList = FXCollections.observableArrayList(newPlayerList);
                      reloadPlayerTable(newList);
                  }
        } 
    
    }
    
    private void initPlayerTable() {
    
    // creates each column of players table
        
    firstNameCol = new TableColumn("First Name");
    firstNameCol.setPrefWidth(80);
    firstNameCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("firstName"));
        
    lastNameCol = new TableColumn("Last Name");
     lastNameCol.setPrefWidth(80);
    lastNameCol.setCellValueFactory(
 new PropertyValueFactory<Player, String>("lastName"));
    
    teamCol = new TableColumn("Pro Team");
       teamCol.setPrefWidth(100);
    teamCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("proTeam"));
    
    positionCol = new TableColumn("Positions");
      positionCol.setPrefWidth(90);
    positionCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("positions"));
    
    birthYearCol = new TableColumn("Year of Birth");
        birthYearCol.setPrefWidth(90);
    birthYearCol.setCellValueFactory(
    new PropertyValueFactory<Player, Integer>("birthYear"));
    
    stat1Col = new TableColumn("R/W");
    stat1Col.setPrefWidth(60);
    stat1Col.setCellValueFactory(
    new PropertyValueFactory<Player, Integer>("stat1"));
    
    stat2Col = new TableColumn("HR/SV");
    stat2Col.setPrefWidth(60);
    stat2Col.setCellValueFactory(
    new PropertyValueFactory<Player, Integer>("stat2"));
    
    stat3Col = new TableColumn("RBI/K");
    stat3Col.setPrefWidth(60);
    stat3Col.setCellValueFactory(
    new PropertyValueFactory<Player, Integer>("stat3"));
    
    stat4Col = new TableColumn("SB/ERA");
    stat4Col.setPrefWidth(60);
    stat4Col.setCellValueFactory(
    new PropertyValueFactory<Player, Double>("stat4"));
    
    stat5Col = new TableColumn("BA/WHIP");
    stat5Col.setPrefWidth(60);
    stat5Col.setCellValueFactory(
    new PropertyValueFactory<Player, Double>("stat5"));
    
    valueCol = new TableColumn("Estimated Value");
    valueCol.setPrefWidth(100);
    valueCol.setCellValueFactory(
    new PropertyValueFactory<Player, Double>("value"));
    
    
    
        notesCol = new TableColumn("Notes");
        notesCol.setMinWidth(60);
        notesCol.setCellValueFactory(new PropertyValueFactory<Player, String>("notes"));
        notesCol.setCellFactory(TextFieldTableCell.forTableColumn());
        notesCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Player, String>>() {
                @Override
                public void handle(CellEditEvent<Player, String> newValue) {
                    (newValue.getTableView().getItems().get(newValue.getTablePosition().getRow()))
                            .setNotes(newValue.getNewValue());
                }
            }
        );
         
    statsCol = new ArrayList<TableColumn>();

    // Adds columns to table
    playersTable.setItems(viewablePlayers);
    playersTable.getColumns().addAll(
            firstNameCol, 
            lastNameCol, 
            teamCol, 
            positionCol,
            birthYearCol,
            stat1Col,
            stat2Col,
            stat3Col,
            stat4Col,
            stat5Col,
            valueCol,
            notesCol);
    
       playersTable.setOnMouseClicked(e -> {
           
           
            if (e.getClickCount() == 2) {
                // OPEN EDIT PLAYER DIALOG
                Player playerToEdit = playersTable.getSelectionModel().getSelectedItem();
                boolean textEmpty = true;
        
                
                int playerIndex = playersTable.getSelectionModel().getSelectedIndex();
               
                boolean playerRemoved = editPlayerDialog.showEditPlayerDialog(playerToEdit, draft);
                if(playerRemoved) {
                    
                    viewablePlayers.remove(playerIndex);
                    if(textEmpty == false) {
                    //searchTextField.setText(text);
                   // reloadFromSearchField();
                    }
                
                }
     
            }
        });
    
    }
    
    private void reloadPlayerTable(ObservableList players) {
        
        playersTable.setItems(players);
    }    
    
    private void setStatColumns(String s) {
        
        if(s.contentEquals("Hitters")) {
        stat1Col.setText("R");
         stat2Col.setText("HR");
          stat3Col.setText("RBI");
           stat4Col.setText("SB");
            stat5Col.setText("BA");
            
            
        } else if(s.contentEquals("Pitchers")) {
          stat1Col.setText("W");
         stat2Col.setText("SV");
          stat3Col.setText("K");
           stat4Col.setText("ERA");
            stat5Col.setText("WHIP");
            
        } 
        
        else {
         stat1Col.setText("R/W");
         stat2Col.setText("HR/SV");
         stat3Col.setText("RBI/K");
         stat4Col.setText("SB/ERA");
         stat5Col.setText("BA/WHIP");
            
        }
    }

}