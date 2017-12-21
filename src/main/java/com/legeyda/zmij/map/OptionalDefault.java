package com.legeyda.zmij.map;

import java.util.Optional;
import java.util.function.Function;

public class OptionalDefault<T, R> implements Function<T, R> {

	private final Function<? super T, Optional<R>> wrapee;
	private final R defaultValue;

	public OptionalDefault(Function<? super T, Optional<R>> wrapee, final R defaultValue) {
		this.wrapee = wrapee;
		this.defaultValue = defaultValue;
	}

	@Override
	public R apply(T t) {
		final Optional<? extends R> result = this.wrapee.apply(t);
		if(result.isPresent()) {
			return result.get();
		} else {
			return this.defaultValue;
		}
	}
}
