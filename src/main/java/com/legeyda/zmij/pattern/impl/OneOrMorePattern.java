package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.passage.impl.SuppliedPassage;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.impl.Branch;
import com.legeyda.zmij.tree.impl.ValuedLeaf;
import com.legeyda.zmij.util.Util;

import java.util.LinkedList;
import java.util.List;

public class OneOrMorePattern<T> extends BaseDescriptionPattern<T, Tree> {

	private final Pattern<T, ?> pattern;

	public OneOrMorePattern(Pattern<T, ?> pattern) {
		super(String.format("one or more of\n%s", Util.indent(pattern.description())));
		this.pattern = pattern;
	}

	@Override
	protected Passage<Tree> createPassage(final ParsingContext<T> input) {
		final Passage<?> passage = pattern.apply(input);
		return new SuppliedPassage<>(() -> {
			final List<Tree> children = new LinkedList<>();
			Result<?> result;
			while((result = passage.get()).isPresent()) {
				children.add(new ValuedLeaf(Tag.RESULT, result.value()));
			}
			if(0<children.size()) {
				return new Value<>(new Branch(Tag.REPEAT, children));
			} else {
				return this.createFailure(input, String.format("at least single <%s> expected", this.pattern.description()));
			}
		});
	}

}
