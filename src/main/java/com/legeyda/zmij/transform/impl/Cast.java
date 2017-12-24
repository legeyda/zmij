package com.legeyda.zmij.transform.impl;

import com.legeyda.zmij.transform.OptFunction;

import java.util.Optional;

public class Cast<T, R> implements OptFunction<T, R> {

	public static <T, R> Cast<T, R> create() {
		return new Cast<>();
	}

	public static <T, R> Cast<T, R> create(final Class<R> clazz) {
		return new Cast<>(clazz);
	}

	public Cast() {}

	public Cast(final Class<R> clazz) {}

	@Override
	public Optional<R> apply(T t) {
		final R r;
		try {
			r = (R) t;
		} catch (ClassCastException e) {
			return Optional.empty();
		}
		return Optional.of(r);
	}
}
