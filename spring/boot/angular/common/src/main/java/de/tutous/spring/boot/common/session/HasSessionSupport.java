package de.tutous.spring.boot.common.session;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface HasSessionSupport {

	/**
	 * Register a component by a key.
	 * 
	 * @param <T>
	 * @param key
	 * @param component
	 */
	public default <T> void register(String key, T component) {
		SessionSupportContext.get().put(key, component);
	}

	/**
	 * Register a session supplier. The identification will be done by the type.
	 * 
	 * @param <T>
	 * @param supplier
	 * @param typeT
	 */
	public default <T> void register(Supplier<T> supplier, Class<T> type) {
		SessionSupportContext.get().put(type, supplier);
	}

	/**
	 * Register a {@code Function<T, R>}. Used by the instance.
	 * 
	 * @param <T>      the type of the input to the function
	 * @param <R>      the type of the result of the function
	 * @param function
	 * @param typeT
	 * @param typeR
	 * @return
	 */
	public default <T, R> void register(Function<T, R> function, Class<T> typeT, Class<R> typeR) {
		SessionSupportContext.get().put(typeT, function);
	}

	/**
	 * Register a {@code BiFunction<T, U, R>}. Used by the instance.
	 * 
	 * @param <T>      the type of the first argument to the function
	 * @param <U>      the type of the second argument to the function
	 * @param <R>      the type of the result of the function
	 * @param function
	 * @param typeT
	 * @param typeU
	 * @param typeR
	 * @return
	 */
	public default <T, U, R> void register(BiFunction<T, U, R> function, Class<T> typeT, Class<U> typeU,
			Class<R> typeR) {
		SessionSupportContext.get().put(typeT, typeU, function);
	}

	/**
	 * Get ...
	 * 
	 * @param <T>
	 * @param key
	 * @param type
	 * @return
	 */
	public default <T> Optional<T> get(String key, Class<T> type) {
		return SessionSupportContext.get().getComponent(key, type);
	}

	/**
	 * Get ...
	 * 
	 * @param <T>
	 * @param <R>
	 * @param uuid
	 * @param typeT
	 * @param typeR
	 * @return
	 */
	public default <T, R> Optional<Function<T, R>> get(Class<T> typeT, Class<R> typeR) {
		return SessionSupportContext.get().getFunction(typeT);
	}

	/**
	 * Get ...
	 * 
	 * @param <T>
	 * @param <U>
	 * @param <R>
	 * @param uuid
	 * @param typeT
	 * @param typeU
	 * @param typeR
	 * @return
	 */
	public default <T, U, R> Optional<BiFunction<T, U, R>> get(Class<T> typeT, Class<U> typeU, Class<R> typeR) {
		return SessionSupportContext.get().getBiFunction(typeT, typeU);
	}

	/**
	 * Get ...
	 * 
	 * @param <T>
	 * @param type
	 * @return
	 */
	public default <T> Optional<Supplier<T>> get(Class<T> type) {
		return SessionSupportContext.get().getSupplier(type);
	}

	/**
	 * 
	 * @param <T>
	 * @param type
	 * @param consumer
	 */
	public default <T> void doIfSupplier(Class<T> type, Consumer<T> consumer) {
		Optional<Supplier<T>> optional = SessionSupportContext.get().getSupplier(type);
		if (optional.isPresent()) {
			T t = optional.get().get();
			if (Objects.nonNull(t)) {
				consumer.accept(t);
			}
		}
	}

	public default <T, U> void doIfSuppliers(Class<T> typeOne, Class<U> typeTwo, BiConsumer<T, U> consumer) {
		Optional<Supplier<T>> optionalOne = SessionSupportContext.get().getSupplier(typeOne);
		Optional<Supplier<U>> optionalTwo = SessionSupportContext.get().getSupplier(typeTwo);
		if (optionalOne.isPresent() && optionalTwo.isPresent()) {
			T t = optionalOne.get().get();
			U u = optionalTwo.get().get();
			if (Objects.nonNull(t) && Objects.nonNull(u)) {
				consumer.accept(t, u);
			}
		}
	}

	/**
	 * 
	 * @param <T>
	 * @param <R>
	 * @param type
	 * @param function
	 * @return
	 */
	public default <T, R> Optional<R> doAndGetIfSupplier(Class<T> type, Function<T, Optional<R>> function) {
		Optional<Supplier<T>> optional = SessionSupportContext.get().getSupplier(type);
		if (optional.isPresent()) {
			T t = optional.get().get();
			if (Objects.nonNull(t)) {
				return function.apply(t);
			}
		}
		return Optional.empty();
	}

	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param <R>
	 * @param typeOne
	 * @param typeTwo
	 * @param function
	 * @return
	 */
	public default <T, U, R> Optional<R> doAndGetIfSuppliers(Class<T> typeOne, Class<U> typeTwo,
			BiFunction<T, U, Optional<R>> function) {
		Optional<Supplier<T>> optionalOne = SessionSupportContext.get().getSupplier(typeOne);
		Optional<Supplier<U>> optionalTwo = SessionSupportContext.get().getSupplier(typeTwo);
		if (optionalOne.isPresent() && optionalTwo.isPresent()) {
			T t = optionalOne.get().get();
			U u = optionalTwo.get().get();
			if (Objects.nonNull(t) && Objects.nonNull(u)) {
				return function.apply(t, u);
			}
		}
		return Optional.empty();
	}
}
