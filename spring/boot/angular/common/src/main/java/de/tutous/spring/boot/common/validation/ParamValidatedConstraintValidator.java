package de.tutous.spring.boot.common.validation;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Payload;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

import de.tutous.spring.boot.common.log.Logger;

public class ParamValidatedConstraintValidator implements ConstraintValidator<ParamValidated, Object>
{

    private static final Logger LOGGER = Logger.getLogger(ParamValidatedConstraintValidator.class);

    @Autowired
    private Validator validator;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context)
    {
        Set<ConstraintViolation<Object>> violations = validator.validate(value);
        if (violations.size() > 0)
        {
            violations.stream().forEach(ParamValidatedConstraintValidator::processError);
            return false;
        }
        return true;
    }

    @SuppressWarnings(
    { "unchecked" })
    private static void processError(ConstraintViolation<Object> violation)
    {
        Set<Class<? extends Payload>> payload = violation.getConstraintDescriptor().getPayload();
        payload.forEach(p -> {
            if (ParamErrorHandler.class.isAssignableFrom(p))
            {
                try
                {
                    ParamErrorHandler.class.cast(p.newInstance()).onError(violation);
                }
                catch (Exception e)
                {
                    LOGGER.error("can't handle bean validation payload", e);
                }
            }
        });
    }
}
