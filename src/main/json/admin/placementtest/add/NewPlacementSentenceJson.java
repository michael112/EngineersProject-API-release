package main.json.admin.placementtest.add;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import main.model.placementtest.PlacementAnswer;

@EqualsAndHashCode
public class NewPlacementSentenceJson {

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

    @Getter
    @Setter
    private double weight;

    public NewPlacementSentenceJson() {
        super();
        this.answers = new HashSet<>();
        this.setWeight(1);
    }

}
