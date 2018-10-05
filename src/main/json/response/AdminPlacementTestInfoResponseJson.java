package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.placementtest.list.PlacementTestJson;

public class AdminPlacementTestInfoResponseJson extends AbstractResponseJson {

    @Getter
    private PlacementTestJson placementTest;

    public AdminPlacementTestInfoResponseJson(PlacementTestJson placementTests, String message, HttpStatus status) {
        super(message, status);
        this.placementTest = placementTests;
    }

}
