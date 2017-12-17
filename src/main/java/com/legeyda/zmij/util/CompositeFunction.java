package com.legeyda.zmij.util;

import java.util.function.Function;

public class CompositeFunction<X, Y, Z> implements Function<X, Z> {
	private final Function<? super X, ? extends Y> f;
	private final Function<? super Y, ? extends Z> g;

	public CompositeFunction(Function<? super X, ? extends Y> f, Function<? super Y, ? extends Z> g) {
		this.f = f;
		this.g = g;
	}

	@Override
	public Z apply(X x) {
		return this.g.apply(this.f.apply(x));
	}
}
