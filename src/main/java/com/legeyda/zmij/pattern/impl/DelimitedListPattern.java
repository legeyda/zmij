package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.passage.impl.SuppliedPassage;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.Trees;
import com.legeyda.zmij.tree.impl.AnythingAsTree;

import java.util.LinkedList;
import java.util.List;

/** always greedy */
public class DelimitedListPattern<T, R> extends BaseDescriptionPattern<T, Tree> {

	private final Pattern<T, R> element;
	private final Pattern<T, ?> delimiter;
	private final Long minOccurs;
	private final Long maxOccurs;

	public DelimitedListPattern(Pattern<T, R> element, Pattern<T, ?> delimiter, final Long minOccurs, final Long maxOccurs) {
		super(String.format("list of %s delimited with %s", element.description(), delimiter.description()));
		this.element = element;
		this.delimiter = delimiter;
		this.minOccurs=minOccurs;
		this.maxOccurs=maxOccurs;
	}

	public DelimitedListPattern(Pattern<T, R> element, Pattern<T, ?> delimiter) {
		this(element, delimiter, 0L, -1L);
	}

	@Override
	protected Passage<Tree> createPassage(ParsingContext<T> input) {
		final Passage<R> elementPassage   = this.element.apply(input);
		final Passage<?> delimiterPassage = this.delimiter.apply(input);
		return new SuppliedPassage<>(() -> {
			final List<Tree> result = new LinkedList<>();
			final Result<R> first = elementPassage.get();
			if(minOccurs>0) {
				return createFailure(input, String.format("expected at least %d of %s", minOccurs, element.description()));
			}
			while(true) {
				if (!delimiterPassage.get().isPresent()) {
					if(result.size()<minOccurs) {
						return createFailure(input, String.format("expected at least %d of %s", minOccurs, element.description()));
					}
				}
				final Result<R> next = elementPassage.get();
				if(!next.isPresent()) {
					return createFailure(input, String.format("expected %s after separator", element.description()));
				}
				result.add(new AnythingAsTree(Tag.DELIMITED_ITEM, next.value()));
				if(maxOccurs<0 || result.size()>maxOccurs) {
					return createFailure(input, String.format("expected at most %d elements", element.description()));
				}
			}

			// todo fix
			//return new Value<>(new ValuelessBranch(Tag.DELIMITED_LIST, result));
		});
	}
}
