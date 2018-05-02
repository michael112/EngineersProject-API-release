package main.json.admin.placementtest.list;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PlacementTestJson {

    @Getter
    private String id;

    @Getter
    private String languageName;

    @Getter
    private String languageID;

    @Getter
    private Set<PlacementTaskJson> tasks;

    public void addTask(PlacementTaskJson task) {
        this.tasks.add(task);
    }

    public PlacementTestJson(String id, String languageID, String languageName) {
        super();
        this.id = id;
        this.languageID = languageID;
        this.languageName = languageName;
        this.tasks = new HashSet<>();
    }

}
