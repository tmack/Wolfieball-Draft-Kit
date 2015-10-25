package wdk.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import wdk.data.Draft;
import wdk.data.Players;
import wdk.data.Team;
import wdk.file.JsonDraftFileManager;

/**
 * Handles the state of a Draft object
 * @author Terrell Mack
 */
public class DraftController {
    
    JsonDraftFileManager fileManager;
    
    public DraftController() {
    
        fileManager = new JsonDraftFileManager();
    
    }
    
    public Draft handleNewDraftRequest() {
    
        Draft newDraft = new Draft("Draft Name", new ArrayList<Players>(), new ArrayList<Team>());;
        
        Players newDraftPitchers = fileManager.loadPlayers("Pitchers",
               "data/Pitchers.json", 
               new String[]{"ER", "W", "SV","H","BB", "K", "IP"});
        
        setPitcherStats(newDraftPitchers);
        
        Players newDraftHitters = fileManager.loadPlayers("Hitters",
                "data/Hitters.json", 
                new String[]{"AB", "R", "H", "HR","RBI","SB"});
        
        setHitterStats(newDraftHitters);
        newDraft.getRoster().add(newDraftPitchers);
        newDraft.getRoster().add(newDraftHitters);
       
    return newDraft;
    }
    
    private void setPitcherStats(Players pitchers) {
    
        for(int i = 0; i < pitchers.size(); i++) {
            
        // Wins (W)
        pitchers.get(i).setStat1((int) pitchers.get(i).getStats().get(1).getStatValue());
        
        // Saves (SV)
        pitchers.get(i).setStat2((int) pitchers.get(i).getStats().get(2).getStatValue());
        
        // Strikeouts (K)
        pitchers.get(i).setStat3((int) pitchers.get(i).getStats().get(5).getStatValue());
        
        // Earned Run Average (ERA)
        if(pitchers.get(i).getStats().get(6).getStatValue() == 0) {
        pitchers.get(i).setStat4(0);
        }
        else {
        pitchers.get(i).setStat4(
               pitchers.get(i).getStats().get(0).getStatValue() * 9 //ER * 9
       / pitchers.get(i).getStats().get(6).getStatValue()); // / Inning pitched 
        }
        
        // WHIP
        
        if(pitchers.get(i).getStats().get(6).getStatValue() == 0){
        pitchers.get(i).setStat5(0);
        } else {
        pitchers.get(i).setStat5(
               (pitchers.get(i).getStats().get(4).getStatValue() //ER 
           +   pitchers.get(i).getStats().get(3).getStatValue())
                  /    pitchers.get(i).getStats().get(6).getStatValue());
        
        }
           
        }
    }
    
    private void setHitterStats(Players hitters) {
    
        for(int i = 0; i < hitters.size(); i++) {
        
        // Runs (R)
        hitters.get(i).setStat1((int) hitters.get(i).getStats().get(1).getStatValue());
        
        // Runs (HR)
        hitters.get(i).setStat2((int) hitters.get(i).getStats().get(2).getStatValue());
        
       // Runs Batted In (RBI)
        hitters.get(i).setStat3((int) hitters.get(i).getStats().get(4).getStatValue());
        
        // SB (SB)
        hitters.get(i).setStat4(hitters.get(i).getStats().get(5).getStatValue());
        
        // Batting Average (BA)
       
        if(hitters.get(i).getStats().get(0).getStatValue() == 0) {
        hitters.get(i).setStat5(0);
        } else {
        hitters.get(i).setStat5((int)hitters.get(i).getStats().get(2).getStatValue()
                /  hitters.get(i).getStats().get(0).getStatValue());
          // System.out.print(hitters.get(i).getStats().get(2).getStatValue() + " ");
          // System.out.print(hitters.get(i).getStats().get(0).getStatValue() + " ");
           //System.out.println(hitters.get(i).getStat5());
        }
        
      
        }
    }
    
}
