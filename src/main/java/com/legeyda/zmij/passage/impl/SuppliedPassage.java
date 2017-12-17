package com.legeyda.zmij.passage.impl;

import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.result.Result;

import java.util.function.Supplier;

public class SuppliedPassage<T> implements Passage<T> {

	private final Supplier<Result<T>> result;

	public SuppliedPassage(Supplier<Result<T>> result) {
		this.result = result;
	}

	@Override
	public Result<T> get() {
		return this.result.get();
	}

}
