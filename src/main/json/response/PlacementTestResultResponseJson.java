package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class PlacementTestResultResponseJson extends AbstractResponseJson {

    @Getter
    private double result;

    @Getter
    private double maxResult;
    /*

    @Getter
    private SuggestedLevelJson suggestedLevel;

    */

    public PlacementTestResultResponseJson(double result, double maxResult, String message, HttpStatus status) {
        super(message, status);
        this.result = result;
        this.maxResult = maxResult;
    }

}
