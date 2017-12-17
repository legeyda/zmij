package com.legeyda.zmij.pattern.factory;

import com.legeyda.zmij.pattern.FluentPattern;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.tree.Tree;

public interface FluentPatternFacade<T> extends PatternFacade<T> {
	@Override
	<R> FluentPattern<T, R> from(Pattern<T, R> pattern);

	@Override
	FluentPattern<T, Tree> exact(Iterable<T> exact);

	@Override
	<R> FluentPattern<T, R> cache(Pattern<T, R> pattern);

	@Override
	<R> FluentPattern<T, R> anyOf(Pattern<T, ? extends R> ... choices);

	@Override
	<R> FluentPattern<T, Tree> sequence(Pattern<T, R> ... elements);
}
