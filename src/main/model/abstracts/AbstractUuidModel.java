package main.model.abstracts;

import main.model.UuidGenerator;

public class AbstractUuidModel extends AbstractModel<String> {

    public AbstractUuidModel() {
        this.setId(UuidGenerator.newUUID());
    }

}
