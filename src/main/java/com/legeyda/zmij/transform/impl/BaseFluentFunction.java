package com.legeyda.zmij.transform.impl;

import com.legeyda.zmij.transform.FluentFunction;
import com.legeyda.zmij.transform.FluentOptFunction;

import java.util.Optional;
import java.util.function.Function;

public abstract class BaseFluentFunction<T, R> implements FluentFunction<T, R> {

	@Override
	public <V> FluentFunction<V, R> compose(Function<? super V, ? extends T> primary) {
		return new FluentFunctionImpl<>(v -> this.apply(primary.apply(v)));
	}

	@Override
	public <V> FluentFunction<T, V> andThen(Function<? super R, ? extends V> secondary) {
		return new FluentFunctionImpl<>(t -> secondary.apply(this.apply(t)));
	}

	@Override
	public <V> FluentOptFunction<T, V> cast() {
		return new FluentOptFunctionImpl<>(r -> {
			final V result;
			try {
				result = (V) r;
			} catch (ClassCastException e) {
				return Optional.empty();
			}
			return Optional.of(result);
		});
	}

}
