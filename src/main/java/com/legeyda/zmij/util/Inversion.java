package com.legeyda.zmij.util;

import java.util.function.Predicate;

public class Inversion<T> implements Predicate<T> {

	private Predicate<T> base;

	public Inversion(Predicate<T> base) {
		this.base = base;
	}

	@Override
	public boolean test(T t) {
		return !this.base.test(t);
	}


}
