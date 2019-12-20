package de.tutous.spring.boot.conf;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@Configuration
public class AppAuditorAware implements AuditorAware<String>
{

    /**
     * Find the user name for the attributes createdBy and modifiedOn. Later we can use our {@code UserEntity}.
     */
    public Optional<String> getCurrentAuditor()
    {

        Optional<User> optionalUser = Optional.ofNullable(SecurityContextHolder.getContext())
                /** Authentication */
                .map(SecurityContext::getAuthentication)
                /** isAuthenticated */
                .filter(Authentication::isAuthenticated)
                /** get Principal */
                .map(Authentication::getPrincipal)
                /** map to userdetails User */
                .map(User.class::cast);
        if (optionalUser.isPresent())
        {
            return Optional.of(optionalUser.get().getUsername());
        }
        else
        {
            return Optional.of("unknown");
        }
    }
}
