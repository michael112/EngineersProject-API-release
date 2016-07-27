package main.model;

import java.io.Serializable;

import org.hibernate.id.IdentifierGenerator;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.HibernateException;

import com.eaio.uuid.UUID;

public class UuidGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
        if (obj == null) {
            throw new HibernateException(new NullPointerException());
        }
        else if( !( obj instanceof ModelInterface ) ) {
            throw new HibernateException("Invalid model: not implementing " + ModelInterface.class.getName());
        }
        else {
            ModelInterface model = (ModelInterface) obj;
            if( ( model.getId() != null ) && ( !( model.getId().equals("")) ) ) {
                return model.getId();
            }
            else return UuidGenerator.newUUID();
        }
    }

    public static String newUUID() {
        return new UUID().toString();
    }
}