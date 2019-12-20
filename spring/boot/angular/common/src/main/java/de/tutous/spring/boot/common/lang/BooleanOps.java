package de.tutous.spring.boot.common.lang;

import java.util.Arrays;
import java.util.Objects;

public interface BooleanOps<T> {

	T isTrue(Boolean value);

	public default T isEquals(Object object, Object other) {
		if (Objects.nonNull(object) && Objects.nonNull(other)) {
			return isTrue(object.equals(other));
		} else {
			return isTrue(false);
		}
	}

	public default T isNull(Object obj) {
		return isTrue(Objects.isNull(obj));
	}

	public default T nonNull(Object obj) {
		return isTrue(Objects.nonNull(obj));
	}

	public default T nonNull(Object... objs) {
		return Arrays.asList(objs).stream().map(obj -> nonNull(obj)).findFirst().get();
	}

}