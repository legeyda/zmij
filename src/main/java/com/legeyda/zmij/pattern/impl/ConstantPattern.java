package com.legeyda.zmij.pattern.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.passage.impl.SuppliedPassage;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.Trees;
import com.legeyda.zmij.tree.impl.AnythingAsTree;
import com.legeyda.zmij.tree.impl.ValuedLeaf;
import com.legeyda.zmij.tree.impl.ValuelessBranch;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ConstantPattern<T> extends BasePattern<T, Tree> {

	final Iterable<T> pattern;


	public ConstantPattern(final Iterable<T> pattern) {
		if(0 == Iterables.size(pattern)) {
			throw new IllegalArgumentException(String.format("pattern size is %d", Iterables.size(pattern)));
		}
		this.pattern = pattern;
	}

	public ConstantPattern(final T ... elements) {
		this(Arrays.asList(elements));
	}

	@Override
	protected Passage<Tree> createPassage(ParsingContext<T> input) {
		return new SuppliedPassage<>(() -> {
			for(final T item: pattern) {
				if(!input.valid() || !item.equals(input.get().value())) {
					return ConstantPattern.this.createFailure(input, String.format("%s expected", this.description()));
				}
				input.advance();
			}
			return new Value<>(new ValuelessBranch(Tag.CONSTANT,
					Streams.stream(pattern).map(t->new ValuedLeaf(Tag.TOKEN, t)).collect(Collectors.toList())));
		});
	}

	@Override
	public String description() {
		return String.format("%s", pattern)
				.replace("\b", "\\b")
				.replace("\f", "\\f")
				.replace("\n", "\\n")
				.replace("\t", "\\t")
				.replace("\r", "\\r");
	}
}
