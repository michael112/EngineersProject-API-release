package main.json.response;

import java.util.Set;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.placementtests.PlacementTestListJson;

public class PlacementTestListResponseJson extends ResponseJson {

    @Getter
    private Set<PlacementTestListJson> placementTestListJson;

    public PlacementTestListResponseJson(Set<PlacementTestListJson> placementTestListJson, String message, HttpStatus status) {
        this.placementTestListJson = placementTestListJson;
        this.message = message;
        this.setSuccess(status);
    }

}
