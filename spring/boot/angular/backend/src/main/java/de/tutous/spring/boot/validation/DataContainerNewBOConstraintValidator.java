package de.tutous.spring.boot.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import de.tutous.spring.boot.bo.DataContainerNewBO;
import de.tutous.spring.boot.common.log.Logger;
import de.tutous.spring.boot.service.DataContainerService;

public class DataContainerNewBOConstraintValidator implements ConstraintValidator<DataContainerNewBOValidated, DataContainerNewBO>
{

    private static final Logger LOGGER = Logger.getLogger(DataContainerNewBOConstraintValidator.class);
    
    @Autowired
    private DataContainerService dataContainerService;

	@Override
	public boolean isValid(DataContainerNewBO value, ConstraintValidatorContext context) 
	{
		boolean valid = !dataContainerService.findByName(value.getName()).isPresent();
		
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate("DataContainer name not unique!").addConstraintViolation();
		
		return valid;
	}

}
