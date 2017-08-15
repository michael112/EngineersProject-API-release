package main.json.placementtests;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlacementTestResultJson {
    // dołożyć maxResult

    @Getter
    private String placementTestID;

    @Getter
    private String placementTestResultID;

    @Getter
    private Double result;

    public PlacementTestResultJson(String placementTestID) {
        this.placementTestID = placementTestID;
    }

    public PlacementTestResultJson(String placementTestID, String placementTestResultID, Double result) {
        this(placementTestID);
        this.placementTestResultID = placementTestResultID;
        this.result = result;
    }

}
