package wdk.data;

import java.util.ArrayList;
import java.util.Collections;
import wdk.compare.BAStatComparator;
import wdk.compare.EraStatComparator;
import wdk.compare.HRStatComparator;
import wdk.compare.KStatComparator;
import wdk.compare.RBIStatComparator;
import wdk.compare.RStatComparator;
import wdk.compare.SBStatComparator;
import wdk.compare.SVStatComparator;
import wdk.compare.WHIPStatComparator;
import wdk.compare.WStatComparator;

/**
 * Represents a fantasy draft  
 * @author Terrell Mack
 */
public class Draft {
    
    private String name;
    private ArrayList<Players> roster;
    private Players fullRoster;
    private ArrayList<Team> teams;
    private Players draftList;
    private int draftCount;

    public Draft() {
        this.name = null;
        this.roster = null;
        this.fullRoster = null;
        this.draftList = new Players();
        this.teams = null;
        this.draftCount = 1;
    }

    public Draft(String name) {
        this();
        this.name = name;
    }

    public Draft(String name, ArrayList<Players> roster, ArrayList<Team> teams) {
        this();
        this.name = name;
        this.roster = roster;
        this.teams = teams;
    }
    
    public void clearRoster() {
        roster = new ArrayList<Players>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Players> getRoster() {
        return roster;
    }

    public void setRoster(ArrayList<Players> roster) {
        this.roster = roster;
    }

    public void setFullRoster(Players fullRoster) {
        this.fullRoster = fullRoster;
    }

    public Players getFullRoster() {
      
        fullRoster = new Players();
        
        for(int i = 0; i < roster.size(); i++) {
            for(int j = 0; j < roster.get(i).size(); j++) {
                fullRoster.add(roster.get(i).get(j));
            }
        }
        return fullRoster;
    }
   
    public Players getPlayers(String position) {
    
        Players players = new Players();
        switch (position) {
            case "All":
                return getFullRoster();
            case "P":
                return roster.get(0);
            case "U":
                return roster.get(1);
            default:
                for(int i = 0; i < roster.get(1).size();i++) {
                   
                    String[] positions =  roster.get(1).get(i).getPositions().split("_");
                    
                    for(int j = 0; j < positions.length; j++) {
                        
                        // a middle fielder can be either a 2nd baseman or short stop
                        if(position.contentEquals("MI")) {
                            if(positions[j].contentEquals("2B") || positions[j].contentEquals("SS")){
                                players.add(roster.get(1).get(i));}
                        }
                        
                        // a center fielder can be either a 1st or 3rd baseman
                        else if(position.contentEquals("CI")) {
                            if(positions[j].contentEquals("1B") || positions[j].contentEquals("3B")){
                                players.add(roster.get(1).get(i));}
                        }
                        
                        else {
                            if(positions[j].contentEquals(position)) {
                                players.add(roster.get(1).get(i));}
                        }
                    }
                }
                break;
        }
 
        return players;
    }
    
    public ArrayList<Team> getTeams() {
        return teams;
    }
    
    public void removePlayer(Player playerToRemove) {
        
       for(int i = 0; i < roster.size(); i++) {
           
           for(int j = 0; j < roster.get(i).size(); j++) {
             
               if(roster.get(i).get(j).equals(playerToRemove)) {

                 roster.get(i).remove(j);
             
              
                  this.getFullRoster();
                 
                 return;
             }
            }
        }

        }
    
    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
    
    public Team getTeam(String name) {
        
        for (Team team : teams) {
            if (team.getName().contentEquals(name)) {
                return team;
            }
        }
      
        return null;
    }
   
    public boolean hasTeam(String name) {
        
        for (Team team : teams) {
            if (team.getName().contentEquals(name)) {
                return true;
            }
        }
      
        return false;
    }

    public void removeTeam(String name) {
        
        for(int i = 0; i < teams.size(); i++) {
            if(teams.get(i).getName().contentEquals(name)) {
                teams.remove(i);
                return;
           }         
        }
    }

    public int getDraftCount() {
        return draftCount;
    }

    public void setDraftCount(int draftCount) {
        this.draftCount = draftCount;
    }

    public Players getDraftedPlayers() {
        return draftList;
    }

    public void setDraftedPlayers(Players draftList) {
        this.draftList = draftList;
    }
    
    public void reloadDraftList() {
    
        draftList = new Players();
        
        for(Team team : teams) {
            
            for(Player player : team.getStartingPlayers()) {
                
                if(player != null && player.getContract().contentEquals("S2")) {
                  
                    draftList.add(player);
                
                }
                
            }
        
        }
        
        Collections.sort(draftList);
        reloadDraftNumbers();
        
    }
    
    public void reloadDraftNumbers() {
        
        for(int i = 0; i < draftList.size() ; i++) {
            draftList.get(i).setDraftNumber(i+1);
        
        }
        
        draftCount = draftList.size()+1;
    
    }
    
    public boolean draftPlayer() {
        
       if(!this.allStartingLineupsFull()) {
        
       for(Team team : teams) {
          
           if(!team.isFull()) {  
       
                if(!team.isStartingFull()) {
                    
                    Players playersForPosition = this.getPlayers(team.getOpenPositions().get(0));
              
                    // get player of open position (if no players return false) 
                    if(playersForPosition.size() < 1) {
                        return false;
                    }
                
                    // get first avaiable player for position
                    Player draftedPlayer = playersForPosition.get(0);
            
                    // update variables of player
                    draftedPlayer.setFantasyTeam(team.getName());
                    draftedPlayer.setPosition(team.getOpenPositions().get(0));
                    draftedPlayer.setContract("S2");
                    draftedPlayer.setSalary(1);
                    draftedPlayer.setDraftNumber(draftCount);

                    // add player to team
                    team.setFunds(team.getFunds()-1);

                    // remove player from draft
                    if(team.addStartingPlayer(draftedPlayer, team.getOpenPositions().get(0))) {
                      //  this.getDraftedPlayers().add(draftedPlayer);
                        this.removePlayer(draftedPlayer); 
                    } else {
                        return false;
                    }
                    this.draftCount++;
                
                    return true;
       
           } 

       }
          
               
            }
       
       } else if(!this.allTeamsFull()) {
           
             if(this.getFullRoster().size() < 1) {     
                return false;
              } 
             
           for(Team team : teams) {
            // ADD A PLAYER TO RESERVE LIST
               
            if(!team.isReserveFull()) {
                // update variables of getFullRoster().get(0)player
                Player draftedPlayer = getFullRoster().get(0);
                    draftedPlayer.setFantasyTeam(team.getName());
                    //getFullRoster().get(0).setPosition(getFullRoster().get(0).getP);
                    draftedPlayer.setContract("X");
                    draftedPlayer.setSalary(1);
                    if(team.addReservePlayer(draftedPlayer)) {   
                        this.removePlayer(getFullRoster().get(0));
                        getFullRoster();
                        
                        return true;
                    } else {
                        return false;
                    }
        }
           }  
       }
       return true;
   
       
    }
    
    public void incrementDraftCount() {
        this.draftCount++;
    }
    
    public void reloadTeamsStats() {
    
        for(Team team : teams) {
            team.reloadStats();
        }  
    
    }
    
    public boolean allTeamsFull() {
    
        for(Team team : teams) {
        if(!team.isFull()) {
            return false;
        }
        }    
        return true;
    }
    
      public boolean allStartingLineupsFull() {
    
        for(Team team : teams) {
        if(!team.isStartingFull()) {
            return false;
        }
        }    
        return true;
    }
    
    @Override
    public String toString() {
        return "Draft{" + "name=" + name + ", roster=" + roster + ", teams=" + teams + '}';
    }
    
    public void reloadTotalPoints() {
        
        this.reloadTeamsStats();
        
       Collections.sort(teams, new RStatComparator());
       
       int rank = 1;       
       
       //RSTAT
       for(int i = 0; i < teams.size(); i++) {
         
           teams.get(i).setTotalPoints(0);
           
          int statPoints = 10 * (teams.size() - (rank - 1));
          teams.get(i).setTotalPoints(teams.get(i).getTotalPoints() + statPoints);
        
          rank++;
          if(i < teams.size() - 1) {
               
               if(teams.get(i).getRStat() == teams.get(i+1).getRStat()) {
                   rank--;
               
               }
           }    
       }

       //HRSTAT
        rank = 1;     
       Collections.sort(teams, new HRStatComparator());
       for(int i = 0; i < teams.size(); i++) {
           
          int statPoints = 10 * (teams.size() - (rank - 1));
          teams.get(i).setTotalPoints(teams.get(i).getTotalPoints() + statPoints);
         
          
          rank++;
          if(i < teams.size() - 1) {
               
               if(teams.get(i).getHrStat()== teams.get(i+1).getHrStat()) {
                   rank--;
               
               }
           }    
       }

       
       //RBISTAT
        rank = 1;  
       Collections.sort(teams, new RBIStatComparator());
       for(int i = 0; i < teams.size(); i++) {
           
          int statPoints = 10 * (teams.size() - (rank - 1));
          teams.get(i).setTotalPoints(teams.get(i).getTotalPoints() + statPoints);
          
          rank++;
          if(i < teams.size() - 1) {
               
               if(teams.get(i).getRbiStat()== teams.get(i+1).getRbiStat()) {
                   rank--;
               
               }
           }    
       }

       //SBSTAT
        rank = 1;  
       Collections.sort(teams, new SBStatComparator());
       for(int i = 0; i < teams.size(); i++) {
           
          int statPoints = 10 * (teams.size() - (rank - 1));
          teams.get(i).setTotalPoints(teams.get(i).getTotalPoints() + statPoints);
        
          rank++;
          if(i < teams.size() - 1) {
               
               if(teams.get(i).getSbStat()== teams.get(i+1).getSbStat()) {
                   rank--;
               
               }
           }    
       }

       
       //BASTAT
        rank = 1;  
       Collections.sort(teams, new BAStatComparator());
       for(int i = 0; i < teams.size(); i++) {
           
          int statPoints = 10 * (teams.size() - (rank - 1));
          teams.get(i).setTotalPoints(teams.get(i).getTotalPoints() + statPoints);
         
          rank++;
          if(i < teams.size() - 1) {
               
               if(teams.get(i).getBaStat()== teams.get(i+1).getBaStat()) {
                   rank--;
               
               }
           }    
       }
 
       
       //WSTAT
        rank = 1;  
       Collections.sort(teams, new WStatComparator());
       for(int i = 0; i < teams.size(); i++) {
           
          int statPoints = 10 * (teams.size() - (rank - 1));
          teams.get(i).setTotalPoints(teams.get(i).getTotalPoints() + statPoints);
          
          rank++;
          if(i < teams.size() - 1) {
               
               if(teams.get(i).getWStat() == teams.get(i+1).getWStat()) {
                   rank--;
               
               }
           }    
       }
    
       //SVSTAT
        rank = 1;  
       Collections.sort(teams, new SVStatComparator());
       for(int i = 0; i < teams.size(); i++) {
           
          int statPoints = 10 * (teams.size() - (rank - 1));
          teams.get(i).setTotalPoints(teams.get(i).getTotalPoints() + statPoints);
     
          rank++;
          if(i < teams.size() - 1) {
               
               if(teams.get(i).getSvStat()== teams.get(i+1).getSvStat()) {
                   rank--;
               
               }
           }    
       }

       
          //KSTAT
        rank = 1;  
       Collections.sort(teams, new KStatComparator());
       for(int i = 0; i < teams.size(); i++) {
           
          int statPoints = 10 * (teams.size() - (rank - 1));
          teams.get(i).setTotalPoints(teams.get(i).getTotalPoints() + statPoints);
         
          rank++;
          if(i < teams.size() - 1) {
               
               if(teams.get(i).getKStat()== teams.get(i+1).getKStat()) {
                   rank--;
               
               }
           }    
       }

       
          //ERA
        rank = 1;  
       Collections.sort(teams, new EraStatComparator());
       for(int i = 0; i < teams.size(); i++) {
           
          int statPoints = 10 * (teams.size() - (rank - 1));
          teams.get(i).setTotalPoints(teams.get(i).getTotalPoints() + statPoints);
            
          rank++;
          if(i < teams.size() - 1) {
               
               if(teams.get(i).getEraStat()== teams.get(i+1).getEraStat()) {
                   rank--;
               
               }
           }    
       }

       
          //Whip
        rank = 1;  
       Collections.sort(teams, new WHIPStatComparator());
       for(int i = 0; i < teams.size(); i++) {
           
          int statPoints = 10 * (teams.size() - (rank - 1));
          teams.get(i).setTotalPoints(teams.get(i).getTotalPoints() + statPoints);
  
          rank++;
          if(i < teams.size() - 1) {
               
               if(teams.get(i).getWhipStat()== teams.get(i+1).getWhipStat()) {
                   rank--;
               
               }
           }    
       }

    
    }
    
    public void calcEstimateValues() {
        getFullRoster();
        
        // get total money left
        int moneyLeft = 0;
        int spotsLeft = 0;
        int hittersLeft = 0;
        int pitchersLeft = 0;
        
        for(Team team : teams) {
            moneyLeft+=team.getFunds();
            hittersLeft+=team.hittersNeeded();
            pitchersLeft+=team.pitchersNeeded();
        }
        
        int stat1 = 0;
        int stat2 = 0;
        int stat3 = 0;
        double stat4 = 0;
        double stat5 = 0;
        // getaverage hitter 
        
        for(Player hitters : roster.get(1)) {
            stat1+=hitters.getStat1();
             stat2+=hitters.getStat2();
              stat3+=hitters.getStat3();
               stat4+=hitters.getStat4();
                stat5+=hitters.getStat5();
       
        }
        double averagePitcherSalary = 0;
        double averageHitterSalary = 0;
        if(pitchersLeft > 0) {
            averagePitcherSalary = moneyLeft / (2 * pitchersLeft);
        } else {
            averagePitcherSalary = 1;
        }
        
        if(hittersLeft > 0) {
            averageHitterSalary = moneyLeft / (2 * hittersLeft);
        } else {
            averageHitterSalary = 1;
        }
       
        double averageHitterStats = (stat1+stat2+stat3+stat4+(stat5*100)) / roster.get(1).size();
        double playerStats = 0;
          for(Player hitters : roster.get(1)) {
   
            stat1=hitters.getStat1();
             stat2=hitters.getStat2();
              stat3=hitters.getStat3();
               stat4=hitters.getStat4();
                stat5=hitters.getStat5();
                
        playerStats = (stat1+stat2+stat3+stat4+(stat5*100));
        //System.out.println(playerStats+":"+averageHitterStats);
        if(playerStats != 0) {
            playerStats/=averageHitterStats;
            playerStats*=averageHitterSalary;
            int value = (int) playerStats;
            if(value < 1) {
            hitters.setValue(1);
            } else {
            hitters.setValue(value);
            }
        
        }
        else {
            hitters.setValue(1);
        }
    
       
        }
          stat1=0;
          stat2=0;
          stat3=0;
          stat4=0;
          stat5=0;
          
          
            for(Player pitchers : roster.get(0)) {
            stat1+=pitchers.getStat1();
             stat2+=pitchers.getStat2();
              stat3+=pitchers.getStat3();
               stat4+=pitchers.getStat4();
                stat5+=pitchers.getStat5();
       
        }
          
        double averagePitcherStats = (stat1+stat2+(stat3/10)+(stat4*10)+(stat5*10)) / roster.get(0).size();
       for(Player pitchers : roster.get(0)) {
   
            stat1=pitchers.getStat1();
             stat2=pitchers.getStat2();
              stat3=pitchers.getStat3();
               stat4=pitchers.getStat4();
                stat5=pitchers.getStat5();
                
        playerStats = (stat1+stat2+(stat3/10)+(stat4*10)+(stat5*10));
        
        if(playerStats != 0) {
            playerStats/=averagePitcherStats;
            playerStats*=averagePitcherSalary;
            int value = (int) playerStats;
            if(value < 1) {
            pitchers.setValue(1);
            } else {
            pitchers.setValue(value);
            }
        
        }
        else {
            pitchers.setValue(1);
        }
       
        }
              
    }
    
    public void clearTeams() {
        teams.clear();
        
    }
    
    public Players getProTeamPlayers(String teamName) {
        Players playerList = new Players();
        this.getFullRoster();
        
        for(Player player : fullRoster) {
        if(player.getProTeam().contentEquals(teamName))
            playerList.add(player);
        }
        
      return playerList;
        
    }
   
 }
