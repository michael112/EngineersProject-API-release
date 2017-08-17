package main.json.placementtests;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlacementTestResultJson {

    @Getter
    private String placementTestID;

    @Getter
    private String placementTestResultID;

    @Getter
    private Double result;

    @Getter
    private Double maxResult;

    public PlacementTestResultJson(String placementTestID) {
        this.placementTestID = placementTestID;
    }

    public PlacementTestResultJson(String placementTestID, String placementTestResultID, Double result, Double maxResult) {
        this(placementTestID);
        this.placementTestResultID = placementTestResultID;
        this.result = result;
        this.maxResult = maxResult;
    }

}
