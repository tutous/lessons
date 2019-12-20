package de.tutous.spring.boot.exc;

import java.util.Date;
import java.util.Objects;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import de.tutous.spring.boot.common.api.AppError;
import de.tutous.spring.boot.common.exc.ApplicationRuntimeException;
import de.tutous.spring.boot.common.log.Logger;
import de.tutous.spring.boot.common.session.HasSessionSupport;
import de.tutous.spring.boot.common.session.SessionInfo;
import de.tutous.spring.boot.controller.DataContainerController;
import de.tutous.spring.boot.controller.MemberController;

@ControllerAdvice(basePackageClasses =
{ DataContainerController.class, MemberController.class })
public class ApplicationRuntimeExceptionHandler extends ResponseEntityExceptionHandler implements HasSessionSupport
{

    private static final Logger LOGGER = Logger.getLogger(ApplicationRuntimeExceptionHandler.class);

    private static final Supplier<SessionInfo> errorSessionInfo = () -> new SessionInfo("unknown", "unknown");

    @ResponseBody
    @ExceptionHandler(value = ApplicationRuntimeException.class)
    public ResponseEntity<AppError<String>> handleException(HttpServletRequest request,
            ApplicationRuntimeException exception)
    {

        Supplier<SessionInfo> supplierSessionInfo = get(SessionInfo.class).orElse(errorSessionInfo);

        AppError<String> appError = new AppError<String>(exception.getId(String.class), //
                exception.getMessage(), //
                new Date(), //
                exception.getHttpStatus().value(), //
                exception.getHttpStatus().getReasonPhrase(), //
                supplierSessionInfo.get().getCurrentRequestURI(), //
                supplierSessionInfo.get().getCurrentRequestMethod(), //
                supplierSessionInfo.get().getUuid().toString());

        LOGGER.error(appError.toString(), exception);

        return ResponseEntity.status(exception.getHttpStatus()).body(appError);
    }

    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<AppError<String>> handleException(HttpServletRequest request,
            ConstraintViolationException exception)
    {

        Supplier<SessionInfo> supplierSessionInfo = get(SessionInfo.class).orElse(errorSessionInfo);

        HttpStatus status = HttpStatus.BAD_REQUEST;

        AppError<String> appError = new AppError<String>("000", //
                exception.getMessage(), //
                new Date(), //
                status.value(), //
                status.getReasonPhrase(), //
                supplierSessionInfo.get().getCurrentRequestURI(), //
                supplierSessionInfo.get().getCurrentRequestMethod(), //
                supplierSessionInfo.get().getUuid().toString());

        LOGGER.error(appError.toString(), exception);

        return ResponseEntity.status(status).body(appError);

    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request)
    {

        Supplier<SessionInfo> supplierSessionInfo = get(SessionInfo.class).orElse(errorSessionInfo);
        AppError<String> appError;
        ApplicationRuntimeException appException = findApplicationRuntimeException(exception);

        if (Objects.nonNull(appException))
        {

            appError = new AppError<String>(appException.getId(String.class), //
                    appException.getMessage(), //
                    new Date(), //
                    appException.getHttpStatus().value(), //
                    appException.getHttpStatus().getReasonPhrase(), //
                    supplierSessionInfo.get().getCurrentRequestURI(), //
                    supplierSessionInfo.get().getCurrentRequestMethod(), //
                    supplierSessionInfo.get().getUuid().toString());

        }
        else
        {

            appError = new AppError<String>("UNKNOWN", //
                    exception.getMessage(), //
                    new Date(), //
                    status.value(), //
                    status.getReasonPhrase(), //
                    supplierSessionInfo.get().getCurrentRequestURI(), //
                    supplierSessionInfo.get().getCurrentRequestMethod(), //
                    supplierSessionInfo.get().getUuid().toString());

        }

        LOGGER.error(appError.toString(), exception);

        return ResponseEntity.status(status).body(appError);
    }

    private ApplicationRuntimeException findApplicationRuntimeException(Throwable exception)
    {
        Throwable cause = exception;
        while (Objects.nonNull(cause) && !ApplicationRuntimeException.class.isInstance(cause))
        {
            cause = cause.getCause();
        }
        if (ApplicationRuntimeException.class.isInstance(cause))
        {
            return ApplicationRuntimeException.class.cast(cause);
        }
        else
        {
            return null;
        }
    }
}
