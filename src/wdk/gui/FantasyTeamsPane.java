/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.gui;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import wdk.WDK_PropertyType;
import static wdk.WDK_StartupConstants.PATH_IMAGES;
import wdk.data.Draft;
import wdk.data.Player;
import wdk.data.Team;
import static wdk.gui.WDK_GUI.CLASS_HEADING_LABEL;

/**
 *
 * @author Terrell Mack
 */
public class FantasyTeamsPane extends BorderPane {
    
   Draft draft;
   
   
   FantasyTeamDialog teamDialog;
   Stage primaryStage;

   // Players to displayed in tables
   ObservableList<Player> viewableStartingPlayers;
   ObservableList<Player> viewableReservePlayers;
    
   Label title;
   Pane headingPane;
   
   PropertiesManager props;
   
   Button addTeamButton;
   Button removeTeamButton;
   Button editTeamButton; 
   Label selectTeamLabel;
   ComboBox teamsComboBox;
   ToolBar teamsToolbar;
   
   Label startingPlayersLabel;
   PlayersTable startingPlayersTable;
   VBox startingPlayersPane;
   
   Label reservePlayersLabel;
   PlayersTable reservePlayersTable;
   VBox reservePlayersPane;
   
   VBox playersTablePane;
   EditPlayerDialog editPlayerDialog;
   
   public FantasyTeamsPane() {
     
        // CREATES THE HEADING OF THE MAIN SCREEN
        title = new Label("Fantasy Team");
        title.getStyleClass().add(CLASS_HEADING_LABEL);
        headingPane = new Pane();
        headingPane.getChildren().add(title);
        this.setTop(headingPane);
  
   
        // GIVES ACCESS TO ICONS AND TOOLTIPS FOR BUTTONS
        props = PropertiesManager.getPropertiesManager();
     
        // SETS UP BUTTON FOR ADDING A TEAM
        String imagePath = "file:" + PATH_IMAGES + props.getProperty(WDK_PropertyType.ADD_ICON.toString());
        Image buttonImage = new Image(imagePath);
        addTeamButton = new Button();
        addTeamButton.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(WDK_PropertyType.ADD_TEAM_TOOLTIP.toString()));
        addTeamButton.setTooltip(buttonTooltip);
        
        // SETS UP BUTTON FOR REMOVING A TEAM
        imagePath = "file:" + PATH_IMAGES + props.getProperty(WDK_PropertyType.MINUS_ICON.toString());
        buttonImage = new Image(imagePath);
        removeTeamButton = new Button();
        removeTeamButton.setGraphic(new ImageView(buttonImage));
        buttonTooltip = new Tooltip(props.getProperty(WDK_PropertyType.REMOVE_TEAM_TOOLTIP.toString()));
        removeTeamButton.setTooltip(buttonTooltip);
        
        // SETS UP BUTTON FOR EDITING A TEAM
        imagePath = "file:" + PATH_IMAGES + props.getProperty(WDK_PropertyType.EDIT_ICON.toString());
        buttonImage = new Image(imagePath);
        editTeamButton = new Button();
        editTeamButton.setGraphic(new ImageView(buttonImage));
        buttonTooltip = new Tooltip(props.getProperty(WDK_PropertyType.EDIT_TEAM_TOOLTIP.toString()));
        editTeamButton.setTooltip(buttonTooltip);
        
        // SETS UP THE COMBOBOX FOR SELECTING A TEAM
        selectTeamLabel = new Label("Fantasy Teams: ");
        teamsComboBox = new ComboBox();
        teamsComboBox.setPrefWidth(150);

         // SETS UP STARTING PLAYERS TABLE
        startingPlayersLabel = new Label("Starting Lineup");
        startingPlayersTable = new PlayersTable("FantasyTeamTable");
        startingPlayersTable.setEditable(false);
        
        // SETS UP RESERVE PLAYERS TABLE
        reservePlayersLabel = new Label("Taxi Squad");
        reservePlayersTable = new PlayersTable("ReserveTable");
        reservePlayersTable.setEditable(false);
        
        // SETS UP THE PANES USED TO ORGANIZE ALL THE COMPONENTS
        teamsToolbar = new ToolBar();
        startingPlayersPane = new VBox();
        reservePlayersPane = new VBox();
        playersTablePane = new VBox();
        
        // PLACES COMPONENTS INTO PANES
        teamsToolbar.getItems().add(addTeamButton);
        teamsToolbar.getItems().add(removeTeamButton);
        teamsToolbar.getItems().add(editTeamButton);
        teamsToolbar.getItems().add(selectTeamLabel);
        teamsToolbar.getItems().add(teamsComboBox);
        
        startingPlayersPane.getChildren().add(startingPlayersLabel);
        startingPlayersPane.getChildren().add(startingPlayersTable);
     
        reservePlayersPane.getChildren().add(reservePlayersLabel);
        reservePlayersPane.getChildren().add(reservePlayersTable);
        
        playersTablePane.getChildren().add(teamsToolbar);
        playersTablePane.getChildren().add(startingPlayersPane);
        playersTablePane.getChildren().add(reservePlayersPane);
     
        playersTablePane.setStyle("-fx-background-color: #FFFFFF"); // TODO EXPORT TO CSS FILE
        playersTablePane.setPadding(new Insets(5,5,5,5));
        
        // PUTS IT ALL TOGETHER
        this.setPadding(new Insets(5,10,15,10));
        this.setCenter(playersTablePane);
        
   }
   
   public FantasyTeamsPane(Draft draft, Stage primaryStage) {
       
       this();
       this.draft = draft;
       this.primaryStage = primaryStage;
       editPlayerDialog = new EditPlayerDialog(primaryStage);
       teamDialog = new FantasyTeamDialog(primaryStage);
       
          for(int i = 0; i < draft.getTeams().size(); i++) {
        
           teamsComboBox.getItems().add(draft.getTeams().get(i).getName());
        
        }
         
       addTeamButton.setOnAction(e-> {
          Team newTeam = teamDialog.showAddTeamDialog();
           if(teamDialog.getSelection().contentEquals("Complete")
                   && draft.hasTeam(newTeam.getName()) == false) {
               draft.getTeams().add(newTeam);
               teamsComboBox.getItems().add(newTeam.getName());
           }
           
        });
       
       editTeamButton.setOnAction(e-> {
           if(!teamsComboBox.getSelectionModel().isEmpty()) {
           int index = teamsComboBox.getSelectionModel().getSelectedIndex();
           String newName = teamDialog.showEditTeamDialog(draft.getTeam(teamsComboBox.getSelectionModel().getSelectedItem().toString()));
           teamsComboBox.getItems().set(index, newName);
           
  
           
           
       }});
       
       removeTeamButton.setOnAction(e-> {
           if(!teamsComboBox.getSelectionModel().isEmpty()) {
                int index = teamsComboBox.getSelectionModel().getSelectedIndex();
                Team t = draft.getTeam(teamsComboBox.getSelectionModel().getSelectedItem().toString());
                
              for(int i = 0 ; i < t.getStartingPlayers().length; i++) {
               
               t.getStartingPlayers()[i].setFantasyTeam(null);
               t.getStartingPlayers()[i].setContract(null);
               t.getStartingPlayers()[i].setPosition(null);
               t.getStartingPlayers()[i].setSalary(0);
               t.getStartingPlayers()[i].setContract(null);
               t.getStartingPlayers()[i].setDraftNumber(0);
               
                if(t.getStartingPlayers()[i] != null) {  
             
                if(t.getStartingPlayers()[i].getPositions().contains("P")) {
                       draft.getRoster().get(0).add(t.getStartingPlayers()[i]);
              }
                else {
                    draft.getRoster().get(1).add(t.getStartingPlayers()[i]);
                    
                }
                draft.getFullRoster();
                t.getStartingPlayers()[i] = null;
               
               }
              
              }
              
                 for(int i = 0 ; i < t.getReservePlayers().length; i++) {
               if(t.getReservePlayers()[i] != null) {
                   
                     t.getReservePlayers()[i].setFantasyTeam(null);
               t.getReservePlayers()[i].setContract(null);
               t.getReservePlayers()[i].setPosition(null);
               t.getReservePlayers()[i].setSalary(0);
               t.getReservePlayers()[i].setContract(null);
               t.getReservePlayers()[i].setDraftNumber(0);
               
                if(t.getReservePlayers()[i].getPositions().contains("P")) {
                       draft.getRoster().get(0).add(t.getReservePlayers()[i]);
              }
                else {
                    draft.getRoster().get(1).add(t.getReservePlayers()[i]);
                    
                }
                draft.getFullRoster();
                t.getReservePlayers()[i] = null;
               
               }
              
              }
                
                draft.removeTeam(teamsComboBox.getSelectionModel().getSelectedItem().toString());
                teamsComboBox.getItems().remove(index);
         
           
           }
       });
       
       teamsComboBox.setOnAction(e-> {
              if(!teamsComboBox.getSelectionModel().isEmpty())  {
                  viewableStartingPlayers = FXCollections.observableArrayList(
                  draft.getTeam(teamsComboBox.getSelectionModel().getSelectedItem().toString()).getStartingPlayersList());
                  startingPlayersTable.reloadPlayerTable(viewableStartingPlayers);
                  
                  viewableReservePlayers = FXCollections.observableArrayList(
                  draft.getTeam(teamsComboBox.getSelectionModel().getSelectedItem().toString()).getReservePlayersList());
                  reservePlayersTable.reloadPlayerTable(viewableReservePlayers);
              }
       });
       
          startingPlayersTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                // OPEN EDIT PLAYER DIALOG
                Player playerToEdit = startingPlayersTable.getSelectionModel().getSelectedItem();
                editPlayerDialog.showEditPlayerDialog(playerToEdit, draft);
                       
            }
            
          
        });
       
          reservePlayersTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                // OPEN EDIT PLAYER DIALOG
                Player playerToEdit = reservePlayersTable.getSelectionModel().getSelectedItem();
                editPlayerDialog.showEditPlayerDialog(playerToEdit, draft);
                       
            }
            
          
        });
   }



}
       


