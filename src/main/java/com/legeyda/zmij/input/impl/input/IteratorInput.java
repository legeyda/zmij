package com.legeyda.zmij.input.impl.input;

import com.legeyda.zmij.input.Input;
import com.legeyda.zmij.input.Item;

import java.util.Iterator;

public class IteratorInput<T> implements Input<T> {

	private final Iterator<Item<T>> data;
	private boolean valid = false;
	private Item<T> item;

	public IteratorInput(Iterator<Item<T>> data) {
		this.data = data;
		this.advance();
	}

	@Override
	public void advance() {
		if(this.data.hasNext()) {
			this.item = this.data.next();
			this.valid=true;
		} else {
			this.valid = false;
		}
	}

	@Override
	public boolean valid() {
		return this.valid;
	}

	@Override
	public Item<T> get() {
		if(!this.valid()) {
			throw new IllegalStateException("trying to read from invalid input");
		}
		return this.item;
	}

}
