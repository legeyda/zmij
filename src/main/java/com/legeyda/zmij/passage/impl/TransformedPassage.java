package com.legeyda.zmij.passage.impl;

import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.result.Failure;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.result.Value;

import java.util.function.Function;

public class TransformedPassage<T, R> implements Passage<R> {

	private final Passage<T> passage;
	private final Function<? super T, ? extends R> function;

	public TransformedPassage(Passage<T> passage, Function<? super T, ? extends R> function) {
		this.passage = passage;
		this.function = function;
	}

	@Override
	public Result<R> get() {
		final Result<? extends T> result = this.passage.get();
		if(result.isPresent()) {
			return new Value<>(this.function.apply(result.value()));
		} else {
			return new Failure<>(result.message());
		}
	}

}
