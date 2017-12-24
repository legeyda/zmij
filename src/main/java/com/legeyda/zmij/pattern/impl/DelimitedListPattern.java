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

/** always greedy */
public class DelimitedListPattern<T> extends BaseDescriptionPattern<T, Tree> {

	private final Pattern<T, ?> element;
	private final Pattern<T, ?> delimiter;
	private final Integer minOccurs;
	private final Integer maxOccurs;

	public DelimitedListPattern(Pattern<T, ?> element, Pattern<T, ?> delimiter, final Integer minOccurs, final Integer maxOccurs) {
		super(String.format("list of %s delimited with %s", element.description(), delimiter.description()));
		this.element = element;
		this.delimiter = delimiter;
		this.minOccurs=minOccurs;
		this.maxOccurs=maxOccurs;
	}

	public DelimitedListPattern(Pattern<T, ?> element, Pattern<T, ?> delimiter, final Integer minOccurs) {
		this(element, delimiter, minOccurs, -1);
	}


	public DelimitedListPattern(Pattern<T, ?> element, Pattern<T, ?> delimiter) {
		this(element, delimiter, 0, -1);
	}

	@Override
	protected Passage<Tree> createPassage(ParsingContext<T> input) {
		final Passage<?> elementPassage   = this.element.apply(input);
		final Passage<?> delimiterPassage = this.delimiter.apply(input);
		return new SuppliedPassage<>(() -> {
			final List<Tree> result = new LinkedList<>();

			final Result<?> first = elementPassage.get();
			if(first.isPresent()) {
				result.add(AnythingAsTree.create(Tag.DELIMITED_ITEM, first.value()));
			} else if(minOccurs>0) {
				return createFailure(input, String.format("expected at least %d of %s", minOccurs, element.description()));
			}

			while(true) {
				if (delimiterPassage.get().isPresent()) {
					if(maxOccurs>0 && result.size()>=maxOccurs) {
						return createFailure(input, String.format("expected at most %d of %s", maxOccurs, element.description()));
					}
				} else {
					if(result.size()>=minOccurs) {
						return new Value<>(new ValuelessBranch(Tag.DELIMITED_LIST, result));
					} else {
						return createFailure(input, String.format("expected at least %d of %s", minOccurs, element.description()));
					}
				}

				final Result<?> next = elementPassage.get();
				if(next.isPresent()) {
					if(maxOccurs<0 || result.size()<maxOccurs) {
						result.add(AnythingAsTree.create(Tag.DELIMITED_ITEM, next.value()));
					} else {
						return createFailure(input, String.format("expected at most %d of %s", maxOccurs, element.description()));
					}
				} else {
					return createFailure(input, String.format("expected %s after separator", element.description()));
				}
			}
		});
	}
}
