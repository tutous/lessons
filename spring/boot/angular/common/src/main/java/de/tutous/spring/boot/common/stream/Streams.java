package de.tutous.spring.boot.common.stream;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

public class Streams
{

    /**
     * Convert a list of items to a {@code StreamSupplier} of items.
     * 
     * @param <E>
     * @param values
     * @return
     */
    public static <E> StreamSupplier<E> asStream(Iterable<E> values)
    {
        return asStream(values, t -> t);
    }

    /**
     * Convert a list of items to a {@code StreamSupplier} of converted items.
     * 
     * @param <E>
     * @param <R>
     * @param values
     * @param function
     * @return
     */
    public static <E, R> StreamSupplier<R> asStream(Iterable<E> values, Function<E, R> function)
    {
        return () -> {
            Builder<R> builder = Stream.builder();
            values.forEach(t -> builder.add(function.apply(t)));
            return builder.build();
        };
    }

    public static <E, R> StreamSupplier<R> asStream(Collection<E> values, Function<E, R> function)
    {
        return () -> values.stream().map(t -> function.apply(t));
    }
}
