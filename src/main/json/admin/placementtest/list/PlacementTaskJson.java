package main.json.admin.placementtest.list;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PlacementTaskJson {

    @Getter
    private String id;

    @Getter
    private String command;

    @Getter
    private Set<PlacementSentenceJson> sentences;

    public void addSentence(PlacementSentenceJson sentence) {
        this.sentences.add(sentence);
    }

    public PlacementTaskJson(String id, String command) {
        super();
        this.id = id;
        this.command = command;
        this.sentences = new HashSet<>();
    }

}
