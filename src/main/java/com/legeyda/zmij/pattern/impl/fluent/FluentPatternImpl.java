package com.legeyda.zmij.pattern.impl.fluent;

import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.pattern.FluentPattern;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Result;

import java.util.function.Function;

public class FluentPatternImpl<T, R> extends FluentPatternBase<T, R> {

	private final Pattern<T, R> pattern;

	public FluentPatternImpl(final String description, Pattern<T, R> pattern) {
		super(description);
		this.pattern = pattern;
	}

	public FluentPatternImpl(final Pattern<T, R> pattern) {
		super();
		this.pattern = pattern;
	}




	// delegate to pattern

	@Override
	String defaultDescription() {
		return this.pattern.description();
	}

	@Override
	public Passage<R> apply(final ParsingContext input) {
		return this.pattern.apply(input);
	}





	// delegate to fluent pattern

	@Override
	public FluentPattern<T, R> description(final String description) {
		return new FluentPatternImpl<>(description, this.pattern);
	}

	@Override
	public <V> FluentPattern<T, V> andThen(Function<Result<R>, Result<V>> function) {
		return new TransformedFluentPattern<>(this.description, this.pattern, function);
	}

}
