package com.legeyda.zmij.input.impl.input;

import com.legeyda.zmij.input.Input;
import com.legeyda.zmij.input.Item;

import java.util.function.Consumer;

public class ListenInput<T> extends InputDecorator<T> {

	private final Consumer<Item<T>> listener;

	public ListenInput(final Input wrap, final Consumer<Item<T>> listener) {
		super(wrap);
		this.listener = listener;
		this.onAfterMove();
	}

	@Override
	public void advance() {
		super.advance();
		this.onAfterMove();
	}

	public void onAfterMove() {
		if(this.valid()) {
			this.listener.accept(this.get());
		}
	}

}
