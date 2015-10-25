/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import wdk.data.Draft;
import static wdk.gui.WDK_GUI.CLASS_HEADING_LABEL;

/**
 *
 * @author Terrell Mack
 */
public class FantasyStandingsPane extends BorderPane{
    
   Label title;
   Pane topPane;
   TeamsTable fantasyStandingsTable;
   Draft draft;
   
   ObservableList viewableTeams;
   
   public FantasyStandingsPane() {
       
     title = new Label("Fantasy Standings");
     topPane = new Pane();
     title.getStyleClass().add(CLASS_HEADING_LABEL);
    
     topPane.getChildren().add(title);
     this.setTop(topPane);
     this.setPadding(new Insets(5,10,15,10));
     
   }
   
   public FantasyStandingsPane(Draft draft) {
       this();
       this.draft = draft;
       fantasyStandingsTable = new TeamsTable("FantasyStandings");
       reloadFantasyStandingsTable();
       this.setCenter(fantasyStandingsTable);
   
   }
   
    public void reloadFantasyStandingsTable() {
    
    draft.reloadTeamsStats();
    draft.reloadTotalPoints();
    viewableTeams = FXCollections.observableArrayList(draft.getTeams());
    fantasyStandingsTable.reloadTeamList(viewableTeams);
    
   }
    
}
