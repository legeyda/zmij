package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.passage.impl.SuppliedPassage;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.Trees;
import com.legeyda.zmij.tree.impl.AnythingAsTree;
import com.legeyda.zmij.tree.impl.ValuelessBranch;

import java.util.LinkedList;
import java.util.List;

public class ZeroOrMorePattern<T> extends BasePattern<T, Tree> {

	private final Pattern<T, ?> pattern;

	public ZeroOrMorePattern(Pattern<T, ?> pattern) {
		this.pattern = pattern;
	}

	@Override
	public String description() {
		return String.format("zero or more of %s", pattern.description());
	}

	@Override
	protected Passage<Tree> createPassage(ParsingContext<T> input) {
		final Passage<?> passage = pattern.apply(input);
		return new SuppliedPassage<>(() -> {
			final List<Tree> children = new LinkedList<>();
			Result<?> result;
			while((result = passage.get()).isPresent()) {
				children.add(AnythingAsTree.create(Tag.REPEAT_ITEM, result.value()));
			}
			return new Value<>(new ValuelessBranch(Tag.REPEAT, children));
		});
	}

}
