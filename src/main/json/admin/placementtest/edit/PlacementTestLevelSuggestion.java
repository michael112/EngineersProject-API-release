package main.json.admin.placementtest.edit;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PlacementTestLevelSuggestion extends main.json.admin.placementtest.abs.AbstractPlacementTestLevelSuggestion {

    @Getter
    @Setter
    private String id;

    public PlacementTestLevelSuggestion() {
        super();
    }

}
