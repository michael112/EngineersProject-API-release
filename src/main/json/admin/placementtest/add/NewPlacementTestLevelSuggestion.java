package main.json.admin.placementtest.add;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NewPlacementTestLevelSuggestion {

    @Getter
    @Setter
    private String courseLevelName;

    @Getter
    @Setter
    private double points;

}
