 package wdk.data;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a sports team player
 * @author Terrell Mack
 */
public class Player implements Comparable<Player> {
    
    private String proTeam;
    private String fantasyTeam;
    private String firstName;
    private String lastName;
    private String contract;
    private String position;
    private String positions; // eligible playing position 
    private ArrayList<Statistic> stats; // grouping of players statistics
    private double value;
    private double salary;
    private int draftNumber;
    private int birthYear; 
    private String birthNation;
    private String notes; // where a user can keep notes on a player
    
    private int stat1;
    private int stat2;
    private int stat3;
    private double stat4;
    private double stat5;

    public Player() {
        proTeam = null;
        fantasyTeam = null;
        firstName = null;
        lastName = null;
        stats = null;
        birthYear = 0;
        birthNation = null;
        position = null;
        notes = null;   
        contract = null;
        value = 0.0;
        draftNumber = 0;
        salary = 0.0;

                
                
        stat1 = 0;
        stat2 = 0;
        stat3 = 0;
        stat4 = 0;
        stat5 = 0;
    }

    public Player(String firstName, String lastName) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        
    }
    
    public Player(String firstName, String lastName, String team, String positions) {
        this(); 
        this.firstName = firstName;
        this.lastName = lastName;
        this.proTeam = team;
        this.positions = positions;
        
    }

    public Player(String team, String firstName, String lastName, int birthYear, String birthNation) {
       this();
        this.proTeam = team;
        this.firstName = firstName;
        this.lastName = lastName;
        this.stats = null;
        this.value = 0.0; 
        this.birthYear = birthYear;
        this.birthNation = birthNation;
        this.notes = null;
    }

    public Player(String team, String firstName, String lastName, String positions, ArrayList<Statistic> stats, int birthYear, String birthNation, String notes) {
       this();
        this.proTeam = team;
        this.firstName = firstName;
        this.lastName = lastName;
        this.positions = positions;
        this.stats = stats;
        this.value = 0.0;
        this.birthYear = birthYear;
        this.birthNation = birthNation;
        this.notes = notes;
    }
    
    public Player(String team, String firstName, String lastName, String positions, ArrayList<Statistic> stats, double value, int birthYear, String birthNation, String notes) {
        this();
        this.proTeam = team;
        this.firstName = firstName;
        this.lastName = lastName;
        this.positions = positions;
        this.stats = stats;
        this.value = value;
        this.birthYear = birthYear;
        this.birthNation = birthNation;
        this.notes = notes;
    }

    public Player(String team, String firstName, String lastName, String position, String positions, 
            ArrayList<Statistic> stats, double value, int draftNumber, int birthYear, String birthNation, 
            String notes, int stat1, int stat2, int stat3, double stat4, double stat5) {
        this.proTeam = team;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.positions = positions;
        this.stats = stats;
        this.value = value;
        this.draftNumber = draftNumber;
        this.birthYear = birthYear;
        this.birthNation = birthNation;
        this.notes = notes;
        this.stat1 = stat1;
        this.stat2 = stat2;
        this.stat3 = stat3;
        this.stat4 = stat4;
        this.stat5 = stat5;
    }
    
    /******************************************************************************
                                GETTERS AND SETTERS 
    *******************************************************************************/
    public String getProTeam() {
        return proTeam;
    }

    public void setProTeam(String team) {
        this.proTeam = team;
    }

    public String getFantasyTeam() {
        return fantasyTeam;
    }

    public void setFantasyTeam(String fantasyTeam) {
        this.fantasyTeam = fantasyTeam;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPositions() {
        return positions;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    
    public ArrayList<String>  getPostions() {
        String[] positionsString = positions.split("_");
        
        ArrayList<String> positionsList = new ArrayList<String>();

        for(int i = 0; i < positionsString.length;i++) {
            positionsList.add(positionsString[i]);
        }
        return positionsList;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }
    
    

    public ArrayList<Statistic> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Statistic> stats) {
        this.stats = stats;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getBirthNation() {
        return birthNation;
    }

    public void setBirthNation(String birthNation) {
        this.birthNation = birthNation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getStat1() {
        return stat1;
    }

    public void setStat1(int stat1) {
        this.stat1 = stat1;
    }

    public int getStat2() {
        return stat2;
    }

    public void setStat2(int stat2) {
        this.stat2 = stat2;
    }

    public int getStat3() {
        return stat3;
    }

    public void setStat3(int stat3) {
        this.stat3 = stat3;
    }

    public double getStat4() {
        return stat4;
    }

    public void setStat4(double stat4) {
        this.stat4 = stat4;
    }

    public double getStat5() {
        return stat5;
    }

    public void setStat5(double stat5) {
        this.stat5 = stat5;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getDraftNumber() {
        return draftNumber;
    }

    public void setDraftNumber(int draftNumber) {
        this.draftNumber = draftNumber;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.proTeam);
        hash = 23 * hash + Objects.hashCode(this.firstName);
        hash = 23 * hash + Objects.hashCode(this.lastName);
        hash = 23 * hash + Objects.hashCode(this.positions);
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.value) ^ (Double.doubleToLongBits(this.value) >>> 32));
        hash = 23 * hash + this.birthYear;
        hash = 23 * hash + Objects.hashCode(this.birthNation);
        hash = 23 * hash + Objects.hashCode(this.notes);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (!Objects.equals(this.proTeam, other.proTeam)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.positions, other.positions)) {
            return false;
        }
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
            return false;
        }
        if (this.birthYear != other.birthYear) {
            return false;
        }
        if (!Objects.equals(this.birthNation, other.birthNation)) {
            return false;
        }
        if (!Objects.equals(this.notes, other.notes)) {
            return false;
        }
        return true;
    }
    
    
 
    @Override
    public String toString() {
        return "Player{" + "proTeam=" + proTeam + ", firstName=" + firstName + ", lastName=" + lastName + ", stats="  + ", birthYear=" + birthYear + ", birthNation=" + birthNation + ", notes=" + notes + '}';
    }

    @Override
    public int compareTo(Player o) {
        
        if(o instanceof Player) {
            Player playerToCompare = (Player)o;
           
            if(this.draftNumber > playerToCompare.getDraftNumber()) {
                return 1;
            }
            else if(this.draftNumber < playerToCompare.getDraftNumber()) {
                return -1;
            }
           
       
    }
        return 0;


    }

}
