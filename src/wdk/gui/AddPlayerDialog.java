/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.gui;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wdk.data.Player;
import static wdk.gui.WDK_GUI.CLASS_SUBHEADING_LABEL;
import static wdk.gui.WDK_GUI.PRIMARY_STYLE_SHEET;

/**
 *
 * @author Terrell Mack
 */
public class AddPlayerDialog extends Stage {
    
    Scene addPlayerScene;
    Scene editPlayerScene;
    VBox addPlayerPane;
    
    Label headingLabel;
    Label firstNameLabel;
    Label lastNameLabel;
    Label TeamLabel;
    
    TextField firstNameTextField;
    TextField lastNameTextField;
    ComboBox teamsComboBox;
    
    HBox positionsBox;
    CheckBox cCheckBox;
    CheckBox b1CheckBox;
    CheckBox b3CheckBox;
    CheckBox b2CheckBox;
    CheckBox ssCheckBox;
    CheckBox ofCheckBox;
    CheckBox pCheckBox;
    
    Button completeButton;
    Button cancelButton;

   static final String[] PRO_TEAMS = {"ATL","AZ","CHC","CIN","COL","LAD","MIA","MIL"
   ,"NYM", "PHI", "PIT", "SD", "SF", "STL", "WAS"};
   
   String selection;
   
   Label playerNameLabel;
   Label playablePositonsLabel;

   Label fantasyTeamLabel;
   ComboBox fantasyTeamsComboBox;
   
   Label positionsLabel;
   ComboBox positionsComboBox;
   
   Label contractLabel;
   ComboBox contractsComboBox;
   
   Label salaryLabel;
   TextField salaryTextField;
   
   GridPane editPlayerPane;
   
   Label editPlayerLabel;
   Button editCompleteButton;
   Button editCancelButton;
   
    public AddPlayerDialog(Stage primaryStage) {
    
    // INITIALIZE ALL THE COMPONENTS
    initLabels();
    initTextFields();
    initComboBox();
    initCheckBoxes();
    initButtons();
    
    // ADD ALL THE COMPONENTS TO  PANE
    GridPane playerInfoPane = new GridPane();
    playerInfoPane.setHgap(10);
    playerInfoPane.setVgap(10);
    playerInfoPane.add(firstNameLabel, 0, 0);
    playerInfoPane.add(firstNameTextField, 1, 0);
    playerInfoPane.add(lastNameLabel, 0, 1);
    playerInfoPane.add(lastNameTextField, 1, 1);
    playerInfoPane.add(TeamLabel, 0, 2);
    playerInfoPane.add(teamsComboBox, 1, 2);
   
    GridPane confirmCancelPane = new GridPane();
    confirmCancelPane.setPadding(new Insets(10,0,0,0));
    confirmCancelPane.setHgap(10);
    confirmCancelPane.setVgap(10);
    confirmCancelPane.add(completeButton, 0, 0);
    confirmCancelPane.add(cancelButton, 1, 0);
           
    
    addPlayerPane = new VBox();
    addPlayerPane.setPadding(new Insets(15,15,15,15));
    addPlayerPane.setSpacing(10);
    
    
    addPlayerPane.getChildren().addAll(headingLabel, playerInfoPane, positionsBox, confirmCancelPane);
    
    initModality(Modality.WINDOW_MODAL);
    initOwner(primaryStage);
    
    playerNameLabel = new Label();
    playablePositonsLabel = new Label();

   Label playerNameLabel = new Label("No Name");
   Label playersPositionLabel = new Label("NO_POSITIONS");
   
   Label fantasyTeamLabel =  new Label("Fantasy Team: ");
   ComboBox fantasyTeamsComboBox = new ComboBox();
   
   Label positionsLabel = new Label("Position: ");
   ComboBox positionsComboBox = new ComboBox();
   
   Label contractLabel = new Label("Contract: ");
   ComboBox contractsComboBox = new ComboBox();
   
   Label salaryLabel = new Label("Salary ($): ");
   TextField salaryTextField = new TextField();
   
   editPlayerLabel = new Label("Player Details");
   
   editPlayerPane = new GridPane();
   editPlayerPane.add(editPlayerLabel, 0, 0, 2, 2);
        
    editPlayerPane.add(playerNameLabel, 0, 2, 1, 1);
    editPlayerPane.add(playersPositionLabel, 1, 2, 1, 1);
        
    editPlayerPane.add(fantasyTeamLabel, 0, 3, 1, 1);
    editPlayerPane.add(fantasyTeamsComboBox, 1, 3, 1, 1);
    
    editPlayerPane.add(positionsLabel, 0, 4, 1, 1);
    editPlayerPane.add(positionsComboBox, 1, 4, 1, 1);
    
    editPlayerPane.add(contractLabel, 0, 5, 1, 1);
    editPlayerPane.add(contractsComboBox, 1, 5, 1, 1);
    
     editPlayerPane.add(salaryLabel, 0, 6, 1, 1);
    editPlayerPane.add(salaryTextField, 1, 6, 1, 1);
        
   editPlayerPane.add(editCompleteButton, 0, 7, 1, 1);
    editPlayerPane.add(editCancelButton, 1, 7, 1, 1);
   
     addPlayerScene = new Scene(addPlayerPane);
      addPlayerScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
               headingLabel.getStyleClass().add(CLASS_SUBHEADING_LABEL); 
     
     editPlayerScene = new Scene(editPlayerPane);
    
    }
    
    public Player showAddPlayerDialog() {
    
 
    this.setScene(addPlayerScene);
    this.setTitle("Add Player");
    firstNameTextField.setText("");
    lastNameTextField.setText("");
    cCheckBox.setSelected(false);
    b1CheckBox.setSelected(false);
    b3CheckBox.setSelected(false);
    b2CheckBox.setSelected(false);
    ssCheckBox.setSelected(false);
    ofCheckBox.setSelected(false);
    pCheckBox.setSelected(false);
    
    this.showAndWait();
  
    String firstName = "";
    String lastName = "";
    String team = "";
    String positions = "";
        
        if(selection.equals("Complete")){
          
          firstName = firstNameTextField.getText();
          lastName = lastNameTextField.getText();
          team = teamsComboBox.getSelectionModel().getSelectedItem().toString();
          
          ArrayList<String> positionsList = new ArrayList<String>();
          
          if(cCheckBox.isSelected()) {
              positionsList.add("C");
          }
          
          if(b1CheckBox.isSelected()) {
            positionsList.add("1B");
          }
          
          if(b3CheckBox.isSelected()) {
              positionsList.add("3B");
          }
           
          if(b2CheckBox.isSelected()) {
             positionsList.add("2B");
          }
          
          if(ssCheckBox.isSelected()) {
              positionsList.add("SS");
          
          }
          
          if(ofCheckBox.isSelected()) {
              positionsList.add("OF");
          }
          
          if(pCheckBox.isSelected()) {
              positionsList.add("P");
          
          }
         
          for(int i = 0; i < positionsList.size(); i++) {
              
              if(i == 0 && positionsList.size() == 1) {
                  positions = positionsList.get(i);
              } 
              
              else if(i == 0) {
               positions = positionsList.get(i) + "_";
              }
              
              else if(i == positionsList.size() - 1) {
                  positions += positionsList.get(i);
              
              } else {
                  
                  positions += positionsList.get(i) + "_";
              }
             
              
          }
            
        } else {
        
            return null;
        }
        
        return new Player(firstName, lastName, team, positions);
    }
    
    public void initLabels() {    
        
        headingLabel = new Label("Player Details");
        firstNameLabel = new Label("First Name");
        lastNameLabel = new Label("Last Name");
        TeamLabel = new Label("Pro Team"); 
        
    }
    
    public void initTextFields() {
   
        firstNameTextField = new TextField();
        lastNameTextField = new TextField();
    
    }
    
    public void initCheckBoxes() {
       
        cCheckBox = new CheckBox("C");
        b1CheckBox = new CheckBox("1B");
        b3CheckBox = new CheckBox("3B");
        b2CheckBox = new CheckBox("2B");
        ssCheckBox = new CheckBox("SS");
        ofCheckBox = new CheckBox("OF");
        pCheckBox = new CheckBox("P");
         
        positionsBox = new HBox();
        positionsBox.getChildren().addAll(cCheckBox,b1CheckBox,b3CheckBox,
        b2CheckBox, ssCheckBox, ofCheckBox, pCheckBox);
        positionsBox.setSpacing(5);
    
    }
    
    public void initComboBox() {
        
        teamsComboBox = new ComboBox();
        
        for(String proTeams : PRO_TEAMS) {
            teamsComboBox.getItems().add(proTeams);
        }
        
        if(teamsComboBox.getItems().size() > 0) {
            teamsComboBox.setValue(teamsComboBox.getItems().get(0));
        }
        
    }
     
    public void initButtons() {
        
       completeButton = new Button("Complete");
       cancelButton = new Button("Cancel");
       
       editCompleteButton = new Button("Complete");
       editCancelButton = new Button("Cancel");
         // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            AddPlayerDialog.this.selection = sourceButton.getText();
            
            if((firstNameTextField.getCharacters().length() != 0 
               && lastNameTextField.getCharacters().length() != 0) || selection.equals("Cancel"))
            AddPlayerDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);
        
        
        editCompleteButton.setOnAction(completeCancelHandler);
        editCancelButton.setOnAction(completeCancelHandler); 
    
    }
    
    public String getSelection() {
        return selection;
    }
    
    public boolean wasCompleteSelected() {
        return selection.equals("Complete");
    }
    
}
