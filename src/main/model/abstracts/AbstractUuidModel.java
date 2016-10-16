package main.model.abstracts;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Access;
import javax.persistence.AccessType;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode

@MappedSuperclass
@Access(AccessType.FIELD)
public class AbstractUuidModel extends AbstractModel<String> {

    @Id
    @GenericGenerator(name = "uuid_generator", strategy = "main.model.UuidGenerator")
    @GeneratedValue(generator = "uuid_generator")
    @Column(name="id")
    @Access(AccessType.PROPERTY)
    @Override
    public String getId() {
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
