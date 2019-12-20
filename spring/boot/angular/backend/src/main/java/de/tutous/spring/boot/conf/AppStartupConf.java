package de.tutous.spring.boot.conf;

import java.sql.SQLException;
import java.util.Objects;

import org.h2.tools.Server;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.tutous.spring.boot.common.exc.ApplicationStartupRuntimeException;

@Configuration
public class AppStartupConf implements ApplicationListener<ApplicationStartingEvent>
{

    private static Server server;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseaServer()
    {
        if (Objects.isNull(server))
        {
            try
            {
                server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
            }
            catch (SQLException e)
            {
                throw new ApplicationStartupRuntimeException("create tcp H2 server failed ", e);
            }
        }
        return server;
    }

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event)
    {

    }

}
