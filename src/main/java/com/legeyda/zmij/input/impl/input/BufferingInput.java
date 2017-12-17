package com.legeyda.zmij.input.impl.input;

import com.legeyda.zmij.input.Input;
import com.legeyda.zmij.input.Item;

import java.util.Collection;
import java.util.LinkedList;

public class BufferingInput<T> extends InputDecorator<T> {

	private final Collection<Item> buffer;

	public BufferingInput(final Input<T> wrap, final Collection<Item> buffer) {
		super(wrap);
		this.buffer = buffer;
		this.onAfterAdvance();
	}

	public BufferingInput(final Input<T> wrap) {
		this(wrap, new LinkedList<>());
	}

	private void onAfterAdvance() {
		if(this.valid()) {
			this.buffer.add(this.get());
		}
	}

	@Override
	public void advance() {
		super.advance();
		this.onAfterAdvance();
	}

}
