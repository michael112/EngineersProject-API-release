package main.json.placementtests;

import java.util.Set;

import lombok.Getter;

import main.model.placementtest.PlacementTask;

public class PlacementTestJson {

    @Getter
    private String language;

    @Getter
    private Set<PlacementTask> tasks;

    public PlacementTestJson(String language, Set<PlacementTask> tasks) {
        this.language = language;
        this.tasks = tasks;
    }

}
