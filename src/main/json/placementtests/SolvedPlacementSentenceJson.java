package main.json.placementtests;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.model.placementtest.PlacementAnswer;

@EqualsAndHashCode
public class SolvedPlacementSentenceJson {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private PlacementAnswer answer;

}
