package main.json.admin.placementtest.edit;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PlacementSentenceJson extends main.json.admin.placementtest.abs.AbstractPlacementSentenceJson {

    @Getter
    @Setter
    private String id;

    public PlacementSentenceJson() {
        super();
    }

}
