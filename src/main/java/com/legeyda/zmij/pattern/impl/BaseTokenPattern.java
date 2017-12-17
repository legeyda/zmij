package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.passage.impl.SuppliedPassage;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.impl.ValuedLeaf;

import java.util.function.Predicate;

public class BaseTokenPattern<T> extends BaseDescriptionPattern<T, Tree> {

	final Predicate<T> predicate;

	public BaseTokenPattern(final String description, final Predicate<T> predicate) {
		super(description);
		this.predicate = predicate;
	}

	@Override
	protected Passage<Tree> createPassage(final ParsingContext<T> input) {
		return new SuppliedPassage<>(() -> {
			if(input.valid()) {
				final T value = input.get().value();
				if(this.predicate.test(value)) {
					input.advance();
					return new Value<>(new ValuedLeaf(Tag.TOKEN, value));
				}
			}
			return createFailure(input, String.format("expected %s", this.description()));
		});
	}

}
