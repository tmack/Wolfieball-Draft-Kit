/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.gui;

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

/**
 *
 * @author Terrell Mack
 */
public class PlayerDialog extends Stage {
    
    /*
    GridPane editDialogPane;
    Pane addDialogPane;
    Scene playerDialogScene;
    VBox addPlayerPane;
    String selection;
    
    Label headingLabel;
    
    Label firstNameLabel;
    TextField firstNameTextField;
    Label lastNameLabel;
    TextField lastNameTextField;
    Label proTeamLabel;
    ComboBox proTeamComboBox; 
    HBox positionsBox;
    CheckBox cCheckBox;
    CheckBox b1CheckBox;
    CheckBox b3CheckBox;
    CheckBox b2CheckBox;
    CheckBox ssCheckBox;
    CheckBox ofCheckBox;
    CheckBox pCheckBox;
    
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
    
    static final String[] PRO_TEAMS = {"ATL","AZ","CHC","CIN","COL","LAD","MIA","MIL"
   ,"NYM", "PHI", "PIT", "SD", "SF", "STL", "WAS"};
   
    public PlayerDialog(Stage primaryStage) {
        
        // THIS WILL HOLD ALL THE DIALOGS COMPONENTS 
        addDialogPane = new Pane();
        editDialogPane = new GridPane();
        
        // CREATES ALL THE LABELS
        headingLabel = new Label("Player Details");
        firstNameLabel = new Label("First Name: ");
        lastNameLabel = new Label("Last Name: ");
        proTeamLabel = new Label("Pro Team: "); 
        fullNameLabel = new Label("NO NAME"); //displays edited player name
        positionsLabel = new Label("NO_POSITIONS"); // displayed edited player positions
        fantasyTeamLabel = new Label("Fantasy Team: ");
        positionLabel = new Label("Position: ");
        contractLabel = new Label("Contract: ");
        salaryLabel = new Label("Salary ($): ");

        // AND TEXTFIELDS
        firstNameTextField = new TextField();
        lastNameTextField = new TextField();
        firstNameTextField = new TextField();
        salaryTextField = new TextField();
        
        // AND CHECKBOXES
        cCheckBox = new CheckBox("C");
        b1CheckBox = new CheckBox("1B");
        b3CheckBox = new CheckBox("3B");
        b2CheckBox = new CheckBox("2B");
        ssCheckBox = new CheckBox("SS");
        ofCheckBox = new CheckBox("OF");
        pCheckBox = new CheckBox("P");
        
        // PUTS ALL POSITION CHECKBOXES INTO ONE BOX 
        positionsBox = new HBox();
        positionsBox.getChildren().addAll(cCheckBox,b1CheckBox,b3CheckBox,
        b2CheckBox, ssCheckBox, ofCheckBox, pCheckBox);
        positionsBox.setSpacing(5);
        
        // CREATES COMBO BOXES
        proTeamComboBox = new ComboBox();
        fantasyTeamComboBox = new ComboBox();
        positionComboBox = new ComboBox();
        contractComboBox = new ComboBox();
        
        // AND FINALLY BUTTONS
        completeButton = new Button("Complete");
        cancelButton = new Button("Cancel");
        
        // THIS STORES THE NAME OF THE LAST BUTTON CLICKED
        selection = null; 
        
        // SETS UP DIALOG FOR ADDING A PLAYER
        GridPane playerInfoPane = new GridPane();
        playerInfoPane.setHgap(10);
        playerInfoPane.setVgap(10);
        playerInfoPane.add(firstNameLabel, 0, 0);
        playerInfoPane.add(firstNameTextField, 1, 0);
        playerInfoPane.add(lastNameLabel, 0, 1);
        playerInfoPane.add(lastNameTextField, 1, 1);
        playerInfoPane.add(proTeamLabel, 0, 2);
        playerInfoPane.add(proTeamComboBox, 1, 2);
   
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
        
        // SETS UP DIALOG PANE USED FOR EDITING A PLAYER
        editDialogPane = new GridPane();
        
        editDialogPane.add(headingLabel, 0, 0, 2, 1);
        
        editDialogPane.add(fullNameLabel, 0, 1, 1, 1); 
        editDialogPane.add(positionsLabel, 1, 1, 1, 1); 
        
        editDialogPane.add(fantasyTeamLabel, 0, 2, 1, 1);
        editDialogPane.add(fantasyTeamComboBox, 1, 2, 1, 1);
        
        editDialogPane.add(positionLabel, 0, 3, 1, 1);
        editDialogPane.add(positionComboBox, 1, 3, 1, 1);
    
        editDialogPane.add(contractLabel, 0, 4, 1, 1);
        editDialogPane.add(contractComboBox, 1, 4, 1, 1);
        
        editDialogPane.add(salaryLabel, 0, 5, 1, 1);
        editDialogPane.add(salaryTextField, 1, 5, 1, 1);
        
        editDialogPane.add(completeButton, 0, 8, 1, 1);
        editDialogPane.add(cancelButton, 1, 8, 1, 1);
        
        // THIS GIVES CONTROL OF THE PROGRAM TO THIS DIALOG
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
       
    
    }
    
    public Player showAddPlayerDialog() {

        this.setTitle("Add Player");

        playerDialogScene = new Scene(addPlayerPane);
        this.setScene(playerDialogScene);
        this.showAndWait();
    
    return null;
    }
    
    public void showEditPlayerDialog(Player playerToEdit) {
        
        // SETS UP THE EDIT PLAYER DIALOG BOX
        this.setTitle("Edit Player");
             
        playerDialogScene = new Scene(editDialogPane);
        this.setScene(playerDialogScene);
        
        this.showAndWait();
    }
    
    public String getSelection() {
        return selection;
    }
    
    public boolean wasCompleteSelected() {
        return selection.equals("Complete");
    }
    
    
    */
}
