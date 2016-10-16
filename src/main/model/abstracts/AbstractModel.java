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

    @Override
    public int hashCode() {
        if( this.getId() != null ) return this.getId().hashCode();
        else return new Integer(0).hashCode();
    }

    @Override
    public boolean equals(Object otherObj) {
        if( !( this.getClass().getName().equals(otherObj.getClass().getName()) ) ) {
            return false;
        }
        else {
            AbstractModel other = this.getClass().cast(otherObj);
            return other.getId().equals(this.getId());
        }
    }

}
