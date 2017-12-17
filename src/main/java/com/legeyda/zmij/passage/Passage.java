package com.legeyda.zmij.passage;

import com.legeyda.zmij.result.Result;

import java.util.function.Supplier;

/** accotiating pattern with particular input */
public interface Passage<R> extends Supplier<Result<R>> {

	/** match pattern and return result */
	Result<R> get();

}
