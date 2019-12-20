package de.tutous.spring.boot.common.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Payload;

public interface ParamErrorHandler<T> extends Payload
{
    void onError (ConstraintViolation<T> violation);
}
