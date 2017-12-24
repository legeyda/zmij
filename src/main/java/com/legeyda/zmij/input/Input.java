package com.legeyda.zmij.input;

import java.util.function.Supplier;

public interface Input<T> extends Supplier<Item<T>> {

	/** is valid state */
	boolean valid();

	/** goto next position */
	void advance();

	/** any2value character&position currently standing on */
	@Override
	Item<T> get();

}
