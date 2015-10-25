/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.compare;

import java.util.Comparator;
import wdk.data.Team;

/**
 *
 * @author Terrell Mack
 */
public class BAStatComparator implements Comparator<Team> {
    
    @Override
    public int compare(Team o1, Team o2) {
        if(o1.getBaStat() < o2.getBaStat()) {
        return 1;
        }
        
        if(o1.getBaStat() > o2.getBaStat()) {
        return -1;
        }
        return 0;
    }

    
}

