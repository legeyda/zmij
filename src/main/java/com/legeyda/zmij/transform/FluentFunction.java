package com.legeyda.zmij.transform;

import com.legeyda.zmij.transform.impl.Cast;

import java.util.function.Function;

public interface FluentFunction<T, R> extends Function<T, R> {

	default <V> OptFunction<T, V> cast() {
		final Cast<R, V> caster = Cast.create();
		return t -> caster.apply(this.apply(t));
	}

	default <V> OptFunction<T, V> cast(Class<V> clazz) {
		return this.cast();
	}

}
