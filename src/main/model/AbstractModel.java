package main.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractModel<PK extends Serializable> {

    @Getter
    @Setter
    private PK id;

}
