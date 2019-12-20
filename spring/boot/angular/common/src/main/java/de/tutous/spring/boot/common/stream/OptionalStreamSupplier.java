package de.tutous.spring.boot.common.stream;

import java.util.Optional;
import java.util.stream.Stream;

public interface OptionalStreamSupplier<T>
{

    public Optional<StreamSupplier<T>> supply();

    public default StreamSupplier<T> concat(Stream<T> stream)
    {
        Optional<StreamSupplier<T>> o = supply();
        if (o.isPresent())
        {
            return () -> Stream.concat(o.get().stream(), stream);
        }
        return () -> stream;
    }

}
