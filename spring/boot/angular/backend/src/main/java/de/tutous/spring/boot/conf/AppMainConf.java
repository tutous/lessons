package de.tutous.spring.boot.conf;

import static de.tutous.spring.boot.conf.AppMainConf.BACKEND;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

import de.tutous.spring.boot.props.DcStorageProperties;
import de.tutous.spring.boot.service.StorageService;

@Configuration
// scans
@ComponentScan(basePackages =
{ //
        BACKEND + "controller", //
        BACKEND + "service", //
        BACKEND + "convert", //
        BACKEND + "exc", //
        BACKEND + "http" })
// HATEOAS
@EnableHypermediaSupport(type =
{ HypermediaType.HAL })
// JPA
@EntityScan(basePackages =
{ BACKEND + "domain" })
@EnableJpaAuditing
@EnableJpaRepositories(basePackages =
{ BACKEND + "repository" })
// Properties
@EnableConfigurationProperties(DcStorageProperties.class)
public class AppMainConf
{

    public static final String BACKEND = "backend.";

    private StorageService storageService;

    public AppMainConf(StorageService storageService)
    {
        this.storageService = storageService;
    }

    @Bean
    CommandLineRunner initApplication()
    {
        return (args) -> {
            storageService.init();
        };
    }

}
