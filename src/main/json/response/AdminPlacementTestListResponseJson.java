package main.json.response;

import java.util.Set;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.admin.placementtest.list.PlacementTestJson;

@EqualsAndHashCode(callSuper = true)
public class AdminPlacementTestListResponseJson extends AbstractResponseJson {

    @Getter
    private Set<PlacementTestJson> placementTests;

    public AdminPlacementTestListResponseJson(Set<PlacementTestJson> placementTests, String message, HttpStatus status) {
        super(message, status);
        this.placementTests = placementTests;
    }

}
