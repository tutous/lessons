package de.tutous.spring.boot.common.stream;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
public interface StreamSupplier<T>
{

    public Stream<T> stream();

    /**
     * Returns a StreamSupplier consisting of the results of applying the given function to the elements of this
     * stream.
     * 
     * @param <R>
     * @param function
     * @return
     */
    public default <R> StreamSupplier<R> map(Function<T, R> function)
    {
        return () -> stream().map(t -> function.apply(t));
    }

    /**
     * Performs an action for each element of this stream.
     * 
     * This is a terminal operation.
     * 
     * @param action
     */
    public default void forEach(Consumer<? super T> action)
    {
        stream().forEach(action);
    }

    /**
     * Returns an Optional describing the first element of this stream, or an empty Optional if the stream is empty.
     * If the stream has no encounter order, then any element may be returned.
     * 
     * This is a short-circuiting terminal operation.
     * 
     * @return
     */
    public default Optional<T> findFirst()
    {
        return stream().findFirst();
    }

    /**
     * Performs a mutable reduction operation on the elements of this stream using a Collector.
     * 
     * @return
     */
    public default Collection<T> collect()
    {
        return stream().collect(Collectors.toList());
    }

    /**
     * Creates a lazily concatenated stream whose elements are all the elements of the first stream followed by all
     * the elements of the second stream.
     * 
     * @param stream
     * @return
     */
    public default StreamSupplier<T> concat(Stream<T> stream)
    {
        return () -> Stream.concat(stream(), stream);
    }

    /**
     * Creates a lazily concatenated stream whose elements are all the elements of the first stream followed by all
     * the elements of the second stream.
     * 
     * @param supplier
     * @return
     */
    public default StreamSupplier<T> concat(StreamSupplier<T> supplier)
    {
        return concat(supplier.stream());
    }

    /**
     * Creates a lazily concatenated stream whose elements are all the elements of the first stream followed by all
     * the elements of the second stream.
     * 
     * @param optional
     * @return
     */
    public default StreamSupplier<T> concat(OptionalStreamSupplier<T> optional)
    {
        return optional.concat(stream());
    }

}
