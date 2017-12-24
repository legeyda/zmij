package com.legeyda.zmij;

import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.transform.ResultFunction;

public interface Parser<T, R> extends ResultFunction<Iterable<T>, R> {

	Result<R> parse(Iterable<T> input);

}
