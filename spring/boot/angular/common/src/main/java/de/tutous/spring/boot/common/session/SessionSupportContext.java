package de.tutous.spring.boot.common.session;

import java.util.HashMap;
import java.util.Map;

public class SessionSupportContext
{

    private static Map<String, SessionSupport> map = new HashMap<String, SessionSupport>();

    private static ThreadLocal<SessionSupport> current = new ThreadLocal<SessionSupport>();

    public static void set(String sessionId)
    {
        synchronized (map)
        {
            if (!map.containsKey(sessionId))
            {
                map.put(sessionId, new SessionSupport());
            }
            current.set(map.get(sessionId));
        }
    }

    public static SessionSupport get()
    {
        return current.get();
    }
    
}
