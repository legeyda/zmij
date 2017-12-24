package com.legeyda.zmij.sugar;

import com.google.common.collect.Lists;
import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.input.impl.context.ParsingContextImpl;
import com.legeyda.zmij.input.impl.input.restoreable.GivenRestoreableInput;
import com.legeyda.zmij.input.impl.input.util.ItemList;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Result;

import java.util.function.Supplier;

public class Parser<T, R> {

	protected final Pattern<T, R> pattern;

	public Parser(final Supplier<Pattern<T, R>> pattern) {
		this.pattern = pattern.get();
	}

	public Parser(final Pattern<T, R> pattern) {
		this.pattern = pattern;
	}

	public Result<R> parse(final Iterable<T> input) {
		return this.pattern.apply(this.getContext(input)).get();
	}

	protected ParsingContext<T> getContext(final Iterable<T> data) {
		// todo support streaming input
		return new ParsingContextImpl<>(new GivenRestoreableInput<>(
						new ItemList<>(Lists.newArrayList(data))));
	}

}
