package wdk.data;

import java.util.ArrayList;

/**
 * Represents a sports team
 * @author Terrell Mack
 */
public class Team {
    
    private final String[] STARTING_POSITIONS_ORDER = {"C", "C", 
        "1B", "CI", "3B", "2B", "MI", "SS", 
        "OF", "OF", "OF", "OF", "OF", "U", 
        "P", "P", "P", "P", "P", "P", "P", "P" ,"P"};
    
    private final int MAX_STARTING_PLAYERS = 23;
    private final int MAX_RESERVE_PLAYERS = 8;
    private int playersNeeded;
    private int totalPoints;
    
    private String name;
    private String owner;
    private double funds;
    private double rank;
    private Player[] startingPlayers;
    private Player[] reservePlayers;
    
    private int rStat;
    private int hrStat;
    private int rbiStat;
    private int sbStat;
    private double baStat;
    private double wStat;
    private int svStat;
    private int kStat;
    private double eraStat;
    private double whipStat;

    public Team() {
        this.name = null;
        this.owner = null;
        this.funds = 260;
        this.rank = 0.0;
        totalPoints = 0;
        
        playersNeeded = MAX_STARTING_PLAYERS;
        startingPlayers = new Player[MAX_STARTING_PLAYERS];
        reservePlayers = new Player[MAX_RESERVE_PLAYERS];
        
       for(int i = 0; i < startingPlayers.length; i++) {
           startingPlayers[i] = null;
           
       }
       
       rStat = 0;
       hrStat = 0;
       rbiStat = 0;
       sbStat = 0;
       baStat = 0;
       
       wStat = 0;
       svStat = 0;
       kStat = 0;
       eraStat = 0;
       whipStat = 0;
        
        
        
    }

    public Team(String name, String owner) {
       this();
        this.name = name;
        this.owner = owner;
      
    }

    public Team(String name, String owner, double funds, double rank, 
        Player[] startingPlayers, Player[] reservePlayers) {
        this();
        this.name = name;
        this.owner = owner;
        this.funds = funds;
        this.rank = rank;
        this.startingPlayers = startingPlayers;
        this.reservePlayers = reservePlayers;
    }

    public void removePlayer(Player playerToRemove) {
        
        for(int i = 0; i < startingPlayers.length; i++) {
       
            if(startingPlayers[i] != null 
                && startingPlayers[i].getFirstName().contentEquals(playerToRemove.getFirstName())
                && startingPlayers[i].getLastName().contentEquals(playerToRemove.getLastName())) {   
            startingPlayers[i] = null;
        playersNeeded++;
        return;
            }
        }
        
        for(int i = 0; i < reservePlayers.length; i++) {
        if(reservePlayers[i] != null 
                && reservePlayers[i].getFirstName().contentEquals(playerToRemove.getFirstName())
                && reservePlayers[i].getLastName().contentEquals(playerToRemove.getLastName()))
        {
            reservePlayers[i] = null;
        
        return;
        }
        }
        
    
    }
        public boolean isStartingFull() {
         
        for(Player player : startingPlayers) {
           if(player == null) {
               return false;
           }        
        }
        
        return true;
    }
    
    public boolean isReserveFull() {
  
        for(Player player : reservePlayers) {
           if(player == null) {
               return false;
           }        
        }
        
        return true;
    
    }
    
    
    // returns if there are any empty positions in the starting and reserve player rosters
    public boolean isFull() {
        return isStartingFull() && isReserveFull();
    }
    
   
    /******************************************************************************
                                GETTERS AND SETTERS 
    *******************************************************************************/
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public Player[] getStartingPlayers() {
        return startingPlayers;
    }
    
   public Players getStartingPlayersList() {
   
    Players playersList = new Players();
    
        for (Player startingPlayer : startingPlayers) {
            if (startingPlayer != null) {
                playersList.add(startingPlayer);
            }
        }
    return playersList;
    }
   
    public Players getReservePlayersList() {
   
    Players playersList = new Players();
    
        for (Player reservePlayer : reservePlayers) {
            if (reservePlayer != null) {
                playersList.add(reservePlayer);
            }
        }
    return playersList;
    }

    public void setStartingPlayers(Player[] startingPlayers) {
        this.startingPlayers = startingPlayers;
    }

    public Player[] getReservePlayers() {
        return reservePlayers;
    }
    
    public int hittersNeeded() {
        int hittersNeeded = 0;
        for(int i = 0; i < startingPlayers.length; i++) {
        if(!STARTING_POSITIONS_ORDER[i].contentEquals("P")) {
            hittersNeeded++;
        }
            
        }
        return hittersNeeded;
    }
    
      public int pitchersNeeded() {
        int pitchersNeeded = 0;
        for(int i = 0; i < startingPlayers.length; i++) {
        if(STARTING_POSITIONS_ORDER[i].contentEquals("P")) {
            pitchersNeeded++;
        }
            
        }
        return pitchersNeeded;
    }
    
    public boolean addStartingPlayer(Player playerToAdd, String position) { 
        
        for(int i = 0; i < startingPlayers.length; i++) {
            
            if(position.contentEquals(STARTING_POSITIONS_ORDER[i]) && startingPlayers[i] == null) {
                startingPlayers[i] = playerToAdd;
                playersNeeded--;
                return true;
            }
            
            if(STARTING_POSITIONS_ORDER[i].contentEquals("CI") && startingPlayers[i] == null
               && (position.contentEquals("1B") || position.contentEquals("3B"))) {
              startingPlayers[i] = playerToAdd;
               playersNeeded--;
                return true;
            }
            
              if(STARTING_POSITIONS_ORDER[i].contentEquals("MI") && startingPlayers[i] == null
               && (position.contentEquals("2B") || position.contentEquals("SS"))) {
              startingPlayers[i] = playerToAdd;
               playersNeeded--;
                return true;
            }
              
            if(STARTING_POSITIONS_ORDER[i].contentEquals("U") && startingPlayers[i] == null)
              // && !position.contentEquals("P")) 
            {
              // playerToAdd.setPosition("U");
               startingPlayers[i] = playerToAdd;
               playersNeeded--;
                return true;
            }
        }
        
        return false;
    }

    public int getPlayersNeeded() {
        return playersNeeded;
    }

    public void setPlayersNeeded(int playersNeeded) {
         this.playersNeeded = playersNeeded;
    }
    


    public void setReservePlayers(Player[] reservePlayers) {
        this.reservePlayers = reservePlayers;
    }
    
    public boolean addReservePlayer(Player playerToAdd) {
        
       for(int i = 0; i < reservePlayers.length; i++) {
           if(reservePlayers[i] == null) {
            reservePlayers[i] = playerToAdd;
            return true;
           }
       
       }
        return false;
    }
    
    public ArrayList<String> getOpenPositions() {
        ArrayList<String> openPositions = new ArrayList<String>();
        
        for(int i = 0; i < startingPlayers.length; i++) {
            
            if(startingPlayers[i] == null) {
                if(STARTING_POSITIONS_ORDER[i].contentEquals("CI")) {
                 openPositions.add("1B");
                  openPositions.add("3B");
                }
                if(STARTING_POSITIONS_ORDER[i].contentEquals("MI")) {
                     openPositions.add("2B");
                      openPositions.add("SS");
                
                }
                if(STARTING_POSITIONS_ORDER[i].contentEquals("U")) {
                 // openPositions.add("1B");
                  //openPositions.add("3B");
                  //openPositions.add("2B");
                  //openPositions.add("SS");
                  //openPositions.add("C");
                  //openPositions.add("OF");
                openPositions.add("U");
                }
               
                openPositions.add(STARTING_POSITIONS_ORDER[i]);
            }
        }
        
        for(int i = 0; i < openPositions.size(); i++) {
            String positionName = openPositions.get(i);
            
            for(int j = 0; j < openPositions.size(); j++) {
                if(i != j && openPositions.get(i).contentEquals(openPositions.get(j))) {
                 openPositions.remove(j);
                 i = 0;
                
                }
            
                }
        
        }
        
        
        return openPositions;
     }
    
    public void reloadStats() {
       
       rStat = 0;
       hrStat = 0;
       rbiStat = 0;
       sbStat = 0;
       baStat = 0;
       
       wStat = 0;
       svStat = 0;
       kStat = 0;
       eraStat = 0;
       whipStat = 0;
        
        
        int pitcherCount = 0;
        int hitterCount = 0;
        
        for(Player player : startingPlayers) {
            
            if(player != null && player.getPosition().contentEquals("P")) {
            
              
               pitcherCount++;
               
               wStat += player.getStat1();
               svStat += player.getStat2();
               kStat += player.getStat3();
               eraStat += player.getStat4();
               whipStat += player.getStat5();
            
            } else if(player != null) {
               rStat += player.getStat1();
               hrStat += player.getStat2();
               rbiStat += player.getStat3();
               sbStat += player.getStat4();
               baStat += player.getStat5();
               hitterCount++;
            
            
            }
            
        
        }
        
        if(hitterCount > 0) {
               //rStat /= pitcherCount;
               //hrStat /= pitcherCount;
               //rbiStat /= pitcherCount;
             //  sbStat /= pitcherCount;
               baStat /= (double)hitterCount;
        
        }
        
        if(pitcherCount > 0) {
            
               //wStat /= (double)hitterCount;
               //svStat /= hitterCount;
               //kStat /= hitterCount;
               eraStat /= (double)pitcherCount;
                whipStat /= (double)pitcherCount;
        
        }
        
     
        
    
    }

    public int getRStat() {
        return rStat;
    }

    public void setrStat(int rStat) {
        this.rStat = rStat;
    }

    public int getHrStat() {
        return hrStat;
    }

    public void setHrStat(int hrStat) {
        this.hrStat = hrStat;
    }

    public int getRbiStat() {
        return rbiStat;
    }

    public void setRbiStat(int rbiStat) {
        this.rbiStat = rbiStat;
    }

    public int getSbStat() {
        return sbStat;
    }

    public void setSbStat(int sbStat) {
        this.sbStat = sbStat;
    }

    public double getBaStat() {
        return baStat;
    }

    public void setBaStat(double baStat) {
        this.baStat = baStat;
    }

    public double getWStat() {
        return wStat;
    }

    public void setwStat(double wStat) {
 
        this.wStat = wStat;
    }

    public int getSvStat() {
        return svStat;
    }

    public void setSvStat(int svStat) {
        this.svStat = svStat;
    }

    public int getKStat() {
        return kStat;
    }

    public void setkStat(int kStat) {
        this.kStat = kStat;
    }

    public double getEraStat() {
        return eraStat;
    }

    public void setEraStat(double eraStat) {
        this.eraStat = eraStat;
    }

    public double getWhipStat() {
        return whipStat;
    }

    public void setWhipStat(double whipStat) {
        this.whipStat = whipStat;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
    
    
    
    @Override
    public String toString() {
        return "Team{" + "name=" + name + ", owner=" + owner + ", funds=" + funds 
                + ", rank=" + rank + ", startingPlayers=" + startingPlayers 
                + ", reservePlayers=" + reservePlayers + '}';
    }
   
}
