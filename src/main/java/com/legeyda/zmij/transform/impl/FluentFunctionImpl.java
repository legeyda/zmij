package com.legeyda.zmij.transform.impl;

import java.util.function.Function;

public class FluentFunctionImpl<T, R> extends BaseFluentFunction<T, R> {

	private final Function<T, R> wrapee;

	public FluentFunctionImpl(Function<T, R> wrapee) {
		this.wrapee = wrapee;
	}

	@Override
	public R apply(T t) {
		return this.wrapee.apply(t);
	}
}
