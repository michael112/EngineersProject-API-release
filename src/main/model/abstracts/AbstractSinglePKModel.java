package main.model.abstracts;

import java.io.Serializable;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Access;
import javax.persistence.AccessType;

import org.hibernate.annotations.GenericGenerator;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractSinglePKModel<PK extends Serializable> extends AbstractModel<PK> {

    @Id
    @GenericGenerator(name = "string_id_generator", strategy = "main.model.StringIdGenerator")
    @GeneratedValue(generator = "string_id_generator")
    @Column(name="id")
    @Access(AccessType.PROPERTY)
    @Override
    public PK getId() {
        return super.getId();
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object otherObj) {
        return super.equals(otherObj);
    }

}
