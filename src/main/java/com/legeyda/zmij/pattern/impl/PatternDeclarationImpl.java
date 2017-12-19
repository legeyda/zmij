package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.passage.impl.SuppliedPassage;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.pattern.PatternDeclaration;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.util.CachedSupplier;

import java.util.function.Function;
import java.util.function.Supplier;

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

	private final String description;

	public PatternDeclarationImpl(String description) {
		this.description = description;
	}

	public void implement(final Pattern<T, R> implementation) {
		if(!implemented) {
			this.implementation = implementation;
			this.implemented = true;
		} else {
			throw new IllegalStateException("pattern already implemented");
		}
	}

	@Override
	public String description() {
		return this.description;
	}

	@Override
	public Passage<R> apply(final ParsingContext<T> input) {
		if(!implemented){
			throw new IllegalStateException("pattern not implemented");
		}
		final Passage<R>[] holder = new Passage[1];
		return () -> {
			if(null==holder[0]) {
				holder[0] = implementation.apply(input);
			}
			return holder[0].get();
		};
	}
}
