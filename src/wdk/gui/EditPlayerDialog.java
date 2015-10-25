/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.gui;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wdk.data.Draft;
import wdk.data.Player;
import wdk.data.Team;
import static wdk.gui.WDK_GUI.CLASS_HEADING_LABEL;
import static wdk.gui.WDK_GUI.CLASS_PROMPT_LABEL;
import static wdk.gui.WDK_GUI.CLASS_SUBHEADING_LABEL;
import static wdk.gui.WDK_GUI.PRIMARY_STYLE_SHEET;

/**
 *
 * @author Terrell Mack
 */
public class EditPlayerDialog extends Stage {
    
    ObservableList viewableFantasyTeams;
    ObservableList viewablePositions;
    
    
    Pane dialogPane;
    VBox playerInfoPane;
    Scene playerDialogScene;
    String selection;
    
    HBox playerBox;
    HBox imageBox;
    HBox fantasyTeamBox;
    HBox positionBox;
    HBox contractBox;
    HBox salaryBox; 
    HBox completeBox;
    VBox mainPane;

    
    Label headingLabel;

    Label fullNameLabel;
    Label positionsLabel;
    Label fantasyTeamLabel;
    ComboBox fantasyTeamComboBox; 
    Label positionLabel;
    ComboBox positionComboBox; 
    Label contractLabel;
    ComboBox contractComboBox;
    Label salaryLabel;
    TextField salaryTextField; 
    
    Button completeButton;
    Button cancelButton;
    Image playerImage;
    Image flagImage;
    Boolean editingTeamPlayer;
            
    static final String[] PRO_TEAMS = {"ATL","AZ","CHC","CIN","COL","LAD","MIA","MIL"
   ,"NYM", "PHI", "PIT", "SD", "SF", "STL", "WAS"};
    
    
   public EditPlayerDialog(Stage primaryStage) {
       
  
       // THIS WILL HOLD ALL THE DIALOGS COMPONENTS 
        dialogPane = new GridPane();
        viewableFantasyTeams = FXCollections.observableArrayList();
        // CREATES ALL THE LABELS
        headingLabel = new Label("Player Details");

        fullNameLabel = new Label("NO NAME"); //displays edited player name
        positionsLabel = new Label("NO_POSITIONS"); // displayed edited player positions
        fantasyTeamLabel = new Label("Fantasy Team: ");
        positionLabel = new Label("Position: ");
        contractLabel = new Label("Contract: ");
        salaryLabel = new Label("Salary ($): ");

        // AND TEXTFIELDS
        salaryTextField = new TextField();
        
        // CREATES COMBO BOXES
        fantasyTeamComboBox = new ComboBox();
        positionComboBox = new ComboBox();
        contractComboBox = new ComboBox();
        
        contractComboBox.getItems().add("S1");
        contractComboBox.getItems().add("S2");
        contractComboBox.getItems().add("X");
        
        // AND FINALLY BUTTONS
        completeButton = new Button("Complete");
        cancelButton = new Button("Cancel");
        
        // THIS STORES THE NAME OF THE LAST BUTTON CLICKED
        selection = null; 
           
        // SETS UP DIALOG PANE USED FOR EDITING A PLAYER
        dialogPane = new Pane();
        
        playerInfoPane =  new VBox();
        
      playerBox = new HBox();
      fantasyTeamBox = new HBox();
      positionBox = new HBox();
      contractBox = new HBox();
      salaryBox = new HBox();
      completeBox = new HBox();
      
      playerImage = new Image("file:images/players/AAA_PhotoMissing.jpg");
      flagImage = new Image("file:images/flags/USA.png");
      ImageView playerImageView = new ImageView(playerImage);
      ImageView flagImageView = new ImageView(flagImage);
      
      mainPane = new VBox();
        
       
      playerInfoPane.getChildren().addAll(fullNameLabel,positionsLabel);
      playerBox.getChildren().addAll(playerImageView, flagImageView);
      fantasyTeamBox.getChildren().addAll(fantasyTeamLabel, fantasyTeamComboBox);          
      positionBox.getChildren().addAll(positionLabel, positionComboBox);
      contractBox.getChildren().addAll(contractLabel, contractComboBox);
      salaryBox.getChildren().addAll(salaryLabel, salaryTextField);
      completeBox.getChildren().addAll(completeButton, cancelButton);
      
      completeBox.setSpacing(10);
      
      mainPane.getChildren().addAll(headingLabel, playerBox, playerInfoPane,fantasyTeamBox,positionBox,
              contractBox, salaryBox, completeBox);
  
        // THIS GIVES CONTROL OF THE PROGRAM TO THIS DIALOG
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            EditPlayerDialog.this.selection = sourceButton.getText();
            
            if((fantasyTeamComboBox.getSelectionModel().getSelectedItem() != null
               && positionComboBox.getSelectionModel().getSelectedItem() != null
               && contractComboBox.getSelectionModel().getSelectedItem() != null
               && salaryTextField.getCharacters().length() != 0) || selection.equals("Cancel")
                ||fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString().contentEquals("Free Agent")
                || positionComboBox.getSelectionModel().getSelectedItem().toString().contentEquals("Taxi Squad"))
            
                
                
               
                
                EditPlayerDialog.this.hide();
        };
        
         completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);
        
        this.setTitle("Edit Player");
        mainPane.setPadding(new Insets(10,10,10,10));
        mainPane.setSpacing(10);
        playerDialogScene = new Scene(mainPane);
        
       playerDialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
       headingLabel.getStyleClass().add(CLASS_SUBHEADING_LABEL); 
       fullNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        this.setScene(playerDialogScene);
       
   }
   
   public boolean showEditPlayerDialog(Player playerToEdit, Draft draft) {
       
      fullNameLabel.setText(playerToEdit.getFirstName() + " " + playerToEdit.getLastName());
      positionsLabel.setText(playerToEdit.getPositions());
    
     
      playerImage = new Image("file:images/players/"+playerToEdit.getLastName()+playerToEdit.getFirstName()+".jpg");
      if(playerImage.isError()) {
        playerImage = new Image("file:images/players/AAA_PhotoMissing.jpg");
      }
      
       flagImage = new Image("file:images/flags/"+playerToEdit.getBirthNation()+".png");
       if(flagImage.isError()) {
        flagImage = new Image("file:images/flags/USA.png");
      }
      
    
      ImageView playerImageView = new ImageView(playerImage);
      ImageView flagImageView = new ImageView(flagImage);
    
       positionBox.getChildren().remove(positionComboBox);
       playerBox.getChildren().remove(0);
       playerBox.getChildren().remove(0);
       playerBox.getChildren().add(playerImageView);
       playerBox.getChildren().add(flagImageView);
       positionComboBox = new ComboBox();
       positionBox.getChildren().add(positionComboBox);
       
       fantasyTeamBox.getChildren().remove(1);
       fantasyTeamComboBox = new ComboBox();
       fantasyTeamBox.getChildren().add(fantasyTeamComboBox);       
       
       if(playerToEdit.getFantasyTeam() != null) {
            fantasyTeamComboBox.getItems().add("Free Agent");
       }
       
        
       for(Team teams: draft.getTeams()) {
            fantasyTeamComboBox.getItems().add(teams.getName().toString());
       }
       
       
      editingTeamPlayer = false;
      
      if(playerToEdit.getFantasyTeam() != null) {
          editingTeamPlayer = true;
         draft.getTeam(playerToEdit.getFantasyTeam()).removePlayer(playerToEdit);   
          
          //for(int i = 0; i < fantasyTeamComboBox.getItems().size(); i++) {
            //  if(fantasyTeamComboBox.getItems().get(i).toString().contentEquals(playerToEdit.getFantasyTeam())) {
                  //fantasyTeamComboBox.setPlaceholder(fantasyTeamComboBox.getItems().get(i));
                  //fantasyTeamComboBox.setSelectionModel((SingleSelectionModel) fantasyTeamComboBox.getItems().get(i));
              //}
          //} 

      }     
      fantasyTeamComboBox.setOnAction(e-> {
      
          if(!(fantasyTeamComboBox.getSelectionModel().getSelectedItem() == null) 
                  && !fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString().contentEquals("Free Agent")) {
            ArrayList<String> teamOpenPositions  = draft.getTeam(fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString()).getOpenPositions();
            ArrayList<String> playerPlayablePositions = playerToEdit.getPostions();
                
       positionBox.getChildren().remove(positionComboBox);
       positionComboBox = new ComboBox();
       positionBox.getChildren().add(positionComboBox);
       
        if(draft.allStartingLineupsFull() && draft.getTeams().size() > 0) {
           positionComboBox.getItems().add("Taxi Squad");
       
       }
        boolean isHitter = true;
        boolean uAdded = false;
        for(String s : playerPlayablePositions) {
            if(s.contentEquals("P")) {
            isHitter = false;
            }
        }
   
            for(int i = 0; i < playerPlayablePositions.size(); i++) {
                
                for(int j = 0; j < teamOpenPositions.size(); j++) {
                
                    if(playerPlayablePositions.get(i).contentEquals(teamOpenPositions.get(j))) {
                    
                    positionComboBox.getItems().add(playerPlayablePositions.get(i));
                }
                    
                    else if((teamOpenPositions.get(j).contentEquals("U") 
                            && isHitter)
                            && uAdded == false) {
                        uAdded = true;
                        positionComboBox.getItems().add("U");
                    
                    }
                }
            
            }
            
          }
          
          else {
            positionComboBox.getItems().add(playerToEdit.getPosition());
            
          }
      
      });
      
      positionComboBox.setOnAction((Event e)-> {
      
          if(positionComboBox.getSelectionModel().getSelectedItem() != null 
                  && positionComboBox.getSelectionModel().getSelectedItem().toString().contentEquals("Taxi Squad")) {
          
          }

      });
      
      
      

       this.showAndWait();
       
       
     if((selection == null || selection.equals("Cancel")) && editingTeamPlayer) {
           
         if(playerToEdit.getPosition() == null) {
         draft.getTeam(playerToEdit.getFantasyTeam()).addReservePlayer(playerToEdit);
         }  else{
         draft.getTeam(playerToEdit.getFantasyTeam()).addStartingPlayer(playerToEdit, playerToEdit.getPosition());
         }   
     }
       
      if(selection != null && selection.contentEquals("Complete")) {

          if(fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString().contentEquals("Free Agent")) {
              draft.getTeam(playerToEdit.getFantasyTeam()).removePlayer(playerToEdit);
              playerToEdit.setFantasyTeam(null);
              playerToEdit.setContract(null);
              playerToEdit.setPosition(null);
              playerToEdit.setSalary(0);
              playerToEdit.setContract(null);
              playerToEdit.setDraftNumber(0);
              // TODO remove from draft Draft
              if(playerToEdit.getPositions().contains("P")) {
                draft.getRoster().get(0).add(playerToEdit);
              } 
              else {
              draft.getRoster().get(1).add(playerToEdit);
              }
          
          } else {
              
          if(positionComboBox.getSelectionModel().getSelectedItem().toString().contentEquals("Taxi Squad")) {
            playerToEdit.setFantasyTeam(fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString());
            playerToEdit.setContract("X");
            playerToEdit.setSalary(1);
            draft.getTeam(playerToEdit.getFantasyTeam()).addReservePlayer(playerToEdit);
            return true;
          
          
          }    else {
                
          playerToEdit.setPosition(positionComboBox.getSelectionModel().getSelectedItem().toString());
          playerToEdit.setContract(contractComboBox.getSelectionModel().getSelectedItem().toString());
          playerToEdit.setSalary(Double.parseDouble(salaryTextField.getText()));
          playerToEdit.setDraftNumber(draft.getDraftCount());
          draft.incrementDraftCount();
          
          }
         
              
              
              if(playerToEdit.getFantasyTeam() == null) {
             playerToEdit.setFantasyTeam(fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString());
            draft.getTeam(playerToEdit.getFantasyTeam()).addStartingPlayer(playerToEdit, playerToEdit.getPosition());
            draft.getTeam(playerToEdit.getFantasyTeam()).setFunds(draft.getTeam(playerToEdit.getFantasyTeam()).getFunds() - playerToEdit.getSalary());
            draft.removePlayer(playerToEdit);
              return true;
              }
              else {
               draft.getTeam(playerToEdit.getFantasyTeam()).removePlayer(playerToEdit);
               draft.getTeam(fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString()).addStartingPlayer(playerToEdit, 
                       positionComboBox.getSelectionModel().getSelectedItem().toString());
               
               playerToEdit.setFantasyTeam(fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString());
             
              }

            
     
          }
      } 
       
      return false; 
   }
   
   public String getSelection() {
        return selection;
   }
    
   public boolean wasCompleteSelected() {
        return selection.equals("Complete");
   }
    
    
}
