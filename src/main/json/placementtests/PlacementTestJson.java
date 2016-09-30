package main.json.placementtests;

import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.model.placementtest.PlacementTask;

@EqualsAndHashCode
public class PlacementTestJson {

    @Getter
    private String language;

    @Getter
    private Set<PlacementTask> tasks;

    public PlacementTestJson(String language, Set<PlacementTask> tasks) {
        this.language = language;
        this.tasks = tasks;
    }

    /*
    @Override
    public boolean equals(Object otherObj) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            PlacementTestJson other = (PlacementTestJson) otherObj;
            if( !( this.getLanguage().equals(other.getLanguage()) ) ) return false;
            if( this.getTasks().size() != other.getTasks().size() ) return false;
            java.util.List<PlacementTask> thisTasks = new java.util.ArrayList<>(this.getTasks());
            java.util.List<PlacementTask> otherTasks = new java.util.ArrayList<>(other.getTasks());
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
