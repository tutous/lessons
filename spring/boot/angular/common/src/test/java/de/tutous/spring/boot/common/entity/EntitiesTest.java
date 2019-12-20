package de.tutous.spring.boot.common.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import de.tutous.spring.boot.common.bo.BusinessObject;
import de.tutous.spring.boot.common.entity.AbstractEntity;
import de.tutous.spring.boot.common.entity.Entities;
import de.tutous.spring.boot.common.exc.EntityNotFoundRuntimeException;
import de.tutous.spring.boot.common.exc.ErrorCode;
import de.tutous.spring.boot.common.stream.StreamSupplier;

public class EntitiesTest
{

    private ErrorCode<String> errorCode = new ErrorCode<String>()
    {

        @Override
        public String getId()
        {
            return "001";
        }

        @Override
        public String getMessage(String[] args)
        {
            return "error";
        }

        @Override
        public HttpStatus getHttpStatus()
        {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    };

    @Test
    public void testGet()
    {
        AbstractEntity<String> entity = Entities.get(Optional.of(new AbstractEntity<String>()
        {

            @Override
            public String getId()
            {
                return "1";
            }
        }), errorCode, new String[] {});

        assertThat(entity.getId()).isEqualTo("1");
    }

    @Test
    public void testGetError()
    {
        try
        {
            Entities.get(Optional.empty(), errorCode, new String[] {});
            fail("EntityNotFoundRuntimeException expected");
        }
        catch (EntityNotFoundRuntimeException exc)
        {

        }
    }

    @Test
    public void testToEntity()
    {

        final AtomicReference<TestEntity> testEntity = new AtomicReference<EntitiesTest.TestEntity>();
        Entities.toEntity(new TestEntity("name").toSafeBO(), TestEntity.class, entity -> {
            testEntity.set(entity);
        });
        assertThat(testEntity.get().getName()).isEqualTo("name");

    }

    @Test
    public void testToEntities()
    {

        final AtomicReference<TestEntity> testEntity = new AtomicReference<EntitiesTest.TestEntity>();
        Entities.toEntities(Arrays.asList(new TestEntity("name").toSafeBO()).stream(), TestEntity.class, entity -> {
            testEntity.set(entity);
        });
        assertThat(testEntity.get().getName()).isEqualTo("name");

    }

    @Test
    public void testToSafeBOs()
    {

        final AtomicReference<TestBO> testBO = new AtomicReference<EntitiesTest.TestBO>();
        Entities.toSafeBOs(Arrays.asList(new TestEntity("name")), TestBO.class).forEach(entity -> {
            testBO.set(entity);
        });
        assertThat(testBO.get().getName()).isEqualTo("name");
    }

    @Test
    public void testFindRemoved()
    {

        StreamSupplier<TestBO> spActual = () -> Arrays.asList(//
                new TestEntity(1, "name1").toSafeBO(), //
                new TestEntity(2, "name2").toSafeBO()).stream();

        StreamSupplier<TestBO> spNew1 = () -> Arrays.asList(//
                new TestEntity(1, "name1").toSafeBO(), //
                new TestEntity(2, "name2").toSafeBO()).stream();

        StreamSupplier<TestBO> spNew2 = () -> Arrays.asList(//
                new TestEntity(1, "name1").toSafeBO()).stream();

        Collection<TestBO> findRemoved1 = Entities.findRemoved(spActual, spNew1).collect(Collectors.toList());
        assertThat(findRemoved1.size()).isEqualTo(0);

        List<TestBO> findRemoved2 = Entities.findRemoved(spActual, spNew2).collect(Collectors.toList());
        assertThat(findRemoved2.size()).isEqualTo(1);
        assertThat(findRemoved2.get(0).getName()).isEqualTo("name2");

    }

    public interface TestBO extends BusinessObject<TestBO, Integer>
    {

        public String getName();

    }

    public static class TestEntity extends AbstractEntity<Integer> implements TestBO
    {

        private Integer id;
        private String name;

        public TestEntity(String name)
        {
            super();
            this.id = null;
            this.name = name;
        }

        protected TestEntity(int id, String name)
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

        public TestBO toSafeBO()
        {
            return toSafeBO(TestBO.class);
        }

    }

}
