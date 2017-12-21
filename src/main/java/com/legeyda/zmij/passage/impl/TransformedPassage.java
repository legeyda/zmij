package com.legeyda.zmij.passage.impl;

import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.result.Failure;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.result.Value;

import java.util.function.Function;

public class TransformedPassage<T, R> implements Passage<R> {

	private final Passage<T> passage;
	private final Function<Result<T>, Result<R>> function;

	public TransformedPassage(Passage<T> passage, Function<Result<T>, Result<R>> function) {
		this.passage = passage;
		this.function = function;
	}

	@Override
	public Result<R> get() {
		return this.function.apply(this.passage.get());
	}

}
