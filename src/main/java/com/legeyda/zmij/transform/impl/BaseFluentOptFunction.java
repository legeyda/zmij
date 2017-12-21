package com.legeyda.zmij.transform.impl;

import com.legeyda.zmij.transform.FluentFunction;
import com.legeyda.zmij.transform.FluentOptFunction;

import java.util.Optional;
import java.util.function.Function;

public abstract class BaseFluentOptFunction<T, R> implements FluentOptFunction<T, R> {

	@Override
	public <V> FluentOptFunction<T, V> map(Function<R, V> then) {
		return new FluentOptFunctionImpl<>(t -> this.apply(t).map(then));
	}

	@Override
	public FluentFunction<T, R> orElse(final R defaultValue) {
		return new BaseFluentFunction<T, R>() {
			@Override
			public R apply(final T o) {
				final Optional<R> result = BaseFluentOptFunction.this.apply(o);
				return result.orElse(defaultValue);
			}
		};
	}

	@Override
	public FluentFunction<T, R> orRaise() {
		return new BaseFluentFunction<T, R>() {
			@Override
			public R apply(final T o) {
				final Optional<R> result = BaseFluentOptFunction.this.apply(o);
				return result.get();
			}
		};
	}

}
