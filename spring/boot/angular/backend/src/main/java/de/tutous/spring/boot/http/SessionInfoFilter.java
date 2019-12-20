package de.tutous.spring.boot.http;

import java.util.Optional;
import java.util.function.Supplier;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import de.tutous.spring.boot.common.session.HasSessionSupport;
import de.tutous.spring.boot.common.session.SessionInfo;
import de.tutous.spring.boot.common.session.SessionSupportContextFilter;

@Component
public class SessionInfoFilter extends SessionSupportContextFilter implements Filter, HasSessionSupport
{

    @Override
    public void doBeforeFilter(HttpServletRequest request)
    {
        // TODO: implement clear session !!!
        Optional<Supplier<SessionInfo>> supplierSessionInfo = get(SessionInfo.class);
        if (supplierSessionInfo.isPresent())
        {
            supplierSessionInfo.get().get().setCurrentRequestURI(request.getRequestURI());
            supplierSessionInfo.get().get().setCurrentRequestMethod(request.getMethod());
        }
        else
        {
            SessionInfo sessionInfo = new SessionInfo(request.getRequestURI(), request.getMethod());
            register(() -> sessionInfo, SessionInfo.class);
        }

    }

}
