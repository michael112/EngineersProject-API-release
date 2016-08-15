package main.json.placementtests;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public class SolvedPlacementTaskJson {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Set<SolvedPlacementSentenceJson> sentences;

}
