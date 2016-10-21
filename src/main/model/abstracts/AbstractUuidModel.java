package main.model.abstracts;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Access;
import javax.persistence.AccessType;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import main.constants.validationconstants.ValidationConstants;

@MappedSuperclass
@Access(AccessType.FIELD)
public class AbstractUuidModel extends AbstractModel<String> {

    @Pattern(regexp=ValidationConstants.UUID_REGEX, message = "ID.invalid")
    @Size(max = 36, message = "ID.length")

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
