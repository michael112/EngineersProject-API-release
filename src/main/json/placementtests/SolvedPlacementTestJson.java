package main.json.placementtests;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class SolvedPlacementTestJson {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Set<SolvedPlacementTaskJson> tasks;

}
