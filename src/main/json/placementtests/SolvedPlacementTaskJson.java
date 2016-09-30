package main.json.placementtests;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public class SolvedPlacementTaskJson {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Set<SolvedPlacementSentenceJson> sentences;

    @Override
    public boolean equals(Object otherObj) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            SolvedPlacementTaskJson other = (SolvedPlacementTaskJson) otherObj;
            if( !( this.getId().equals(other.getId()) ) ) return false;
            if( this.getSentences().size() != other.getSentences().size() ) return false;
            java.util.List<SolvedPlacementSentenceJson> thisSentences = new java.util.ArrayList<>(this.getSentences());
            java.util.List<SolvedPlacementSentenceJson> otherSentences = new java.util.ArrayList<>(other.getSentences());
            for( int i = 0; i < this.getSentences().size(); i++ ) {
                if( !( thisSentences.get(i).equals(otherSentences.get(i)) ) ) return false;
            }
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
