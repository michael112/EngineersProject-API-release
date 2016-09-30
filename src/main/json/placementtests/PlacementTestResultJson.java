package main.json.placementtests;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
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

}
