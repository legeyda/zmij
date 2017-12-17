package com.legeyda.zmij.input.impl.input;

import com.legeyda.zmij.input.Input;
import com.legeyda.zmij.input.Item;

public class EmptyInput<T> implements Input<T> {

	@Override
	public boolean valid() {
		return false;
	}

	@Override
	public void advance() {
		// do nothing
	}

	@Override
	public Item<T> get() {
		throw new IllegalStateException("reading from on empty input");
	}

}
