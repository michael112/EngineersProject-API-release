package main.json.admin.placementtest.add;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PlacementTaskJson {

    @Getter
    @Setter
    private String command;

    @Getter
    @Setter
    private Set<PlacementSentenceJson> sentences;

    public PlacementTaskJson() {
        super();
        this.sentences = new HashSet<>();
    }

}
