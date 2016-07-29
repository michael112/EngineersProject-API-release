package main.model.abstracts;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import main.model.ModelInterface;

public abstract class AbstractModel<PK extends Serializable> implements ModelInterface<PK> {

    @Getter
    @Setter
    private PK id;

    public AbstractModel() {}

}
