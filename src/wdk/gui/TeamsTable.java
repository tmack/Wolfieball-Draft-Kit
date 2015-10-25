/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import wdk.data.Team;

/**
 *
 * @author Terrell Mack
 */
public class TeamsTable extends TableView<Team> {
    
    TableColumn teamNameCol;
    TableColumn playersNeededCol;
    TableColumn fundsLeftCol;
    TableColumn fundsPPCol;
    
    TableColumn rStatCol;
    TableColumn hrStatCol;
    TableColumn rbiStatCol;
    TableColumn sbStatCol;
    TableColumn baStatCol;
    TableColumn wStatCol;
    TableColumn svStatCol; 
    TableColumn kStatCol;   
    TableColumn eraStatCol;
    TableColumn whipStatCol;
    
    TableColumn totalPointsCol;
    
    public TeamsTable(String tableType) {
    
        if(tableType.contentEquals("FantasyStandings")) {
            initFantasyStandingsTable();
        }
    
    
    }
    
    public void initFantasyStandingsTable() {
    
    teamNameCol = new TableColumn("Team Name");
    teamNameCol.setPrefWidth(100);
    teamNameCol.setCellValueFactory(
    new PropertyValueFactory<Team, String>("name"));
    
        
    playersNeededCol = new TableColumn("Players Needed");
    playersNeededCol.setPrefWidth(150);
    playersNeededCol.setCellValueFactory(
    new PropertyValueFactory<Team, String>("playersNeeded"));
    
    fundsLeftCol = new TableColumn("Funds");
    fundsLeftCol.setPrefWidth(60);
    fundsLeftCol.setCellValueFactory(
    new PropertyValueFactory<Team, Double>("funds"));
    
    playersNeededCol = new TableColumn("Players Needed");
    playersNeededCol.setPrefWidth(100);
    playersNeededCol.setCellValueFactory(
    new PropertyValueFactory<Team, Integer>("playersNeeded"));
    
    fundsPPCol = new TableColumn("$PP");
    fundsPPCol.setPrefWidth(60);
    fundsPPCol.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Team, String>, ObservableValue<String>>() {

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, String> team) {
           if(team.getValue().getPlayersNeeded() > 0) {
            int fundsPP =(int)((team.getValue().getFunds()/team.getValue().getPlayersNeeded()) + 0.5) ;
        return new SimpleStringProperty(fundsPP+"");
                   //p.getValue().getPrefix() + " " + p.getValue().getFirstName() + "," + p.getValue().getLastName());
           }
           else {
           return new SimpleStringProperty("0");
           }
        
    }
});   

    rStatCol = new TableColumn("R");
    rStatCol.setPrefWidth(60);
    rStatCol.setCellValueFactory(
    new PropertyValueFactory<Team, Integer>("rStat"));
    
    hrStatCol = new TableColumn("HR");
    hrStatCol.setPrefWidth(60);
    hrStatCol.setCellValueFactory(
    new PropertyValueFactory<Team, Integer>("hrStat"));
    
    rbiStatCol = new TableColumn("RBI");
    rbiStatCol.setPrefWidth(60);
    rbiStatCol.setCellValueFactory(
    new PropertyValueFactory<Team, Integer>("rbiStat"));
    
    sbStatCol = new TableColumn("SB");
    sbStatCol.setPrefWidth(60);
    sbStatCol.setCellValueFactory(
    new PropertyValueFactory<Team, Integer>("sbStat"));
    
    baStatCol = new TableColumn("BA");
    baStatCol.setPrefWidth(60);
    baStatCol.setCellValueFactory(
    new PropertyValueFactory<Team, Integer>("baStat"));
    
    sbStatCol = new TableColumn("SB");
    sbStatCol.setPrefWidth(60);
    sbStatCol.setCellValueFactory(
    new PropertyValueFactory<Team, Integer>("sbStat"));
    
    baStatCol = new TableColumn("BA");
    baStatCol.setPrefWidth(60);
    baStatCol.setCellValueFactory(
   // new PropertyValueFactory<Team, Integer>("baStat"));
        new Callback<TableColumn.CellDataFeatures<Team, String>, ObservableValue<String>>() {

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, String> team) {
     
            return new SimpleStringProperty( String.format("%.3f",team.getValue().getBaStat()));
                   //p.getValue().getPrefix() + " " + p.getValue().getFirstName() + "," + p.getValue().getLastName());
        
    }
});     
    
    wStatCol = new TableColumn("W");
    wStatCol.setPrefWidth(60);
    wStatCol.setCellValueFactory(
    new PropertyValueFactory<Team, Double>("wStat"));
    
    svStatCol = new TableColumn("SV");
    svStatCol.setPrefWidth(60);
    svStatCol.setCellValueFactory(
    new PropertyValueFactory<Team, Integer>("svStat"));
    
    kStatCol = new TableColumn("K");
    kStatCol.setPrefWidth(60);
    kStatCol.setCellValueFactory(
    new PropertyValueFactory<Team, Integer>("kStat"));
    
    eraStatCol = new TableColumn("ERA");
    eraStatCol.setPrefWidth(80);
    eraStatCol.setCellValueFactory(
   // new PropertyValueFactory<Team, Double>("eraStat"));
        new Callback<TableColumn.CellDataFeatures<Team, String>, ObservableValue<String>>() {

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, String> team) {
     
            return new SimpleStringProperty( String.format("%.2f",team.getValue().getEraStat()));
                   //p.getValue().getPrefix() + " " + p.getValue().getFirstName() + "," + p.getValue().getLastName());
        
    }
});     
    
    whipStatCol = new TableColumn("WHIP");
    whipStatCol.setPrefWidth(80);
    whipStatCol.setCellValueFactory(
    //new PropertyValueFactory<Team, Double>("whipStat"));
    new Callback<TableColumn.CellDataFeatures<Team, String>, ObservableValue<String>>() {

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, String> team) {
     
            return new SimpleStringProperty( String.format("%.2f",team.getValue().getWhipStat()));
                   //p.getValue().getPrefix() + " " + p.getValue().getFirstName() + "," + p.getValue().getLastName());
        
    }
});     
    
    totalPointsCol = new TableColumn("Total Points");
    totalPointsCol.setPrefWidth(60);
    totalPointsCol.setCellValueFactory(
    new PropertyValueFactory<Team, Integer>("totalPoints"));
    
    
       this.getColumns().addAll(
            teamNameCol,
            playersNeededCol, 
            fundsLeftCol,
            fundsPPCol,
            rStatCol, 
            hrStatCol, 
            rbiStatCol,
            sbStatCol,
            baStatCol,
            wStatCol,
            svStatCol,
            kStatCol,
            eraStatCol,
            whipStatCol,
            totalPointsCol
            );
    }
    
    
    
    public void reloadTeamList(ObservableList teams) {
     
        this.setItems(teams);
    }  
     
}
