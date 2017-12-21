package com.legeyda.zmij.transform;

import java.util.function.Function;

public interface FluentOptFunction<T, R> extends OptFunction<T, R> {

	<V> FluentOptFunction<T, V> map(Function<R, V> then);
	FluentFunction<T, R> orElse(final R defaultValue);
	FluentFunction<T, R> orRaise();

}
