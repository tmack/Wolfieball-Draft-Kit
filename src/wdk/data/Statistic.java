package wdk.data;

/** 
 * Represents the statistic of a player 
 * statType: The name of a player statistic 
 * statValue: the value of a player statistic 
 * @author Terrell Mack
 */
public class Statistic {
    
    private String statType;
    private double statValue;
    
    public Statistic() {
        statType = null;
        statValue = 0.0;
    }
    
    public Statistic(String statType) {
        this.statType = statType;
        statValue = 0.0;
    }
    
    public Statistic(String statType, double statValue) {
        this.statType = statType;
        this.statValue = statValue;
    }
    
   public String getStatType() {
       return statType;
   }
   
   public void setStatType(String statType) {
      this.statType = statType;
   }
   
   public double getStatValue() {
       return statValue;
   }
   
   public void setStatValue(double statValue) {
       this.statValue = statValue;
   }

    @Override
    public String toString() {
        return "Statistic{" + "statType=" + statType + ", statValue=" + statValue + '}';
    }
    
}
