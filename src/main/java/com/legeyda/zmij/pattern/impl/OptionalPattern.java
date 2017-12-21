package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.input.Savepoint;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.passage.impl.SuppliedPassage;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.Trees;
import com.legeyda.zmij.tree.impl.AnythingAsTree;
import com.legeyda.zmij.tree.impl.ValuelessLeaf;
import com.legeyda.zmij.util.Try;
import com.legeyda.zmij.util.Util;

public class OptionalPattern<T, R> extends BasePattern<T, Tree> {

	private final Pattern<T, R> pattern;

	public OptionalPattern(final Pattern<T, R> pattern) {
		this.pattern = pattern;
	}

	@Override
	public String description() {
		return String.format("optional\n%s", Util.indent(pattern.description()));
	}

	@Override
	protected Passage<Tree> createPassage(ParsingContext<T> input) {
		final Passage<R> passage = pattern.apply(input);
		return new SuppliedPassage<>(() -> new Try<Savepoint, Result<Tree>>(save -> {
			final Result<R> result = passage.get();
			if(result.isPresent()) {
				return new Value<>(new AnythingAsTree(Tag.OPTIONAL_PRESENT, result));
			} else {
				return new Value<>(new ValuelessLeaf(Tag.OPTIONAL_ABSENT));
			}
		}).apply(input.savepoint()));
	}


}
