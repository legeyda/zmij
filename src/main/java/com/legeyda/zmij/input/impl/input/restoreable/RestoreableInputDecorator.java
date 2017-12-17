package com.legeyda.zmij.input.impl.input.restoreable;

import com.legeyda.zmij.input.RestoreableInput;
import com.legeyda.zmij.input.Savepoint;
import com.legeyda.zmij.input.impl.input.InputDecorator;

public class RestoreableInputDecorator<T> extends InputDecorator<T> implements RestoreableInput<T> {

	private final RestoreableInput<T> input;

	public RestoreableInputDecorator(RestoreableInput<T> input) {
		super(input);
		this.input = input;
	}

	@Override
	public Savepoint savepoint() {
		return this.input.savepoint();
	}

}
