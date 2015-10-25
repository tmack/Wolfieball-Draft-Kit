package wdk.data;

import java.util.ArrayList;


/**
 * Represents a collection of players
 * a) Can represent all the players of a draft
 * b) Can represent the roster of a team
 * @author Terrell Mack
 */
public class Players extends ArrayList<Player> {
    
    String type;
    
    public Players() {
        type = null;
    }
    
    public Players(String type) {
        this.type = type;
    }
    
   
}
