package de.tutous.spring.boot.common.validation;

import javax.validation.ConstraintViolation;

import de.tutous.spring.boot.common.log.Logger;

public class ParamLogger implements ParamErrorHandler<Object>
{
    private static final Logger LOGGER = Logger.getLogger(ParamLogger.class);

    @Override
    public void onError(ConstraintViolation<Object> violation)
    {
        String simpleName = violation.getLeafBean().getClass().getSimpleName();
        LOGGER.error(simpleName + "[" + "message='" + violation.getMessage() + "',property='"
                + violation.getPropertyPath() + "',invalidValue='" + violation.getInvalidValue() + "']");
    }

}
