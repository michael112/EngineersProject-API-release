package main.json.admin.placementtest.abs;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AbstractPlacementTaskJson {

    @Getter
    @Setter
    private String command;

    public AbstractPlacementTaskJson() {
        super();
    }

}
