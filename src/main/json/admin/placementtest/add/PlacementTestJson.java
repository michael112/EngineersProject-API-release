package main.json.admin.placementtest.add;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PlacementTestJson extends main.json.admin.placementtest.abs.AbstractPlacementTestJson {

    @Getter
    @Setter
    private Set<PlacementTaskJson> tasks;

    @Getter
    @Setter
    private Set<PlacementTestLevelSuggestion> suggestedLevels;

    public PlacementTestJson() {
        super();
        this.tasks = new HashSet<>();
        this.suggestedLevels = new HashSet<>();
    }

}
