/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.gui;

import java.util.ArrayList;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import wdk.data.Player;

/**
 *
 * @author Terrell Mack
 */
public class PlayersTable extends TableView<Player> {
    // all the columns for displaying player data
    TableColumn positionCol;
    TableColumn firstNameCol;
    TableColumn lastNameCol;
    TableColumn proTeamCol;
    TableColumn positionsCol;
    TableColumn birthYearCol;
    TableColumn valueCol; // the estimated value of a player
    TableColumn notesCol;
    TableColumn contractCol;
    TableColumn salaryCol;
    
    TableColumn pickNumberCol;
    TableColumn fantasyTeamCol;
    
    // these are meant to be temporary couldnt figure out how to make an array
    TableColumn stat1Col;
    TableColumn stat2Col;
    TableColumn stat3Col;
    TableColumn stat4Col;
    TableColumn stat5Col;
    
   // permanent columns
    ArrayList<TableColumn> statsCol;
    
    public PlayersTable(String tableType) {
        
        if(tableType.contentEquals("FantasyTeamTable")) {
            initFantasyTeamTable();
        }
        if(tableType.contentEquals("DraftTable")){
            initDraftTable();   
        }
        
        if(tableType.contentEquals("ReserveTable")) {
            this.initReservesTable();
        }
        
        if(tableType.contentEquals("MLBTable")) {
            this.initMLBTable();
        }

    }
    
    public void initFantasyTeamTable() {
    // creates each column of players table   
    positionCol = new TableColumn("Position");
    positionCol.setPrefWidth(80);
    positionCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("position"));
    
    
    firstNameCol = new TableColumn("First Name");
    firstNameCol.setPrefWidth(80);
    firstNameCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("firstName"));
        
    lastNameCol = new TableColumn("Last Name");
     lastNameCol.setPrefWidth(80);
    lastNameCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("lastName"));
    
    proTeamCol = new TableColumn("Pro Team");
    proTeamCol.setPrefWidth(100);
    proTeamCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("proTeam"));
    
    positionsCol = new TableColumn("Positions");
      positionsCol.setPrefWidth(90);
    positionsCol.setCellValueFactory(
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
    
    contractCol = new TableColumn("Contract");
    contractCol.setPrefWidth(60);
    contractCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("contract"));
    
        
    salaryCol = new TableColumn("Salary");
    salaryCol.setPrefWidth(60);
    salaryCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("salary"));
   
    statsCol = new ArrayList<TableColumn>();

    // Adds columns to table
   // this.setItems(players);
    this.getColumns().addAll(
            positionCol,
            firstNameCol, 
            lastNameCol, 
            proTeamCol, 
            positionsCol,
            birthYearCol,
            stat1Col,
            stat2Col,
            stat3Col,
            stat4Col,
            stat5Col,
            valueCol,
            contractCol,
            salaryCol);
    }
    
    public void initReservesTable() {
    
    positionCol = new TableColumn("Eligable Positions");
    positionCol.setPrefWidth(80);
    positionCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("positions"));
    
    
    firstNameCol = new TableColumn("First Name");
    firstNameCol.setPrefWidth(80);
    firstNameCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("firstName"));
        
    lastNameCol = new TableColumn("Last Name");
     lastNameCol.setPrefWidth(80);
    lastNameCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("lastName"));
    
    proTeamCol = new TableColumn("Pro Team");
    proTeamCol.setPrefWidth(100);
    proTeamCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("proTeam"));
    
    positionsCol = new TableColumn("Positions");
      positionsCol.setPrefWidth(90);
    positionsCol.setCellValueFactory(
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
    
    contractCol = new TableColumn("Contract");
    contractCol.setPrefWidth(60);
    contractCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("contract"));
    
        
    salaryCol = new TableColumn("Salary");
    salaryCol.setPrefWidth(60);
    salaryCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("salary"));
   
    statsCol = new ArrayList<TableColumn>();

    // Adds columns to table
   // this.setItems(players);
    this.getColumns().addAll(
            positionCol,
            firstNameCol, 
            lastNameCol, 
            proTeamCol, 
            positionsCol,
            birthYearCol,
            stat1Col,
            stat2Col,
            stat3Col,
            stat4Col,
            stat5Col,
            valueCol,
            contractCol,
            salaryCol);
    }
    
    
    
    
    public void initDraftTable() {
   
    pickNumberCol = new TableColumn("Pick #");
    pickNumberCol.setPrefWidth(80);
    pickNumberCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("draftNumber"));
   
    firstNameCol = new TableColumn("First Name");
    firstNameCol.setPrefWidth(80);
    firstNameCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("firstName"));
        
    lastNameCol = new TableColumn("Last Name");
     lastNameCol.setPrefWidth(80);
    lastNameCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("lastName"));
    
    fantasyTeamCol = new TableColumn("Team");
    fantasyTeamCol.setPrefWidth(100);
    fantasyTeamCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("fantasyTeam"));
    
    contractCol = new TableColumn("Contract");
    contractCol.setPrefWidth(80);
    contractCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("contract"));
    
    salaryCol = new TableColumn("Salary");
    salaryCol.setPrefWidth(60);
    salaryCol.setCellValueFactory(
    new PropertyValueFactory<Player, Double>("salary"));
    
     this.getColumns().addAll(
            pickNumberCol,
            firstNameCol, 
            lastNameCol, 
            fantasyTeamCol, 
            contractCol,
            salaryCol
            );
    }
    
    public void initMLBTable() {

   
    firstNameCol = new TableColumn("First Name");
    firstNameCol.setPrefWidth(80);
    firstNameCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("firstName"));
        
    lastNameCol = new TableColumn("Last Name");
    lastNameCol.setPrefWidth(80);
    lastNameCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("lastName"));
    
    positionCol = new TableColumn("Positions");
    positionCol.setPrefWidth(80);
    positionCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("positions"));
    
    this.getColumns().addAll(firstNameCol,
            lastNameCol,
            positionCol);
    
    }

    public PlayersTable(ObservableList<Player> items) {
        super(items);
    }
    
     public void reloadPlayerTable(ObservableList players) {
        this.setItems(players);
    }  
}
