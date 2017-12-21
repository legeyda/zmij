package com.legeyda.zmij.map;

import java.util.Optional;
import java.util.function.Function;

public abstract class MapToOptional<T, R> implements Function<T, Optional<R>> {

	OptionalDefault<T, R> orElse(final R defaultValue) {
		return new OptionalDefault<>(this, defaultValue);
	}

}
