package com.legeyda.zmij.transform;

import java.util.function.Function;

public interface FluentFunction<T, R> extends Function<T, R> {

	<V> FluentOptFunction<T, V> cast();

}
