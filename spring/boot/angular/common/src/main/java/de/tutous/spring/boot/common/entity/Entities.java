package de.tutous.spring.boot.common.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import de.tutous.spring.boot.common.bo.BusinessObject;
import de.tutous.spring.boot.common.exc.EntityNotFoundRuntimeException;
import de.tutous.spring.boot.common.exc.ErrorCode;
import de.tutous.spring.boot.common.stream.StreamSupplier;
import de.tutous.spring.boot.common.stream.Streams;

public class Entities
{

    /**
     * Return the entity or throw an {@codeEntityNotFoundRuntimeException}.
     * 
     * @param <E>
     * @param optional
     * @param errorCode
     * @param args
     * @return
     */
    public static <E extends AbstractEntity<? extends Serializable>> E get(Optional<E> optional, ErrorCode<?> errorCode,
            String... args)
    {
        if (optional.isPresent())
        {
            return optional.get();
        }
        throw new EntityNotFoundRuntimeException(errorCode, args);
    }

    public static <BO extends BusinessObject<BO, ID>, E extends AbstractEntity<ID>, ID extends Serializable> Optional<BO> toSafeBO(
            Optional<E> optional, Class<BO> interfaceClass)
    {
        if (optional.isPresent())
        {
            return Optional.of(optional.get().toSafeBO(interfaceClass));
        }
        else
        {
            return Optional.empty();
        }
    }

    /**
     * Consumes the entity behind the {@code BusinessObject}.
     * 
     * @param <BO>
     *            type of BusinessObject
     * @param <E>
     *            type of entity
     * @param <ID>
     *            type of identifier
     * @param bo
     * @param type
     * @param consumer
     */
    public static <BO extends BusinessObject<BO, ID>, E extends AbstractEntity<ID>, ID extends Serializable> void toEntity(
            BO bo, Class<E> type, Consumer<E> consumer)
    {
        Optional<E> optional = Optional.empty();
        AbstractEntity<?> safeEntity;
        if (type.isInstance(bo))
        {
            optional = Optional.of(type.cast(bo));
        }
        else if (SafeEntitySupplier.class.isInstance(bo)
                && Objects.nonNull((safeEntity = SafeEntitySupplier.class.cast(bo).get()))
                && type.isInstance(safeEntity))
        {
            optional = Optional.of(type.cast(safeEntity));
        }
        optional.ifPresent(entity -> consumer.accept(entity));
    }

    /**
     * Consumes a list of entities behind the {@code BusinessObject}.
     * 
     * @param <BO>
     *            type of BusinessObject
     * @param <E>
     *            type of entity
     * @param <ID>
     *            type of identifier
     * @param bos
     * @param type
     * @param consumer
     */
    public static <BO extends BusinessObject<BO, ID>, E extends AbstractEntity<ID>, ID extends Serializable> void toEntities(
            Stream<BO> bos, Class<E> type, Consumer<E> consumer)
    {
        bos.forEach(bo -> toEntity(bo, type, consumer));
    }

    public static <BO extends BusinessObject<BO, ID>, E extends AbstractEntity<ID>, ID extends Serializable> Stream<E> toEntities(
            Stream<BO> bos, Class<E> type)
    {
        AtomicReference<Stream.Builder<E>> reference = new AtomicReference<Stream.Builder<E>>(Stream.builder());
        bos.forEach(bo -> toEntity(bo, type, e -> reference.get().add(e)));
        return reference.get().build();
    }

    /**
     * Creates a {@code StreamSupplier} of safe BOs based on the entities.
     * 
     * @param <BO>
     *            type of BusinessObject
     * @param <E>
     *            type of entity
     * @param <ID>
     *            type of identifier
     * @param entities
     * @param interfaceClass
     * @return
     */
    public static <BO extends BusinessObject<BO, ID>, E extends AbstractEntity<ID>, ID extends Serializable> StreamSupplier<BO> toSafeBOs(
            Iterable<E> entities, Class<BO> interfaceClass)
    {
        return Streams.asStream(entities, entity -> entity.toSafeBO(interfaceClass));
    }

    /**
     * Read each entity property and transform this to a String.
     * 
     * @param <E>
     * @param entities
     * @param properties
     * @param functions
     * @return
     */
    @SafeVarargs
    public static <E extends AbstractEntity<?>> String toString(Collection<E> entities, String[] properties,
            Function<E, Object>... functions)
    {
        if (Objects.isNull(entities) || entities.isEmpty() || Objects.isNull(functions))
        {
            return "[]";
        }
        // properties to iterator (need hasNext and next)
        Iterator<String> props = Arrays.asList(properties).iterator();
        BinaryOperator<String> accumulator = (v1, v2) -> v1 + "," + v2;
        // read each entity property
        String result = '[' + entities.stream()
                // map each entity
                .map(entity -> {
                    // concatenation of function and property
                    return '[' + Arrays.asList(functions).stream()
                            // map to string
                            .map(function -> {
                                String value = String.valueOf(function.apply(entity));
                                String prop = props.hasNext() ? (props.next() + "=") : "";
                                // transform function and property to prop='value'
                                return prop + "'" + value + "'";
                            })
                            // reduce to [prop1='v1',prop2='v2',...]
                            .reduce(accumulator)
                            // else
                            .orElse("") + ']';
                })
                // reduce to [[prop1='v1',prop2='v2',...],[...]]
                .reduce(accumulator)
                // else
                .orElse("") + ']';
        return result;
    }

    /**
     * 
     * @param <BO>
     * @param <ID>
     * @param currentBusinessObjects
     * @param newBusinessObjects
     * @return
     */
    public static <BO extends BusinessObject<BO, ID>, ID extends Serializable> Stream<BO> findRemoved(
            StreamSupplier<BO> currentBusinessObjects, StreamSupplier<BO> newBusinessObjects)
    {
        Collection<BO> newBusinessObjectCollection = newBusinessObjects.collect();
        return currentBusinessObjects.stream().filter(vc -> !newBusinessObjectCollection.contains(vc));
    }
}
