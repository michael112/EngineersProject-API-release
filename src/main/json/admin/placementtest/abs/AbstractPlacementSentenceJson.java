package main.json.admin.placementtest.abs;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import main.model.placementtest.PlacementAnswer;

@EqualsAndHashCode
public class AbstractPlacementSentenceJson {

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

    public AbstractPlacementSentenceJson() {
        super();
        this.answers = new HashSet<>();
    }

}
