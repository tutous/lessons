package de.tutous.spring.boot.common.exc;

import org.springframework.http.HttpStatus;

import de.tutous.spring.boot.common.entity.AbstractEntity;

public class BusinessObjectProxyRuntimeException extends ApplicationRuntimeException
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public BusinessObjectProxyRuntimeException(AbstractEntity<?> entity, Throwable exc)
    {
        super(exc, new ErrorCode<String>()
        {
            @Override
            public HttpStatus getHttpStatus()
            {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }

            @Override
            public String getId()
            {
                return "0500";
            }

            @Override
            public String getMessage(String[] args)
            {
                return "no access by entity: " + entity.getClass().getName();
            }

        }, new String[] {});
    }

}