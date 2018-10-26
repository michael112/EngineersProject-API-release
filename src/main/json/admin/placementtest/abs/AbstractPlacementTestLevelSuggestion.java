package main.json.admin.placementtest.abs;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AbstractPlacementTestLevelSuggestion {

    @Getter
    @Setter
    private String courseLevelName;

    @Getter
    @Setter
    private double points;

    public AbstractPlacementTestLevelSuggestion() {
        super();
    }

}
