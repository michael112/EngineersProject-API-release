package main.json.admin.placementtest.abs;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class AbstractPlacementTestJson {

    @Getter
    @Setter
    private String languageID;

    public AbstractPlacementTestJson() {
        super();
    }

}
