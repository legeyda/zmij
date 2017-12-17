package com.legeyda.zmij.input.impl.input;

import com.legeyda.zmij.input.Input;
import com.legeyda.zmij.input.Item;

public class BaseStatefulInput<T> implements Input<T> {

	private Input<T> state;

	public BaseStatefulInput(Input initialState) {
		this.state = initialState;
	}

	public BaseStatefulInput() {
		this(new EmptyInput<>());
	}

	protected void setState(final Input state) {
		this.state = state;
	}

	public Input getState() {
		return state;
	}

	@Override
	public boolean valid() {
		return state.valid();
	}

	@Override
	public void advance() {
		state.advance();
	}

	@Override
	public Item<T> get() {
		return state.get();
	}

}
