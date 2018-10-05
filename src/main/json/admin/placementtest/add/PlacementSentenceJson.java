package main.json.admin.placementtest.add;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import main.model.placementtest.PlacementAnswer;

@EqualsAndHashCode
public class PlacementSentenceJson {

    @Getter
    @Setter
    private String prefix;

    @Getter
    @Setter
    private String suffix;

    @Getter
    @Setter
    private Set<PlacementAnswer> answers;

    @Getter
    @Setter
    private String correctAnswer;

    public PlacementSentenceJson() {
        super();
        this.answers = new HashSet<>();
    }

}
