package com.legeyda.zmij.result;

import java.util.function.Function;


/** getOpt().ifValue().ifMessage() */
public interface Result<T> {
	boolean isPresent();
	T value();
	String message();

	<V> Result<V> map(Function<? super T, ? extends V> mapping);
	T orElse(final T defaultValue);
}
