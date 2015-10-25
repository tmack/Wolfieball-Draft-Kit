/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import wdk.data.Draft;
import wdk.data.Player;
import static wdk.gui.WDK_GUI.CLASS_HEADING_LABEL;

/**
 *
 * @author Terrell Mack
 */
public class TeamsPane extends BorderPane {
    
   Label title;
   Pane topPane;
   
   Label selectTeamLabel;
   ComboBox proTeamComboBox;
   ToolBar selectTeamsBox;
   VBox mainBox;
   
   PlayersTable mlbTable;
   
   ObservableList<Player> viewablePlayers;
   
   public TeamsPane(Draft draft) {
       
     title = new Label("MLB Teams");
     title.getStyleClass().add(CLASS_HEADING_LABEL);
     topPane = new Pane();
     
     topPane.getChildren().add(title);
     this.setTop(topPane);
     
     proTeamComboBox = new ComboBox();
     proTeamComboBox.getItems().addAll("ATL", "AZ", "CHC", "CIN", "COL", "LAD", "MIA", "MIL"
             , "NYM", "PHI", "PIT", "SD", "SF", "STL", "WAS");
     
    selectTeamLabel = new Label("Select Pro Team:");
    
    mlbTable = new PlayersTable("MLBTable");
    selectTeamsBox = new ToolBar();
    mainBox = new VBox();
    
    proTeamComboBox.setOnAction(e-> {
        
        if(proTeamComboBox.getSelectionModel().getSelectedItem() != null) {
            viewablePlayers = FXCollections.observableArrayList(draft.getProTeamPlayers(
            proTeamComboBox.getSelectionModel().getSelectedItem().toString()));
            mlbTable.reloadPlayerTable(viewablePlayers);
      
        
            
        
        }
    
    
    });
    
    
    selectTeamsBox.getItems().addAll(selectTeamLabel,proTeamComboBox);    
    
    mainBox.getChildren().addAll(selectTeamsBox, mlbTable);
    this.setCenter(mainBox);
    
     
     this.setPadding(new Insets(5,10,15,10));
  
   }
    
}
