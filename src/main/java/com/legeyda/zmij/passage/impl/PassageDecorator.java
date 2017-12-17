package com.legeyda.zmij.passage.impl;

import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.result.Result;

public abstract class PassageDecorator<T> implements Passage<T> {

	private final Passage<T> passage;

	public PassageDecorator(Passage<T> passage) {
		this.passage = passage;
	}

	@Override
	public Result<T> get() {
		return passage.get();
	}
}
