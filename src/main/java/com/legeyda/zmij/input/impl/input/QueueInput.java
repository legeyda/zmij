package com.legeyda.zmij.input.impl.input;

import com.legeyda.zmij.input.Input;
import com.legeyda.zmij.input.Item;

import java.util.Queue;

public class QueueInput<T> implements Input<T> {
	private final Queue<Item<T>> data;

	public QueueInput(Queue<Item<T>> data) {
		this.data = data;
	}

	@Override
	public boolean valid() {
		return !this.data.isEmpty();
	}

	@Override
	public void advance() {
		this.data.poll();
	}

	@Override
	public Item<T> get() {
		if(!this.valid()) {
			throw new IllegalStateException("trying to any2value from invalid input");
		}
		return this.data.peek();
	}
}

