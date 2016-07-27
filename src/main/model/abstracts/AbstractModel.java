package main.model.abstracts;

import java.io.Serializable;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

import main.model.ModelInterface;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public abstract class AbstractModel<PK extends Serializable> implements ModelInterface<PK> {

    @Id
    @GenericGenerator(name = "uuid_generator", strategy = "main.model.UuidGenerator")
    @GeneratedValue(generator = "uuid_generator")
    @Column(name="id")
    @Getter
    @Setter
    private PK id;


    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

}
