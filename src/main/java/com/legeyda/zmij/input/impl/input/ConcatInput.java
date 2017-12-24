package com.legeyda.zmij.input.impl.input;

import com.legeyda.zmij.input.Input;
import com.legeyda.zmij.input.Item;

import java.util.Arrays;
import java.util.LinkedList;

public class ConcatInput<T> implements Input<T> {

	private final LinkedList<Input<T>> queue;

	@SafeVarargs
	public ConcatInput(final Input<T> ... stages) {
		this.queue = new LinkedList<>(Arrays.asList(stages));
		this.rotate();
	}

	private void rotate() {
		while(!this.queue.isEmpty() && !this.queue.peek().valid()) {
			this.queue.poll();
		}
	}

	@Override
	public boolean valid() {
		return !queue.isEmpty() && queue.peek().valid();
	}

	@Override
	public void advance() {
		if(!this.queue.isEmpty()) {
			this.queue.peek().advance();
			this.rotate();
			this.valid();
		}
	}

	@Override
	public Item<T> get() {
		if(!queue.isEmpty()) {
			return queue.peek().get();
		} else {
			throw new IllegalStateException("trying to any2value from invalid input");
		}
	}

}
