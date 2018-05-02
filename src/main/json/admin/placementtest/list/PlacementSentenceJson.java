package main.json.admin.placementtest.list;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;
import lombok.EqualsAndHashCode;

import main.model.placementtest.PlacementAnswer;

@EqualsAndHashCode
public class PlacementSentenceJson {

    @Getter
    private String id;

    @Getter
    private String prefix;

    @Getter
    private String suffix;

    @Getter
    private Set<PlacementAnswer> answers;

    public void addAnswer(PlacementAnswer answer) {
        this.answers.add(answer);
    }

    public PlacementSentenceJson(String id, String prefix, String suffix) {
        super();
        this.id = id;
        this.prefix = prefix;
        this.suffix = suffix;
        this.answers = new HashSet<>();
    }

}
