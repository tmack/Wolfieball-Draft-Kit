package wdk.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import wdk.data.Draft;
import static wdk.gui.WDK_GUI.CLASS_HEADING_LABEL;

/**
 *
 * @author Terrell Mack
 */
public class DraftPane extends BorderPane {
    
  Draft draft; 
    
  Label title;
  Pane topPane;
   
  ToolBar draftToolBar; 
  Button draftPlayerButton;
  Button autoDraftButton;
  PlayersTable draftTable;
  Task<Void> draftTask;
  Pane mainPane;
 
  
  ObservableList viewablePlayers;
  Boolean draftRunning;
   
  
   public DraftPane() {  
       
       
     title = new Label("Draft Summary");
     topPane = new Pane();
     title.getStyleClass().add(CLASS_HEADING_LABEL);
      
     draftTable = new PlayersTable("DraftTable");
     draftRunning = false;
     
     topPane.getChildren().add(title);
     this.setTop(topPane);
     this.setPadding(new Insets(5,10,15,10));
 
   }
   
   public DraftPane(Draft draft) {
       this();
       this.draft = draft;
       
     // draft one player button
     draftPlayerButton = new Button("Draft Player");
     draftPlayerButton.setOnAction(e-> {
    
    draft.draftPlayer();       
    reloadDraftTable();

     });
     
     
     // auto draft button
     autoDraftButton = new Button("Start");
     
     autoDraftButton.setOnAction(e-> {
        
         if(autoDraftButton.getText().contentEquals("Start")) {
             // start auto draft
             startAutoDraft();
         }
         else {
             // stop auto draft
             pauseAutoDraft();
         
         }
         
         toggleAutoDraftButton();

     });
     
     
     
     
     draftToolBar = new ToolBar();
     draftToolBar.getItems().addAll(draftPlayerButton, autoDraftButton);
     
     mainPane = new VBox();
     mainPane.getChildren().addAll(draftToolBar, draftTable);
     
     this.setCenter(mainPane);
     
     
    reloadDraftTable();
     
     // auto draft button
     // drafted players table
     /* draft players table
            / pick #
            / first name
            / last name
            / Team
            / Contract
            / Salary
     
     */
       
  
   }
   
   public void reloadDraftTable() {
    draft.reloadDraftList();
     viewablePlayers = FXCollections.observableArrayList(draft.getDraftedPlayers());
                  draftTable.reloadPlayerTable(viewablePlayers);
   }
   
   public void toggleAutoDraftButton() {
   
       if(autoDraftButton != null && autoDraftButton.getText().contentEquals("Start")) {
           autoDraftButton.setText("Pause");
        
       } else {
           autoDraftButton.setText("Start");
       
       
       }
      
   }
   
   public void startAutoDraft() {
    
      draftRunning = true;
       
      draftTask = new Task<Void>() {
      @Override
      protected Void call() throws Exception {

       while(!draft.allTeamsFull() && draftRunning) {  

           draft.draftPlayer();
           Thread.sleep(1000);
           
                   Platform.runLater(new Runnable() {
               
                            public void run() {     
                                reloadDraftTable();
                             
                            }
                        });

            }
                return null;
          }
        };

        Thread thread = new Thread(draftTask);
        thread.start();
   
   }
   
   public void pauseAutoDraft() {
       draftRunning = false;

   }
   
  
    
}
