package de.tutous.spring.boot.common.lang;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import de.tutous.spring.boot.common.stream.StreamSupplier;

public class OptionalMap<K, V> implements Map<K, V>
{

    private static final String KEY_REQUIRED = "key required";
    private static final String VALUE_REQUIRED = "value required";

    public interface OptionalMapObject<K, V>
    {
        public K getKey();

        public V getValue();
    };

    private Map<K, V> map;

    /**
     * 
     */
    public OptionalMap()
    {
        this.map = new HashMap<K, V>();
    }

    /**
     * 
     * @param key
     * @return
     */
    public Optional<V> getOptional(K key)
    {
        if (Objects.nonNull(key))
        {
            V value = get(key);
            return Objects.nonNull(value) ? Optional.of(value) : Optional.empty();
        }
        else
        {
            return Optional.empty();
        }
    }

    /**
     * only consume
     * 
     * @param key
     * @param consumer
     */
    public void ifPresent(K key, Consumer<V> consumer)
    {
        getOptional(key).ifPresent(v -> consumer.accept(v));
    }

    /**
     * consume or create
     * 
     * @param key
     * @param consumer
     * @param supplier
     */
    public V ifPresent(K key, Consumer<V> consumer, Supplier<V> supplier)
    {
        Optional<V> optional = getOptional(key);
        V value;
        if (optional.isPresent())
        {
            value = optional.get();
            consumer.accept(value);
        }
        else
        {
            value = Objects.requireNonNull(supplier.get(), VALUE_REQUIRED);
            put(key, value);
        }
        return value;
    }

    /**
     * consume or create by a function
     * 
     * @param key
     * @param consumer
     * @param function
     * @return
     */
    public V ifPresent(K key, Consumer<V> consumer, Function<K, V> function)
    {
        Optional<V> optional = getOptional(key);
        V value;
        if (optional.isPresent())
        {
            value = optional.get();
            consumer.accept(value);
        }
        else
        {
            value = Objects.requireNonNull(function.apply(Objects.requireNonNull(key, KEY_REQUIRED)), VALUE_REQUIRED);
            put(key, value);
        }
        return value;
    }

    /**
     * only create
     * 
     * @param key
     * @param supplier
     * @return
     */
    public V ifNotPresent(K key, Supplier<V> supplier)
    {
        Optional<V> optional = getOptional(key);
        V value;
        if (optional.isPresent())
        {
            value = optional.get();
        }
        else
        {
            value = Objects.requireNonNull(supplier.get(), VALUE_REQUIRED);
            put(key, value);
        }
        return value;
    }

    public V ifNotPresent(K key, Function<K, V> function)
    {
        Optional<V> optional = getOptional(key);
        V value;
        if (optional.isPresent())
        {
            value = optional.get();
        }
        else
        {
            value = Objects.requireNonNull(function.apply(Objects.requireNonNull(key, KEY_REQUIRED)), VALUE_REQUIRED);
            put(key, value);
        }
        return value;
    }

    /**
     * filter
     * 
     * @param predicate
     * @return
     */
    public StreamSupplier<Entry<K, V>> filter(Predicate<Entry<K, V>> predicate)
    {
        return () -> entrySet().stream().filter(e -> predicate.test(e));
    }

    /**
     * Associates the specified value with the specified key in this map<br>
     * (optional operation). If the map previously contained a mapping for<br>
     * the key, the old value is replaced by the specified value.<br>
     *
     * @param object
     *            the <tt>key</tt> with which the specified value is to be associated<br>
     *            the <tt>value</tt> value to be associated with the specified key<br>
     * @return the previous value associated with <tt>key</tt>, or<br>
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     */
    public V add(OptionalMapObject<K, V> object)
    {
        return put(object.getKey(), object.getValue());
    }

    @Override
    public int size()
    {
        return map.size();
    }

    @Override
    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key)
    {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value)
    {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key)
    {
        return map.get(key);
    }

    @Override
    public V put(K key, V value)
    {
        return map.put(key, value);
    }

    @Override
    public V remove(Object key)
    {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        map.putAll(m);
    }

    @Override
    public void clear()
    {
        map.clear();
    }

    @Override
    public Set<K> keySet()
    {
        return map.keySet();
    }

    @Override
    public Collection<V> values()
    {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet()
    {
        return map.entrySet();
    }

    @Override
    public boolean equals(Object o)
    {
        return map.equals(o);
    }

    @Override
    public int hashCode()
    {
        return map.hashCode();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue)
    {
        return map.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action)
    {
        map.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function)
    {
        map.replaceAll(function);
    }

    @Override
    public V putIfAbsent(K key, V value)
    {
        return map.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value)
    {
        return map.remove(key, value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue)
    {
        return map.replace(key, oldValue, newValue);
    }

    @Override
    public V replace(K key, V value)
    {
        return map.replace(key, value);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)
    {
        return map.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)
    {
        return map.computeIfPresent(key, remappingFunction);
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)
    {
        return map.compute(key, remappingFunction);
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction)
    {
        return map.merge(key, value, remappingFunction);
    }

}
