package de.tutous.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import de.tutous.spring.boot.conf.AppAuditorAware;
import de.tutous.spring.boot.conf.AppCorsConf;
import de.tutous.spring.boot.conf.AppMainConf;
import de.tutous.spring.boot.conf.AppSecurityAdapterConf;
import de.tutous.spring.boot.conf.AppStartupConf;

@SpringBootApplication
@Import(value =
{ //
        AppStartupConf.class, //
        AppMainConf.class, //
        AppCorsConf.class, //
        AppAuditorAware.class, //
        AppSecurityAdapterConf.class })
public class Application extends SpringBootServletInitializer
{

    public static void main(String[] args) throws Exception
    {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(Application.class);
    }

}
