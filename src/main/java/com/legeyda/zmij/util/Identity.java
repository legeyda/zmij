package com.legeyda.zmij.util;

import java.util.function.Function;

public class Identity<T> implements Function<T, T> {

	public static final <T> Identity<T> instance() {
		return new Identity<>();
	}

	@Override
	public T apply(T t) {
		return t;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
