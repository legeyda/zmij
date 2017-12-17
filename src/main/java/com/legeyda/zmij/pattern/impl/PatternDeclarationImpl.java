package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.pattern.PatternDeclaration;

public class PatternDeclarationImpl<T, R> implements PatternDeclaration<T, R> {

	private Pattern<T, R> implementation = new Pattern<T, R>() {
		@Override
		public String description() {
			throw new IllegalStateException("pattern definition not implemented");
		}
		@Override
		public Passage<R> apply(ParsingContext<T> input) {
			throw new IllegalStateException("pattern definition not implemented");
		}
	};
	private boolean implemented = false;

	public void implement(final Pattern<T, R> implementation) {
		if(!implemented) {
			this.implementation = implementation;
		} else {
			throw new IllegalStateException("pattern already implemented");
		}
	}

	@Override
	public String description() {
		return this.implementation.description();
	}

	@Override
	public Passage<R> apply(ParsingContext<T> input) {
		return this.implementation.apply(input);
	}
}
