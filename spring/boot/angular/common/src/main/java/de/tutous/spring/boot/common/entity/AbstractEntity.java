package de.tutous.spring.boot.common.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;
import org.springframework.data.util.ProxyUtils;

import de.tutous.spring.boot.common.bo.BusinessObject;
import de.tutous.spring.boot.common.bo.BusinessObjectProxy;

@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> implements Persistable<ID>
{

    @Transient
    public boolean isNew()
    {
        return Objects.isNull(getId());
    }

    @Override
    public boolean equals(Object obj)
    {
        obj = BusinessObjectProxy.getEntity(obj);
        if (null == obj)
        {
            return false;
        }

        if (this == obj)
        {
            return true;
        }

        if (!getClass().equals(ProxyUtils.getUserClass(obj)))
        {
            return false;
        }

        AbstractEntity<?> that = (AbstractEntity<?>) obj;

        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    @Override
    public int hashCode()
    {

        int hashCode = 17;

        hashCode += null == getId() ? 0 : getId().hashCode() * 31;

        return hashCode;
    }

    /**
     * to util???
     * 
     * @param <T>
     * @param collection
     * @return
     */
    protected <T> Set<T> check(Set<T> collection)
    {
        if (Objects.isNull(collection))
        {
            return new HashSet<T>();
        }
        return collection;
    }

    /**
     * Create a safe {@code BusinessObject} based on this entity. </br>
     * A {@code BusinessObject} supply only setter and are supported by a proxy.
     * 
     * @param <BO>
     * @param <E>
     * @param interfaceClass
     * @return
     */
    protected <BO extends BusinessObject<BO, ID>, E extends AbstractEntity<ID>> BO toSafeBO(Class<BO> interfaceClass)
    {
        return BusinessObjectProxy.toSafeBO(interfaceClass, this);
    }

}
