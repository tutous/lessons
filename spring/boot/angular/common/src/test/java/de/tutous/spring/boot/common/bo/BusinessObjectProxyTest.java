package de.tutous.spring.boot.common.bo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import de.tutous.spring.boot.common.bo.BusinessObject;
import de.tutous.spring.boot.common.bo.BusinessObjectProxy;
import de.tutous.spring.boot.common.entity.AbstractEntity;
import de.tutous.spring.boot.common.entity.Entities;

public class BusinessObjectProxyTest
{

    @Test
    public void testToSafeBO()
    {

        Collection<ChildBO> childs = new ArrayList<ChildBO>();
        childs.add(new ChildEntity("child 1"));

        ParentBO parentBO = new ParentEntity(childs).toSafeBO();

        parentBO.getChilds().forEach(childBO -> {
            assertThat(childBO.getName()).isEqualTo("child 1");
            Entities.toEntity(childBO, ChildEntity.class, entity -> {
                assertThat(entity).isNotNull();
            });
        });

    }

    public interface ParentBO extends BusinessObject<ParentBO, Integer>
    {
        public Collection<ChildBO> getChilds();

    }

    public interface ChildBO extends BusinessObject<ChildBO, Integer>
    {

        public String getName();

    }

    public static class ParentEntity extends AbstractEntity<Integer> implements ParentBO
    {

        private Integer id;
        private Collection<ChildBO> childs;

        public ParentEntity(Collection<ChildBO> childs)
        {
            super();
            this.id = null;
            this.childs = childs;
        }

        protected ParentEntity(int id, Collection<ChildBO> childs)
        {
            super();
            this.id = id;
            this.childs = childs;
        }

        @Override
        public Integer getId()
        {
            return id;
        }

        public Collection<ChildBO> getChilds()
        {
            return childs;
        }

        @Override
        public ParentBO toSafeBO()
        {
            return BusinessObjectProxy.toSafeBO(ParentBO.class, this);
        }

    }

    public static class ChildEntity extends AbstractEntity<Integer> implements ChildBO
    {

        private Integer id;
        private String name;

        public ChildEntity(String name)
        {
            super();
            this.id = null;
            this.name = name;
        }

        protected ChildEntity(int id, String name)
        {
            super();
            this.id = id;
            this.name = name;
        }

        @Override
        public Integer getId()
        {
            return id;
        }

        @Override
        public String getName()
        {
            return name;
        }

        @Override
        public ChildBO toSafeBO()
        {
            return BusinessObjectProxy.toSafeBO(ChildBO.class, this);
        }

    }

}
