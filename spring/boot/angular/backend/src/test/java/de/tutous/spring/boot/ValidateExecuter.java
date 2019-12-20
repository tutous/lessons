package de.tutous.spring.boot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class ValidateExecuter
{

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ValidateExecuter.class);

    private Validator validator;

    private ValidateExecuter()
    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public static ValidateExecuter getInstance()
    {
        return new ValidateExecuter();
    }

    public void execute(Execute... executes)
    {
        AtomicReference<ArrayList<Throwable>> reference = new AtomicReference<>(new ArrayList<Throwable>());
        Arrays.asList(executes).stream().forEach(e -> {
            try
            {
                e.doTest();
            }
            catch (InvalidFormatException exc)
            {
                LOGGER.error(exc.getLocalizedMessage());
                reference.get().add(exc);
            }
            catch (Throwable exc)
            {
                LOGGER.error("unexpected error", exc);
                reference.get().add(exc);
            }
        });
        if (reference.get().size() > 0)
        {
            Stream<Throwable> errors = reference.get().stream();
            throw new AssertionError(errors.map(exc -> exc.getMessage()).collect(Collectors.toList()));
        }
    }

    public static interface Execute
    {
        void doTest() throws Exception;
    }

    public String toValue(int time, String value)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < time; i++)
        {
            sb.append(value);
        }
        return sb.toString();
    }

    public void validate(Object bo, boolean assertFail)
    {
        Set<ConstraintViolation<Object>> violations = validator.validate(bo);
        if (violations.size() > 0)
        {
            if (assertFail)
            {
                // fail equals true, than only log
                violations.stream().forEach(v -> {
                    String message = v.getPropertyPath() + ": " + v.getMessage();
                    LOGGER.info(message);
                });
            }
            else
            {
                // fail equals false, than error log
                violations.stream().forEach(v -> {
                    String message = v.getPropertyPath() + ": " + v.getMessage();
                    LOGGER.error(message);
                });
                throw new AssertionError(violations);
            }
        }
    }

}
