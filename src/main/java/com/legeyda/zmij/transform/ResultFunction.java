package com.legeyda.zmij.transform;

import com.legeyda.zmij.result.Result;

import java.util.function.Function;

public interface ResultFunction<T, R> extends Function<T, Result<R>> {

	default <V> ResultFunction<T, V> map(Function<R, V> then) {
		return t -> this.apply(t).map(then);
	}

	default Function<T, R> orElse(final R defaultValue) {
		return t -> this.apply(t).orElse(defaultValue);
	}

	default Function<T, R> orRaise() {
		return t -> this.apply(t).value();
	}

}
