package com.legeyda.zmij.pattern;

import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.passage.Passage;

import java.util.function.Function;

public interface Pattern<T, R> {

	/** needed? for standard patterns to be able to produce meaningful error messages */
	String description();


	Passage<R> apply(final ParsingContext<T> input);

}
