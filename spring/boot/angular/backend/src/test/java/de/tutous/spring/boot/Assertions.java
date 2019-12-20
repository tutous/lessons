package de.tutous.spring.boot;

import java.util.Objects;

public class Assertions
{

    public static void nonNull(Object instance)
    {
        if (Objects.isNull(instance))
        {
            throw new AssertionError("instance required");
        }
    }

}
