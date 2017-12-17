package com.legeyda.zmij.input.impl.input;

import com.legeyda.zmij.input.Input;
import com.legeyda.zmij.input.Item;

import java.util.List;


public class GivenInput<T> implements Input<T> {

	private final List<Item<T>> data;
	private int index = 0;

	public GivenInput(final List<Item<T>> data, final long initialPosition) {
		this.data = data;
		this.index = Long.valueOf(initialPosition).intValue();
	}

	public GivenInput(final List<Item<T>> data) {
		this(data, 0);
	}

	@Override
	public boolean valid() {
		return this.index >=0 && this.index < this.data.size();
	}

	@Override
	public void advance() {
		this.index++;
	}

	@Override
	public Item<T> get() {
		if(this.valid()) {
			return this.data.get(index);
		} else {
			throw new IllegalStateException("trying to read from invalid input");
		}
	}

}
