package main.json.response;

import java.util.Set;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.placementtests.PlacementTestListJson;

public class PlacementTestListResponseJson extends AbstractResponseJson {

    @Getter
    private Set<PlacementTestListJson> placementTestListJson;

    public PlacementTestListResponseJson(Set<PlacementTestListJson> placementTestListJson, String message, HttpStatus status) {
        super(message, status);
        this.placementTestListJson = placementTestListJson;
    }

}
