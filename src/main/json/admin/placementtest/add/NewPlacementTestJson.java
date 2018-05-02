package main.json.admin.placementtest.add;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NewPlacementTestJson {

    @Getter
    @Setter
    private String languageID;

    @Getter
    @Setter
    private Set<NewPlacementTaskJson> tasks;

    @Getter
    @Setter
    private Set<NewPlacementTestLevelSuggestion> suggestedLevels;

    public NewPlacementTestJson() {
        super();
        this.tasks = new HashSet<>();
        this.suggestedLevels = new HashSet<>();
    }

}
