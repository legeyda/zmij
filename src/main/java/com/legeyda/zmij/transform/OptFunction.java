package com.legeyda.zmij.transform;

import java.util.Optional;
import java.util.function.Function;

/** function that returns optional */
public interface OptFunction<T, R> extends Function<T, Optional<R>> {

	default <V> OptFunction<T, V> map(Function<R, V> then) {
		return t -> this.apply(t).map(then);
	}

	default Function<T, R> orElse(final R defaultValue) {
		return t -> this.apply(t).orElse(defaultValue);
	}

	default Function<T, R> orRaise() {
		return t -> this.apply(t).get();
	}

}
