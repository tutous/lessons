package de.tutous.spring.boot.conf;

import java.util.Optional;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.tutous.spring.boot.common.session.SessionSupportContext;
import de.tutous.spring.boot.domain.DataContainerEntity;
import de.tutous.spring.boot.repository.DataContainerRepo;

@Profile("dc-validation-test")
@TestConfiguration()
public class DataContainerServiceValidationConf
{

    static
    {
        SessionSupportContext.set("test0123456789");
    }

    @Bean
    @Primary
    public DataContainerRepo creatDataContainerRepo()
    {
        return new DataContainerRepoTestImpl();
    }

    @Repository
    @Transactional(propagation = Propagation.REQUIRED)
    public static class DataContainerRepoTestImpl implements DataContainerRepo
    {

        private DataContainerRepo mockDataContainerRepo = Mockito.mock(DataContainerRepo.class);

        public DataContainerRepo getMockDataContainerRepo()
        {
            return mockDataContainerRepo;
        }

        @Override
        public Optional<DataContainerEntity> findByName(String name)
        {
            return mockDataContainerRepo.findByName(name);
        }

        public <S extends DataContainerEntity> S save(S entity)
        {
            return mockDataContainerRepo.save(entity);
        }

        public <S extends DataContainerEntity> Iterable<S> saveAll(Iterable<S> entities)
        {
            return mockDataContainerRepo.saveAll(entities);
        }

        public Iterable<DataContainerEntity> findAllByFilter(String name)
        {
            return mockDataContainerRepo.findAllByFilter(name);
        }

        public Optional<DataContainerEntity> findById(Long id)
        {
            return mockDataContainerRepo.findById(id);
        }

        public boolean existsById(Long id)
        {
            return mockDataContainerRepo.existsById(id);
        }

        public Iterable<DataContainerEntity> findAll()
        {
            return mockDataContainerRepo.findAll();
        }

        public Iterable<DataContainerEntity> findAllById(Iterable<Long> ids)
        {
            return mockDataContainerRepo.findAllById(ids);
        }

        public long count()
        {
            return mockDataContainerRepo.count();
        }

        public void deleteById(Long id)
        {
            mockDataContainerRepo.deleteById(id);
        }

        public void delete(DataContainerEntity entity)
        {
            mockDataContainerRepo.delete(entity);
        }

        public void deleteAll(Iterable<? extends DataContainerEntity> entities)
        {
            mockDataContainerRepo.deleteAll(entities);
        }

        public void deleteAll()
        {
            mockDataContainerRepo.deleteAll();
        }

    }
}
