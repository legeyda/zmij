package com.legeyda.zmij.result;

import java.util.function.Consumer;


/** getOpt().ifValue().ifMessage() */
public interface Result<T> {
	boolean isPresent();
	T value();
	String message();

	Result<T> ifValue(Consumer<T> valueHandler);
	Result<T> ifMessage(Consumer<String> errorHandler);
}
