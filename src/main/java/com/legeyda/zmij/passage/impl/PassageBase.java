package com.legeyda.zmij.passage.impl;

import com.legeyda.zmij.input.RestoreableInput;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.result.Result;

public abstract class PassageBase<T> implements Passage<T> {

	private final RestoreableInput input;

	public PassageBase(RestoreableInput input) {
		this.input = input;
	}

	protected RestoreableInput getInput() {
		return input;
	}

	@Override
	abstract public Result<T> get();
}
