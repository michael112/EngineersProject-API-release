package main.json.placementtests;

import lombok.Getter;
import lombok.Setter;

import main.model.placementtest.PlacementAnswer;

public class SolvedPlacementSentenceJson {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private PlacementAnswer answer;

}
