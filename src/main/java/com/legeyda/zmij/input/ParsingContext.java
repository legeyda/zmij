package com.legeyda.zmij.input;

import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.pattern.Pattern;

import java.util.function.Supplier;

public interface ParsingContext<T> extends RestoreableInput<T> {

	<R> Passage<R> getOrCreatePassage(final Pattern<T, R> key, Supplier<Passage<R>> generator);

}
