package com.legeyda.zmij.pattern.factory.impl;

import com.legeyda.zmij.pattern.FluentPattern;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.pattern.factory.FluentPatternFacade;
import com.legeyda.zmij.pattern.impl.AnyOfPattern;
import com.legeyda.zmij.pattern.impl.ConstantPattern;
import com.legeyda.zmij.pattern.impl.MemoizingPattern;
import com.legeyda.zmij.pattern.impl.SequencePattern;
import com.legeyda.zmij.pattern.impl.fluent.FluentPatternImpl;
import com.legeyda.zmij.tree.Tree;

public abstract class FluentPatternFacadeBase<TokenT> implements FluentPatternFacade<TokenT> {

	protected abstract <TreeT> Pattern<TokenT, TreeT> wrap(final Pattern<TokenT, TreeT> wrapee);

	@Override
	public <R> FluentPattern<TokenT, R> from(Pattern<TokenT, R> pattern) {
		return new FluentPatternImpl<>(this.wrap(pattern));
	}

	@Override
	public FluentPattern<TokenT, Tree> exact(Iterable<TokenT> exact) {
		return new FluentPatternImpl<>(this.wrap(new ConstantPattern<>(exact)));
	}

	@Override
	public <R> FluentPattern<TokenT, R> cache(Pattern<TokenT, R> pattern) {
		return new FluentPatternImpl<>(this.wrap(new MemoizingPattern<>(pattern)));
	}

	@Override
	@SafeVarargs
	final public <R> FluentPattern<TokenT, R> anyOf(Pattern<TokenT, ? extends R> ... choices) {
		return new FluentPatternImpl<>(this.wrap(new AnyOfPattern<>(choices)));
	}

	@Override
	@SafeVarargs
	final public <R> FluentPattern<TokenT, Tree> sequence(Pattern<TokenT, R> ... elements) {
		return new FluentPatternImpl<>(this.wrap(new SequencePattern<>(elements)));
	}

}
