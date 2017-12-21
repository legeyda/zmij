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

/** match pattern repeatedly based on predicates of match count */
public class RepeatPattern<T> extends BasePattern<T, Tree> {

	private final Pattern<T, ?> pattern;
	private final Integer minOccurs;
	private final Integer maxOccurs;
	private final boolean greedy;

	public RepeatPattern(Pattern<T, ?> pattern, Integer minOccurs, Integer maxOccurs, boolean greedy) {
		this.pattern = pattern;
		this.minOccurs = minOccurs;
		this.maxOccurs = maxOccurs;
		this.greedy = greedy;
	}

	@Override
	public String description() {
		return String.format("%s occurs min %d max %d times", pattern.description(), minOccurs, maxOccurs);
	}

	@Override
	protected Passage<Tree> createPassage(ParsingContext<T> input) {
		final Passage<?> passage = pattern.apply(input);
		return new SuppliedPassage<>(() -> {
			final List<Tree> found = new LinkedList<>();
			Result<?> result;
			while((result = passage.get()).isPresent()) {
				found.add(new AnythingAsTree(Tag.REPEAT_ITEM, result.value()));

				if(!this.greedy && this.maxOccurs == found.size()) {
					break;
				}
				if(found.size()>this.maxOccurs) {
					break;
				}
			}
			if(found.size()>=this.minOccurs && found.size()<=this.maxOccurs) {
				return new Value<>(new ValuelessBranch(Tag.REPEAT, found));
			} else {
				return this.createFailure(input, String.format("got %d repetitions", found.size()));
			}
		});
	}
}
