package main.json.placementtests;

import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PlacementTestListJson {

    @Getter
    private String language;
    @Getter
    private Set<PlacementTestResultJson> test;

    public PlacementTestListJson(String language, Set<PlacementTestResultJson> tests) {
        this.language = language;
        this.test = tests;
    }

    /*
    @Override
    public boolean equals(Object otherObj) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            PlacementTestListJson other = (PlacementTestListJson) otherObj;
            if( !( this.getLanguage().equals(other.getLanguage()) ) ) return false;
            if( this.getTest().size() != other.getTest().size() ) return false;
            java.util.List<PlacementTestResultJson> thisTest = new java.util.ArrayList<>(this.getTest());
            java.util.List<PlacementTestResultJson> otherTest = new java.util.ArrayList<>(other.getTest());
            for( int i = 0; i < this.getTest().size(); i++ ) {
                if( !( thisTest.get(i).equals(otherTest.get(i)) ) ) return false;
            }
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
    */
}