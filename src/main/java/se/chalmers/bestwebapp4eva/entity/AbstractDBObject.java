package se.chalmers.bestwebapp4eva.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.MappedSuperclass;

/**
 * Base class for all objects that will be stored in the database
 *
 * @author simon
 */
@MappedSuperclass
public abstract class AbstractDBObject implements Serializable {

    protected AbstractDBObject() {
    }

    public abstract Long getId();

    public abstract void setId(long id);

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractDBObject other = (AbstractDBObject) obj;
        return Objects.equals(getId(), other.getId());
    }

}
