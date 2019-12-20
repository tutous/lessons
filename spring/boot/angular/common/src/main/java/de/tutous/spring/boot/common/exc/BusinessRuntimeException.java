package de.tutous.spring.boot.common.exc;

public class BusinessRuntimeException extends ApplicationRuntimeException
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public BusinessRuntimeException(ErrorCode<?> errorCode, String[] args)
    {
        super(errorCode, args);
    }

    public BusinessRuntimeException(Throwable cause, ErrorCode<?> errorCode, String[] args)
    {
        super(cause, errorCode, args);
    }
}
