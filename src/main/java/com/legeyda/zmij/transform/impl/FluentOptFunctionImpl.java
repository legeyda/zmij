package com.legeyda.zmij.transform.impl;

import java.util.Optional;
import java.util.function.Function;

public class FluentOptFunctionImpl<T, R> extends BaseFluentOptFunction<T, R> {

	private final Function<T, Optional<R>> wrapee;

	public FluentOptFunctionImpl(Function<T, Optional<R>> wrapee) {
		this.wrapee = wrapee;
	}

	@Override
	public Optional<R> apply(T t) {
		return this.wrapee.apply(t);
	}
}
