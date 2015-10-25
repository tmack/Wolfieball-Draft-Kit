 package wdk.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import wdk.data.Draft;
import wdk.data.Player;
import wdk.data.Players;
import wdk.data.Statistic;
import wdk.data.Team;

/**
 *
 * @author Terrell Mack
 */
public class JsonDraftFileManager {
    
    private static String DATA_PATH = "data/";
    private static String HITTERS_FILE_NAME = "Hitters.json ";
    private static String PITCHERS_FILE_NAME = "Pitchers.json";
    
    // LOADS A JSON FILE AS A SINGLE OBJECT AND RETURNS IT
    private static JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        is.close();
        
        return jsonObject;
    }  
    
    public void saveDraft(Draft draftToSave, String filePath) throws IOException {
        
        // INIT THE WRITER
        OutputStream os = new FileOutputStream(filePath);
        JsonWriter jsonWriter = Json.createWriter(os);  
        
        JsonArray pitchersJsonArray = makePlayerJsonArray(draftToSave.getRoster().get(0));
        JsonArray hittersJsonArray = makePlayerJsonArray(draftToSave.getRoster().get(1));
        JsonArray teamsJsonArray = makeTeamJsonArray(draftToSave.getTeams());
        
         
           JsonObject draftJsonObject = Json.createObjectBuilder()
                                    .add("draftCount", draftToSave.getDraftCount())
                                    .add("Pitchers", pitchersJsonArray)
                                    .add("Hitters", hittersJsonArray)
                                    .add("Teams", teamsJsonArray)
                   .build();
           
           
           
        // AND SAVE EVERYTHING AT ONCE
        jsonWriter.writeObject(draftJsonObject);

    }
    
    public void loadDraft(Draft draftToLoad, String filePath) throws IOException {
         
        JsonObject json = loadJSONFile(filePath);
        
        if(draftToLoad == null) {
        draftToLoad = new Draft();
        } else {
        draftToLoad.clearRoster();
        draftToLoad.clearTeams();
        }
        
       // JsonObject Draftjso = j.getJsonObject("draftCount");
        
        draftToLoad.setDraftCount(json.getJsonNumber("draftCount").intValue());
   
        
        JsonArray pitchersPlayerItemsArray = json.getJsonArray("Pitchers");
        draftToLoad.getRoster().add(new Players());
          
        for (int i = 0; i < pitchersPlayerItemsArray.size(); i++) {
            
            JsonObject jso = pitchersPlayerItemsArray.getJsonObject(i);
            Player playerToLoad = new Player();
            
           
               playerToLoad.setFantasyTeam(jso.getString("fantasyTeam"));
                    playerToLoad.setFirstName(jso.getString("firstName"));
               playerToLoad.setLastName(jso.getString("lastName"));
                    playerToLoad.setContract(jso.getString("contract"));
               playerToLoad.setPosition(jso.getString("position"));
                    playerToLoad.setPositions(jso.getString("positions"));
               playerToLoad.setValue(jso.getJsonNumber("value").doubleValue());
               playerToLoad.setSalary(jso.getJsonNumber("salary").doubleValue());
                    playerToLoad.setDraftNumber(jso.getInt("draftNumber"));
               playerToLoad.setBirthYear(jso.getInt("birthYear"));
                    playerToLoad.setBirthNation(jso.getString("birthNation"));
               playerToLoad.setNotes(jso.getString("notes"));
              
               playerToLoad.setStat1(jso.getInt("stat1"));
               playerToLoad.setStat2(jso.getInt("stat2"));
               playerToLoad.setStat3(jso.getInt("stat3"));
               playerToLoad.setStat4(jso.getJsonNumber("stat4").doubleValue());
               playerToLoad.setStat5(jso.getJsonNumber("stat5").doubleValue());
               
               playerToLoad.setProTeam(jso.getString("proTeam"));
            
          
            // ADD IT TO THE COURSE
            draftToLoad.getRoster().get(0).add(playerToLoad);
        }
        
          JsonArray hittersPlayerItemsArray = json.getJsonArray("Hitters");
        draftToLoad.getRoster().add(new Players());
        
         for (int i = 0; i < hittersPlayerItemsArray.size(); i++) {
            
            JsonObject jso = hittersPlayerItemsArray.getJsonObject(i);
            Player playerToLoad = new Player();
            
           
               playerToLoad.setFantasyTeam(jso.getString("fantasyTeam"));
                    playerToLoad.setFirstName(jso.getString("firstName"));
               playerToLoad.setLastName(jso.getString("lastName"));
                    playerToLoad.setContract(jso.getString("contract"));
               playerToLoad.setPosition(jso.getString("position"));
                    playerToLoad.setPositions(jso.getString("positions"));
              playerToLoad.setValue(jso.getJsonNumber("value").doubleValue());
              playerToLoad.setSalary(jso.getJsonNumber("salary").doubleValue());
                    playerToLoad.setDraftNumber(jso.getInt("draftNumber"));
               playerToLoad.setBirthYear(jso.getInt("birthYear"));
                    playerToLoad.setBirthNation(jso.getString("birthNation"));
              playerToLoad.setNotes(jso.getString("notes"));
              
               playerToLoad.setStat1(jso.getInt("stat1"));
               playerToLoad.setStat2(jso.getInt("stat2"));
               playerToLoad.setStat3(jso.getInt("stat3"));
               playerToLoad.setStat4(jso.getJsonNumber("stat4").doubleValue());
               playerToLoad.setStat5(jso.getJsonNumber("stat5").doubleValue());
               
               playerToLoad.setProTeam(jso.getString("proTeam"));
            
          
            // ADD IT TO THE COURSE
            draftToLoad.getRoster().get(1).add(playerToLoad);
        }
         
           JsonArray teamsItemsArray = json.getJsonArray("Teams");
           
           for(int i = 0; i < teamsItemsArray.size(); i++) {
           
           JsonObject jso = teamsItemsArray.getJsonObject(i);
           Team teamToLoad = new Team();
            
           teamToLoad.setName(jso.getString("name"));
           teamToLoad.setOwner(jso.getString("owner"));
           teamToLoad.setFunds(jso.getJsonNumber("funds").doubleValue());
           teamToLoad.setRank(jso.getJsonNumber("rank").doubleValue());
           teamToLoad.setBaStat(jso.getJsonNumber("baStat").doubleValue());
           teamToLoad.setEraStat(jso.getJsonNumber("eraStat").doubleValue());
           teamToLoad.setHrStat(jso.getJsonNumber("hrStat").intValue());
           teamToLoad.setkStat(jso.getJsonNumber("kStat").intValue());
           teamToLoad.setrStat(jso.getJsonNumber("rStat").intValue());
           teamToLoad.setRbiStat(jso.getJsonNumber("hrStat").intValue());
           teamToLoad.setSbStat(jso.getJsonNumber("sbStat").intValue());
           teamToLoad.setSvStat(jso.getJsonNumber("svStat").intValue());
           teamToLoad.setwStat(jso.getJsonNumber("wStat").doubleValue());
           teamToLoad.setWhipStat(jso.getJsonNumber("whipStat").doubleValue());
           teamToLoad.setPlayersNeeded(jso.getJsonNumber("playersNeeded").intValue());
           teamToLoad.setTotalPoints(jso.getJsonNumber("totalPoints").intValue());
           
           JsonArray startingPlayerItemsArray = jso.getJsonArray("startingPlayers");
           
           for(int j = 0; j < startingPlayerItemsArray.size(); j++) {
           
               JsonObject startingPlayersjso = startingPlayerItemsArray.getJsonObject(j); 
               
               
               if(startingPlayersjso.getString("firstName").contentEquals("null")) {
                   teamToLoad.getStartingPlayers()[j] = null;   
               }
               else {
               
                    teamToLoad.getStartingPlayers()[j] = new Player();
                     
                    teamToLoad.getStartingPlayers()[j].setProTeam(startingPlayersjso.getString("proTeam"));
                     teamToLoad.getStartingPlayers()[j].setFantasyTeam(startingPlayersjso.getString("fantasyTeam"));
                      teamToLoad.getStartingPlayers()[j].setFirstName(startingPlayersjso.getString("firstName"));
                       teamToLoad.getStartingPlayers()[j].setLastName(startingPlayersjso.getString("lastName"));
                        teamToLoad.getStartingPlayers()[j].setContract(startingPlayersjso.getString("contract"));
                         teamToLoad.getStartingPlayers()[j].setPosition(startingPlayersjso.getString("position"));
                         teamToLoad.getStartingPlayers()[j].setPositions(startingPlayersjso.getString("positions"));
                          teamToLoad.getStartingPlayers()[j].setValue(startingPlayersjso.getJsonNumber("value").doubleValue());
                           teamToLoad.getStartingPlayers()[j].setSalary(startingPlayersjso.getJsonNumber("salary").doubleValue());
                           
                    teamToLoad.getStartingPlayers()[j].setDraftNumber(startingPlayersjso.getInt("draftNumber"));
                    teamToLoad.getStartingPlayers()[j].setBirthYear(startingPlayersjso.getInt("birthYear"));
                    teamToLoad.getStartingPlayers()[j].setBirthNation(startingPlayersjso.getString("birthNation"));
                    teamToLoad.getStartingPlayers()[j].setNotes(startingPlayersjso.getString("notes"));
              
               teamToLoad.getStartingPlayers()[j].setStat1(startingPlayersjso.getInt("stat1"));
               teamToLoad.getStartingPlayers()[j].setStat2(startingPlayersjso.getInt("stat2"));
               teamToLoad.getStartingPlayers()[j].setStat3(startingPlayersjso.getInt("stat3"));
               teamToLoad.getStartingPlayers()[j].setStat4(startingPlayersjso.getJsonNumber("stat4").doubleValue());
               teamToLoad.getStartingPlayers()[j].setStat5(startingPlayersjso.getJsonNumber("stat5").doubleValue());
               
             
                   
               }
        
           }
              draftToLoad.getTeams().add(teamToLoad);
           
           JsonArray reserveTeamItemsArray = jso.getJsonArray("reservePlayers");
           
           for(int j = 0; j < reserveTeamItemsArray.size(); j++) {
           
               JsonObject reserverPlayersJso = reserveTeamItemsArray.getJsonObject(j); 
               
               
               if(reserverPlayersJso.getString("firstName").contentEquals("null")) {
                   teamToLoad.getReservePlayers()[j] = null;   
               }
               else {
               
                    teamToLoad.getReservePlayers()[j] = new Player();
                     
                    teamToLoad.getReservePlayers()[j].setProTeam(reserverPlayersJso.getString("proTeam"));
                     teamToLoad.getReservePlayers()[j].setFantasyTeam(reserverPlayersJso.getString("fantasyTeam"));
                      teamToLoad.getReservePlayers()[j].setFirstName(reserverPlayersJso.getString("firstName"));
                       teamToLoad.getReservePlayers()[j].setLastName(reserverPlayersJso.getString("lastName"));
                        teamToLoad.getReservePlayers()[j].setContract(reserverPlayersJso.getString("contract"));
                         teamToLoad.getReservePlayers()[j].setPosition(reserverPlayersJso.getString("position"));
                         teamToLoad.getReservePlayers()[j].setPositions(reserverPlayersJso.getString("positions"));
                          teamToLoad.getReservePlayers()[j].setValue(reserverPlayersJso.getJsonNumber("value").doubleValue());
                           teamToLoad.getReservePlayers()[j].setSalary(reserverPlayersJso.getJsonNumber("salary").doubleValue());
                           
                    teamToLoad.getReservePlayers()[j].setDraftNumber(reserverPlayersJso.getInt("draftNumber"));
                    teamToLoad.getReservePlayers()[j].setBirthYear(reserverPlayersJso.getInt("birthYear"));
                    teamToLoad.getReservePlayers()[j].setBirthNation(reserverPlayersJso.getString("birthNation"));
                    teamToLoad.getReservePlayers()[j].setNotes(reserverPlayersJso.getString("notes"));
              
               teamToLoad.getReservePlayers()[j].setStat1(reserverPlayersJso.getInt("stat1"));
               teamToLoad.getReservePlayers()[j].setStat2(reserverPlayersJso.getInt("stat2"));
               teamToLoad.getReservePlayers()[j].setStat3(reserverPlayersJso.getInt("stat3"));
               teamToLoad.getReservePlayers()[j].setStat4(reserverPlayersJso.getJsonNumber("stat4").doubleValue());
               teamToLoad.getReservePlayers()[j].setStat5(reserverPlayersJso.getJsonNumber("stat5").doubleValue());
               
             
                   
               }
        
           }
           
           
           }
     
        
        
        
        // NOW LOAD THE COURSE
    
    } 
    
    public JsonArray makePlayerJsonArray(Players players) {
        
        JsonArrayBuilder jsb = Json.createArrayBuilder();
            
        for (Player p : players) {
                jsb.add(this.makePlayerJsonObject(p));
            }
        
        JsonArray jA = jsb.build();
        
        return jA;
        
    }
    
    
    public JsonArray makePlayersJsonArray(Player[] players) {
        
        JsonArrayBuilder jsb = Json.createArrayBuilder();
            
        for (Player p : players) {
            if(p != null) {
                jsb.add(this.makePlayerJsonObject(p));
            }
            
            else {
                  JsonObject playerJsonObject = Json.createObjectBuilder()
                          .add("firstName", "null")
                          .build();
                  
                  jsb.add(playerJsonObject);
            
            
            }
            
            }
        
        JsonArray jA = jsb.build();
        
        return jA;
        
    }
    
    public JsonArray makeTeamJsonArray(ArrayList<Team> teams) {
    
     JsonArrayBuilder jsb = Json.createArrayBuilder();
            
        for (Team t : teams) {
                jsb.add(this.makeTeamJsonObject(t));
            }
        
        JsonArray jA = jsb.build();
        
        return jA;
    
    }
    
    
    public JsonObject makeTeamJsonObject(Team team) {
    
       JsonArray startingPlayers = makePlayersJsonArray(team.getStartingPlayers());
       JsonArray reservePlayers = makePlayersJsonArray(team.getReservePlayers());
         
       
        JsonObject teamJsonObject = Json.createObjectBuilder()
                .add("name", team.getName())
                .add("owner", team.getOwner())
                .add("funds", team.getFunds())
                .add("rank", team.getRank())
                .add("startingPlayers", startingPlayers)
                .add("reservePlayers", reservePlayers)
                
                .add("baStat", team.getBaStat())
                .add("eraStat", team.getEraStat())
                .add("hrStat", team.getHrStat())
                .add("kStat", team.getKStat())
                .add("rStat", team.getRStat())
                .add("rbiStat", team.getRbiStat())
                .add("sbStat", team.getSbStat())
                .add("svStat", team.getSvStat())
                .add("wStat", team.getWStat())
                .add("whipStat", team.getWhipStat())
                .add("playersNeeded", team.getPlayersNeeded())
                .add("totalPoints", team.getTotalPoints())
                
                
                .build();
    
        return teamJsonObject;
    }
    
   
    public JsonObject makePlayerJsonObject(Player player) {
          
        JsonObject playerJsonObject = Json.createObjectBuilder()
                                                   .add("proTeam", player.getProTeam())
                                                   .add("fantasyTeam", player.getFantasyTeam() == null ? "null" : player.getFantasyTeam())
                                                   .add("firstName", player.getFirstName())
                                                   .add("lastName", player.getLastName())
                                                   .add("contract", player.getContract() == null ? "null" : player.getContract())
                                                   .add("position", player.getPosition() == null ? "null" : player.getPosition())
                                                   .add("positions", player.getPositions())
                                                   .add("value", player.getValue())
                                                   .add("salary", player.getSalary())
                                                   .add("draftNumber", player.getDraftNumber())
                                                   .add("birthYear", player.getBirthYear())
                                                   .add("birthNation", player.getBirthNation())
                                                   .add("notes", player.getNotes())
                
                                                   .add("stat1", player.getStat1())
                                                   .add("stat2", player.getStat2())    
                                                   .add("stat3", player.getStat3())
                                                   .add("stat4", player.getStat4())
                                                   .add("stat5", player.getStat5())
               
                                                   .build();
        return playerJsonObject;
    
    }
            
            
    // LOADS AN ARRAY OF A SPECIFIC NAME FROM A JSON FILE AND
    // RETURNS IT AS AN ArrayList FULL OF THE DATA FOUND
    private ArrayList<String> loadArrayFromJSONFile(String jsonFilePath, String arrayName) throws IOException {
        
        ArrayList<String> items = new ArrayList();
        JsonObject jsonObject = loadJSONFile(jsonFilePath);
        JsonArray jsonArray = jsonObject.getJsonArray(arrayName);
        
        for (JsonValue jsonValue : jsonArray) {
            items.add(jsonValue.toString());
        }
        
        return items;
    }
    
    /**
     * Loads Players object from  string ArrayList 
     * @param items: ArrayList to be loaded
     * @return list of Players loaded from ArrayList
     */
    public Players loadPlayers(String playerType, String filePath, String[] statsToLoad) {
       
        Players players = new Players(playerType);
       
        JsonObject jsonObject = null;
        try {
             jsonObject = loadJSONFile(filePath);
        } catch (IOException ex) {
            Logger.getLogger(JsonDraftFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       JsonArray jsonPlayersArray = jsonObject.getJsonArray(playerType);
       
       // Thse will temporarily hold data to build a new player object
        String team = null;
        String firstName = null;
        String lastName = null;
        String positions = null;
        ArrayList<Statistic> stats = new ArrayList<Statistic>(); // TODO make stats efficient 
        int birthYear = 0;
        String birthNation = null;
        String notes = null;
       
       for(int i = 0; i < jsonPlayersArray.size(); i++) {
           
           JsonObject jsonPlayer = jsonPlayersArray.getJsonObject(i);
           
           // get player data from json object
           team = jsonPlayer.getString("TEAM");
           firstName = jsonPlayer.getString("FIRST_NAME");
           lastName = jsonPlayer.getString("LAST_NAME");
           notes = jsonPlayer.getString("NOTES");
           birthYear = Integer.parseInt(jsonPlayer.getString("YEAR_OF_BIRTH"));
           birthNation = jsonPlayer.getString("NATION_OF_BIRTH");
          
           // special condition for pitcher conditions
           if(playerType.equalsIgnoreCase("Pitchers")){
           positions = "P";
           }
           else {
           positions = jsonPlayer.getString("QP");}
           
           for(String stat: statsToLoad) {
             stats.add(new Statistic(stat, Double.parseDouble((jsonPlayer.getString(stat)))));
           }
           
           // create new player and add to player list
           players.add(new Player(team, firstName, lastName, positions, new 
           ArrayList<Statistic>(stats), birthYear, birthNation, notes));
           stats.clear();   
       }
       
     return players;
    }
    
}
