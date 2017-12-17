package com.legeyda.zmij.pattern.factory;

import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.tree.Tree;

public interface PatternFacade<T> {

	<R> Pattern<T, R> from(Pattern<T, R> pattern);

	Pattern<T, Tree> exact(Iterable<T> exact);

	<R> Pattern<T, R> cache(Pattern<T, R> pattern);

	<R> Pattern<T, R> anyOf(Pattern<T, ? extends R> ... choices);

	<R> Pattern<T, Tree> sequence(Pattern<T, R> ... elements);

}
