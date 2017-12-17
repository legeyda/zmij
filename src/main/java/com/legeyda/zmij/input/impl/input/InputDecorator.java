package com.legeyda.zmij.input.impl.input;

import com.legeyda.zmij.input.Input;
import com.legeyda.zmij.input.Item;

public abstract class InputDecorator<T> implements Input<T> {
	protected final Input<T> wrap;

	public InputDecorator(Input wrap) {
		this.wrap = wrap;
	}

	@Override
	public boolean valid() {
		return wrap.valid();
	}

	@Override
	public void advance() {
		wrap.advance();
	}

	@Override
	public Item<T> get() {
		return wrap.get();
	}
}
