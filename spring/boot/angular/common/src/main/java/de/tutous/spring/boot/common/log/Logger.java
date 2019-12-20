package de.tutous.spring.boot.common.log;

import static de.tutous.spring.boot.common.session.SessionSupportContext.get;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.LoggerFactory;
// import org.slf4j.Marker;

import de.tutous.spring.boot.common.session.SessionInfo;
import de.tutous.spring.boot.common.session.SessionSupport;

public interface Logger
{

    public static Logger getLogger(Class<?> clazz)
    {
        return new LoggerWrapper(LoggerFactory.getLogger(clazz));
    }

    static class LoggerWrapper implements Logger
    {

        private org.slf4j.Logger logger;

        private LoggerWrapper(org.slf4j.Logger logger)
        {
            this.logger = logger;
        }

        private String addId(boolean enabled, String value)
        {
            if (enabled)
            {
                SessionSupport sessionSupport = get();
                if (Objects.nonNull(sessionSupport))
                {
                    Optional<Supplier<SessionInfo>> info = sessionSupport.getSupplier(SessionInfo.class);
                    if (info.isPresent() && Objects.nonNull(info.get()) && Objects.nonNull(info.get().get()))
                    {
                        return String.valueOf(info.get().get().getUuid()).substring(24) + " - " + value;
                    }
                }
            }
            return value;
        }

        public String getName()
        {
            return logger.getName();
        }

        public boolean isTraceEnabled()
        {
            return logger.isTraceEnabled();
        }

        public void trace(String msg)
        {
            logger.trace(addId(isTraceEnabled(), msg));
        }

        public void trace(String format, Object arg)
        {
            logger.trace(addId(isTraceEnabled(), format), ToString.arg(arg));
        }

        public void trace(String format, Object arg1, Object arg2)
        {
            logger.trace(addId(isTraceEnabled(), format), ToString.arg(arg1), ToString.arg(arg2));
        }

        public void trace(String format, Object... arguments)
        {
            logger.trace(addId(isTraceEnabled(), format), ToString.args(arguments));
        }

        public void trace(String msg, Throwable t)
        {
            logger.trace(addId(isTraceEnabled(), msg), t);
        }

        public boolean isDebugEnabled()
        {
            return logger.isDebugEnabled();
        }

        public void debug(String msg)
        {
            logger.debug(addId(isDebugEnabled(), msg));
        }

        public void debug(String format, Object arg)
        {
            logger.debug(addId(isDebugEnabled(), format), ToString.arg(arg));
        }

        public void debug(String format, Object arg1, Object arg2)
        {
            logger.debug(addId(isDebugEnabled(), format), ToString.arg(arg1), ToString.arg(arg2));
        }

        public void debug(String format, Object... arguments)
        {
            logger.debug(addId(isDebugEnabled(), format), ToString.args(arguments));
        }

        public void debug(String msg, Throwable t)
        {
            logger.debug(addId(isDebugEnabled(), msg), t);
        }

        public boolean isInfoEnabled()
        {
            return logger.isInfoEnabled();
        }

        public void info(String msg)
        {
            logger.info(addId(isInfoEnabled(), msg));
        }

        public void info(String format, Object arg)
        {
            logger.info(addId(isInfoEnabled(), format), ToString.arg(arg));
        }

        public void info(String format, Object arg1, Object arg2)
        {
            logger.info(addId(isInfoEnabled(), format), ToString.arg(arg1), ToString.arg(arg2));
        }

        public void info(String format, Object... arguments)
        {
            logger.info(addId(isInfoEnabled(), format), ToString.args(arguments));
        }

        public void info(String msg, Throwable t)
        {
            logger.info(addId(isInfoEnabled(), msg), t);
        }

        public boolean isWarnEnabled()
        {
            return logger.isWarnEnabled();
        }

        public void warn(String msg)
        {
            logger.warn(addId(isWarnEnabled(), msg));
        }

        public void warn(String format, Object arg)
        {
            logger.warn(addId(isWarnEnabled(), format), arg);
        }

        public void warn(String format, Object... arguments)
        {
            logger.warn(addId(isWarnEnabled(), format), arguments);
        }

        public void warn(String format, Object arg1, Object arg2)
        {
            logger.warn(addId(isWarnEnabled(), format), arg1, arg2);
        }

        public void warn(String msg, Throwable t)
        {
            logger.warn(addId(isWarnEnabled(), msg), t);
        }

        public boolean isErrorEnabled()
        {
            return logger.isErrorEnabled();
        }

        public void error(String msg)
        {
            logger.error(addId(isErrorEnabled(), msg));
        }

        public void error(String format, Object arg)
        {
            logger.error(addId(isErrorEnabled(), format), arg);
        }

        public void error(String format, Object arg1, Object arg2)
        {
            logger.error(addId(isErrorEnabled(), format), arg1, arg2);
        }

        public void error(String format, Object... arguments)
        {
            logger.error(addId(isErrorEnabled(), format), arguments);
        }

        public void error(String msg, Throwable t)
        {
            logger.error(addId(isErrorEnabled(), msg), t);
        }

    }

    /**
     * Return the name of this <code>Logger</code> instance.
     * 
     * @return name of this logger instance
     */
    public String getName();

    /**
     * Is the logger instance enabled for the TRACE level?
     *
     * @return True if this Logger is enabled for the TRACE level, false otherwise.
     */
    public boolean isTraceEnabled();

    /**
     * Log a message at the TRACE level.
     *
     * @param msg
     *            the message string to be logged
     */
    public void trace(String msg);

    /**
     * Log a message at the TRACE level according to the specified format and argument.
     *
     * @param format
     *            the format string
     * @param arg
     *            the argument
     */
    public void trace(String format, Object arg);

    /**
     * Log a message at the TRACE level according to the specified format and arguments.
     *
     * @param format
     *            the format string
     * @param arg1
     *            the first argument
     * @param arg2
     *            the second argument
     */
    public void trace(String format, Object arg1, Object arg2);

    /**
     * Log a message at the TRACE level according to the specified format and arguments.
     *
     * @param format
     *            the format string
     * @param arguments
     *            a list of 3 or more arguments
     */
    public void trace(String format, Object... arguments);

    /**
     * Log an exception (throwable) at the TRACE level with an accompanying message.
     *
     * @param msg
     *            the message accompanying the exception
     * @param t
     *            the exception (throwable) to log
     */
    public void trace(String msg, Throwable t);

    /**
     * Is the logger instance enabled for the DEBUG level?
     *
     * @return True if this Logger is enabled for the DEBUG level, false otherwise.
     */
    public boolean isDebugEnabled();

    /**
     * Log a message at the DEBUG level.
     *
     * @param msg
     *            the message string to be logged
     */
    public void debug(String msg);

    /**
     * Log a message at the DEBUG level according to the specified format and argument.
     *
     * @param format
     *            the format string
     * @param arg
     *            the argument
     */
    public void debug(String format, Object arg);

    /**
     * Log a message at the DEBUG level according to the specified format and arguments.
     *
     * @param format
     *            the format string
     * @param arg1
     *            the first argument
     * @param arg2
     *            the second argument
     */
    public void debug(String format, Object arg1, Object arg2);

    /**
     * Log a message at the DEBUG level according to the specified format and arguments.
     *
     * @param format
     *            the format string
     * @param arguments
     *            a list of 3 or more arguments
     */
    public void debug(String format, Object... arguments);

    /**
     * Log an exception (throwable) at the DEBUG level with an accompanying message.
     *
     * @param msg
     *            the message accompanying the exception
     * @param t
     *            the exception (throwable) to log
     */
    public void debug(String msg, Throwable t);

    /**
     * Is the logger instance enabled for the INFO level?
     *
     * @return True if this Logger is enabled for the INFO level, false otherwise.
     */
    public boolean isInfoEnabled();

    /**
     * Log a message at the INFO level.
     *
     * @param msg
     *            the message string to be logged
     */
    public void info(String msg);

    /**
     * Log a message at the INFO level according to the specified format and argument.
     *
     * @param format
     *            the format string
     * @param arg
     *            the argument
     */
    public void info(String format, Object arg);

    /**
     * Log a message at the INFO level according to the specified format and arguments.
     *
     * @param format
     *            the format string
     * @param arg1
     *            the first argument
     * @param arg2
     *            the second argument
     */
    public void info(String format, Object arg1, Object arg2);

    /**
     * Log a message at the INFO level according to the specified format and arguments.
     *
     * @param format
     *            the format string
     * @param arguments
     *            a list of 3 or more arguments
     */
    public void info(String format, Object... arguments);

    /**
     * Log an exception (throwable) at the INFO level with an accompanying message.
     *
     * @param msg
     *            the message accompanying the exception
     * @param t
     *            the exception (throwable) to log
     */
    public void info(String msg, Throwable t);

    /**
     * Is the logger instance enabled for the WARN level?
     *
     * @return True if this Logger is enabled for the WARN level, false otherwise.
     */
    public boolean isWarnEnabled();

    /**
     * Log a message at the WARN level.
     *
     * @param msg
     *            the message string to be logged
     */
    public void warn(String msg);

    /**
     * Log a message at the WARN level according to the specified format and argument.
     *
     * @param format
     *            the format string
     * @param arg
     *            the argument
     */
    public void warn(String format, Object arg);

    /**
     * Log a message at the WARN level according to the specified format and arguments.
     *
     * @param format
     *            the format string
     * @param arguments
     *            a list of 3 or more arguments
     */
    public void warn(String format, Object... arguments);

    /**
     * Log a message at the WARN level according to the specified format and arguments.
     *
     * @param format
     *            the format string
     * @param arg1
     *            the first argument
     * @param arg2
     *            the second argument
     */
    public void warn(String format, Object arg1, Object arg2);

    /**
     * Log an exception (throwable) at the WARN level with an accompanying message.
     *
     * @param msg
     *            the message accompanying the exception
     * @param t
     *            the exception (throwable) to log
     */
    public void warn(String msg, Throwable t);

    /**
     * Is the logger instance enabled for the ERROR level?
     *
     * @return True if this Logger is enabled for the ERROR level, false otherwise.
     */
    public boolean isErrorEnabled();

    /**
     * Log a message at the ERROR level.
     *
     * @param msg
     *            the message string to be logged
     */
    public void error(String msg);

    /**
     * Log a message at the ERROR level according to the specified format and argument.
     *
     * 
     * @param format
     *            the format string
     * @param arg
     *            the argument
     */
    public void error(String format, Object arg);

    /**
     * Log a message at the ERROR level according to the specified format and arguments.
     *
     * @param format
     *            the format string
     * @param arg1
     *            the first argument
     * @param arg2
     *            the second argument
     */
    public void error(String format, Object arg1, Object arg2);

    /**
     * Log a message at the ERROR level according to the specified format and arguments.
     *
     * @param format
     *            the format string
     * @param arguments
     *            a list of 3 or more arguments
     */
    public void error(String format, Object... arguments);

    /**
     * Log an exception (throwable) at the ERROR level with an accompanying message.
     *
     * @param msg
     *            the message accompanying the exception
     * @param t
     *            the exception (throwable) to log
     */
    public void error(String msg, Throwable t);

}
