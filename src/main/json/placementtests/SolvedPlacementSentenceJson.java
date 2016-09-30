package main.json.placementtests;

import lombok.Getter;
import lombok.Setter;

import main.model.placementtest.PlacementAnswer;

public class SolvedPlacementSentenceJson {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private PlacementAnswer answer;

    @Override
    public boolean equals(Object otherObj) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            SolvedPlacementSentenceJson other = (SolvedPlacementSentenceJson) otherObj;
            if( !( this.getId().equals(other.getId()) ) ) return false;
            if( !( this.getAnswer().equals(other.getAnswer()) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
