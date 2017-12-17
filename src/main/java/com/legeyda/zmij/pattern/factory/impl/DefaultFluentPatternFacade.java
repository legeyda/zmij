package com.legeyda.zmij.pattern.factory.impl;

import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.pattern.impl.MemoizingPattern;

public class DefaultFluentPatternFacade<T> extends FluentPatternFacadeBase<T> {

	@Override
	protected <R> Pattern<T, R> wrap(final Pattern<T, R> wrapee) {
		return new MemoizingPattern<>(wrapee);
	}

}
