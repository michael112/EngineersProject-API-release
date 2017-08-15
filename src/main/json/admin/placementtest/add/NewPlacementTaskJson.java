package main.json.admin.placementtest.add;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NewPlacementTaskJson {

    @Getter
    @Setter
    private String command;

    @Getter
    @Setter
    private Set<NewPlacementSentenceJson> sentences;

    public NewPlacementTaskJson() {
        super();
        this.sentences = new HashSet<>();
    }

}
