package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.input.Savepoint;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.passage.impl.SuppliedPassage;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.util.Try;


public class ButPattern<T, R> extends BaseDescriptionPattern<T, R> {
	private final Pattern<T, R> shouldMatch;
	private final Pattern<T, ?> shouldNotMatch;

	public ButPattern(Pattern<T, R> shouldMatch, Pattern<T, ?> shouldNotMatch) {
		super(String.format("%s but %s", shouldMatch.description(), shouldNotMatch.description()));
		this.shouldMatch = shouldMatch;
		this.shouldNotMatch = shouldNotMatch;
	}

	@Override
	protected Passage<R> createPassage(ParsingContext<T> input) {
		final Passage<R> shouldMatchPassage    = this.shouldMatch.apply(input);
		final Passage<?> shouldNotMatchPassage = this.shouldNotMatch.apply(input);
		return new SuppliedPassage<>(() -> new Try<Savepoint, Result<R>>(save -> {
			final Result<R> should = shouldMatchPassage.get();
			if(!should.isPresent()) {
				return createFailure(input, String.format(
						"expected %s",
						ButPattern.this.description()));
			} else {
				return new Try<Savepoint, Result<R>>(save2 -> {
					save.restore();
					final Result<?> shouldNot = shouldNotMatchPassage.get();
					if (shouldNot.isPresent()) {
						save.restore();
						final Result<R> result = createFailure(input, String.format("expected %s", description()));
						save2.restore();
						return result;
					} else {
						save2.restore();
						return should;
					}
				}).apply(input.savepoint());
			}
		}).apply(input.savepoint()));
	}
}
