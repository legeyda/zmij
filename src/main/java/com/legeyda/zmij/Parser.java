package com.legeyda.zmij;

import com.legeyda.zmij.result.Result;

public interface Parser<T, R> {

	Result<R> parse(Iterable<T> input);

}
