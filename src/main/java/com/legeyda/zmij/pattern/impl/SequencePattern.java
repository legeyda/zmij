package com.legeyda.zmij.pattern.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Streams;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.passage.impl.SuppliedPassage;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.Trees;
import com.legeyda.zmij.tree.impl.Branch;
import com.legeyda.zmij.util.PairIterable;
import com.legeyda.zmij.util.Pair;

import java.util.*;

public class SequencePattern<T> extends BaseDescriptionPattern<T, Tree> {

	private static <T> String createDescription(final Iterable<Pattern<T, ?>> patterns) {
		return "["
				+ Joiner.on(" ").skipNulls().join(Streams.stream(patterns).map(
				Pattern::description).iterator())
				+ "]";
	}

	private Iterable<Pattern<T, ?>> elements;

	public SequencePattern(final Iterable<Pattern<T, ?>> patterns) {
		super(createDescription(patterns));
		this.elements = patterns;
	}

	@SafeVarargs
	public SequencePattern(Pattern<T, ?>... patterns) {
		this(Arrays.asList(patterns));
	}

	@Override
	protected Passage<Tree> createPassage(ParsingContext<T> input) {
		final Iterable<Passage<?>> passages = this.apply(this.elements, input);
		return new SuppliedPassage<>(() -> {
			final List<Tree> result = new LinkedList<>();
			for(final Pair<Pattern<T, ?>, Passage<?>> pair: new PairIterable<>(elements, passages)) {
				final Result<?> child = pair.getB().get();
				if(child.isPresent()) {
					result.add(Trees.from(Tag.SEQUENCE_ITEM, child));
				} else {
					return this.createFailure(input, String.format("expected %s", pair.getA().description()));
				}
			}
			return new Value<>(new Branch(Tag.SEQUENCE, result));
		});
	}
}
