package main.json.placementtests;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlacementTestResultJson {

    @Getter
    private String testID;

    @Getter
    private Double result;

    public PlacementTestResultJson(String testID) {
        this.testID = testID;
    }

    public PlacementTestResultJson(String testID, Double result) {
        this(testID);
        this.result = result;
    }

    @Override
    public boolean equals(Object otherObj) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            PlacementTestResultJson other = (PlacementTestResultJson) otherObj;
            if( !( this.getTestID().equals(other.getTestID()) ) ) return false;
            if( !( this.getResult().equals(other.getResult()) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
