package de.tutous.spring.boot.common.session;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import de.tutous.spring.boot.common.lang.BooleanBuilder;

public class SessionSupport {

	private Map<RegKey, Object> map = new HashMap<RegKey, Object>();
	private LocalDateTime created;

	public SessionSupport() {
		this.created = LocalDateTime.now();
	}
	
	public LocalDateTime getCreated() {
		return created;
	}

	/**
	 * Put ...
	 * 
	 * @param key
	 * @param component
	 */
	public void put(String key, Object component) {
		map.put(new RegKey(key), component);
	}

	/**
	 * Put ...
	 * 
	 * @param <T>
	 * @param <R>
	 * @param type
	 * @param function
	 */
	public <T, R> void put(Class<T> typeT, Function<T, R> function) {
		map.put(new RegKey(typeT), function);
	}

	/**
	 * Put ...
	 * 
	 * @param <T>
	 * @param <U>
	 * @param <R>
	 * @param typeT
	 * @param typeU
	 * @param function
	 */
	public <T, U, R> void put(Class<T> typeT, Class<U> typeU, BiFunction<T, U, R> function) {
		map.put(new RegKey(typeT, typeU), function);
	}

	/**
	 * Put ...
	 * 
	 * @param <T>
	 * @param type
	 * @param supplier
	 */
	public <T> void put(Class<T> type, Supplier<T> supplier) {
		map.put(new RegKey(type), supplier);
	}

	/**
	 * Get ...
	 * 
	 * @param <T>
	 * @param key
	 * @param type
	 * @return
	 */
	public <T> Optional<T> getComponent(String key, Class<T> type) {
		Object component = map.get(new RegKey(key));
		if (Objects.nonNull(component) && type.isInstance(component)) {
			return Optional.of(type.cast(component));
		}
		return Optional.empty();
	}

	/**
	 * Get ...
	 * 
	 * @param <T>
	 * @param type
	 * @return
	 */
	public <T> Optional<Supplier<T>> getSupplier(Class<T> type) {
		Object supplier = map.get(new RegKey(type));
		if (Objects.nonNull(supplier) && Supplier.class.isInstance(supplier)) {
			return Optional.of(Supplier.class.cast(supplier));
		}
		return Optional.empty();
	}

	/**
	 * Get ...
	 * 
	 * @param <T>
	 * @param <R>
	 * @param typeT
	 * @return
	 */
	public <T, R> Optional<Function<T, R>> getFunction(Class<T> typeT) {
		Object component = map.get(new RegKey(typeT));
		if (Objects.nonNull(component) && Function.class.isInstance(component)) {
			return Optional.of(Function.class.cast(component));
		}
		return Optional.empty();
	}

	/**
	 * Get ...
	 * 
	 * @param <T>
	 * @param <U>
	 * @param <R>
	 * @param typeT
	 * @param typeU
	 * @return
	 */
	public <T, U, R> Optional<BiFunction<T, U, R>> getBiFunction(Class<T> typeT, Class<U> typeU) {
		Object component = map.get(new RegKey(typeT, typeU));
		if (Objects.nonNull(component) && BiFunction.class.isInstance(component)) {
			return Optional.of(BiFunction.class.cast(component));
		}
		return Optional.empty();
	}

	public boolean exist(String key) {
		return map.containsKey(new RegKey(key));
	}

	private static class RegKey {

		private String key;
		private Class<?> typeA;
		private Class<?> typeB;

		private RegKey(String key) {
			this.key = key;
		}

		public RegKey(Class<?> typeA) {
			this.typeA = typeA;
		}

		public RegKey(Class<?> typeA, Class<?> typeB) {
			this.typeA = typeA;
			this.typeB = typeB;
		}

		@Override
		public int hashCode() {
			if (Objects.nonNull(key)) {
				return System.identityHashCode(key);
			} else if (Objects.nonNull(typeA) && Objects.nonNull(typeB)) {
				return System.identityHashCode(typeA) + System.identityHashCode(typeB);
			} else if (Objects.nonNull(typeA)) {
				return System.identityHashCode(typeA);
			}
			return super.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			} else if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			RegKey other = (RegKey) obj;
			return BooleanBuilder.byAnd()
					// key
					.nonNull(key).isEquals(key, other.key).or()
					// typeA and typeB
					.nonNull(typeA).isEquals(typeA, other.typeA).nonNull(typeB).isEquals(typeB, other.typeB).or()
					// typeA
					.nonNull(typeA).isEquals(typeA, other.typeA)
					// build
					.build().isTrue();
		}
	}

}
