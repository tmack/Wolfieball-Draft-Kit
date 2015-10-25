package wdk.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wdk.data.Team;

/**
 *
 * @author Terrell Mack
 */
public class FantasyTeamDialog extends Stage {
    
    Label title;
    
    Label nameLabel;
    TextField nameTextField;
    
    Label ownerLabel;
    TextField ownerTextField;
    
    Button completeButton;
    Button cancelButton;
    
    GridPane fantasyTeamDialogPane;
    Scene fantasyTeamDialogScene;
    private String selection;
    
    public FantasyTeamDialog(Stage primaryStage) {
        
        title = new Label("Fantasy Team Details");
        
        nameLabel = new Label("Name:");
        nameTextField = new TextField();
        
        ownerLabel = new Label("Owner:");
        ownerTextField  = new TextField();
        
        completeButton = new Button("Complete");
        cancelButton = new Button("Cancel");
        
        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            FantasyTeamDialog.this.selection = sourceButton.getText();
            if(selection.contentEquals("Cancel") 
                    || nameTextField.getCharacters().length() > 0 
                    && ownerTextField.getCharacters().length() > 0) {
                FantasyTeamDialog.this.hide();
            } else {
            }
        };
        
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);
        
        fantasyTeamDialogPane = new GridPane();
        
        fantasyTeamDialogPane.add(title, 0, 0, 2, 2);
        
        fantasyTeamDialogPane.add(nameLabel, 0, 2, 1, 1);
        fantasyTeamDialogPane.add(nameTextField, 1, 2, 1, 1);
        
        
        fantasyTeamDialogPane.add(ownerLabel, 0, 3, 1, 1);
        fantasyTeamDialogPane.add(ownerTextField, 1, 3, 1, 1);
        
        fantasyTeamDialogPane.add(completeButton, 0, 4, 1, 1);
        fantasyTeamDialogPane.add(cancelButton, 1, 4, 1, 1);
        
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
    fantasyTeamDialogPane.setPadding(new Insets(10,10,10,10));
        fantasyTeamDialogScene = new Scene(fantasyTeamDialogPane);
        this.setScene(fantasyTeamDialogScene);

   
    }
    
    public Team showAddTeamDialog() {
        
        this.setTitle("Add New Fantasy Team");  
        nameTextField.clear();
        ownerTextField.clear();
        this.showAndWait();
        
        return new Team(nameTextField.getText(), ownerTextField.getText());
    }
    
    public String showEditTeamDialog(Team teamToEdit) {
        this.setTitle("Edit Fantasy Team");  
        nameTextField.setText(teamToEdit.getName());
        ownerTextField.setText(teamToEdit.getOwner());
        this.showAndWait();
        
        if(this.selection.contentEquals("Complete")) {
            teamToEdit.setName(nameTextField.getText());
            teamToEdit.setOwner(ownerTextField.getText());
            return nameTextField.getText();
        }
        
        return teamToEdit.getName();
    }

    public String getSelection() {
        return selection;
    }
    
    
    
    
}
