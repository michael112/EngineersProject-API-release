package main.json.placementtests;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class SolvedPlacementTestJson {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Set<SolvedPlacementTaskJson> tasks;

    /*
    @Override
    public boolean equals(Object otherObj) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            SolvedPlacementTestJson other = (SolvedPlacementTestJson) otherObj;
            if( !( this.getId().equals(other.getId()) ) ) return false;
            if( this.getTasks().size() != other.getTasks().size() ) return false;
            java.util.List<SolvedPlacementTaskJson> thisTasks = new java.util.ArrayList<>(this.getTasks());
            java.util.List<SolvedPlacementTaskJson> otherTasks = new java.util.ArrayList<>(other.getTasks());
            for( int i = 0; i < this.getTasks().size(); i++ ) {
                if( !( thisTasks.get(i).equals(otherTasks.get(i)) ) ) return false;
            }
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
    */
}
