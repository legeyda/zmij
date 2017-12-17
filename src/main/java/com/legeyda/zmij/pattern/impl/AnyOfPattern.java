package com.legeyda.zmij.pattern.impl;

import com.google.common.base.Joiner;
import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.input.Savepoint;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.passage.impl.SuppliedPassage;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.util.Util;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class AnyOfPattern<T, R> extends BaseDescriptionPattern<T, R> {

	private final Iterable<Pattern<T, ? extends R>> choices;

	@SafeVarargs
	public AnyOfPattern(Pattern<T, ? extends R> ... choices) {
		super(String.format("any of:\n%s", Util.indent(Joiner.on(", \n").skipNulls().join(
				Arrays.stream(choices).map(Pattern::description).collect(Collectors.toList())))));
		this.choices = Arrays.asList(choices);
	}

	@Override
	protected Passage<R> createPassage(ParsingContext<T> input) {
		final Iterable<Passage<? extends R>> passages = this.apply(this.choices, input);
		return new SuppliedPassage<>(() -> {
			try(final Savepoint save = input.savepoint()) {
				for(final Passage<? extends R> passage: passages) {
					final Result<? extends R> result = passage.get();
					if(result.isPresent()) {
						return new Value<>(result.value());
					} else {
						save.restore();
					}
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return createFailure(input, String.format("expected %s", description()));
		});
	}

}
