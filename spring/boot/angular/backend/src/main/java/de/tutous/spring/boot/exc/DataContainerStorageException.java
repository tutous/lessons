package de.tutous.spring.boot.exc;

public class DataContainerStorageException extends Exception
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public DataContainerStorageException(String message, Throwable exc)
    {
        super(message, exc);
    }

}
