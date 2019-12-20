package de.tutous.spring.boot.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(
{ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DataContainerNewBOConstraintValidator.class)
public @interface DataContainerNewBOValidated {

	String message() default "Name is not unique!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
}
