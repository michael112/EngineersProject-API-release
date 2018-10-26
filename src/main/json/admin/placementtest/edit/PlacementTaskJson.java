package main.json.admin.placementtest.edit;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PlacementTaskJson extends main.json.admin.placementtest.abs.AbstractPlacementTaskJson {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Set<PlacementSentenceJson> sentences;

    public PlacementTaskJson() {
        super();
        this.sentences = new HashSet<>();
    }

}
