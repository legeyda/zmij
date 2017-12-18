package com.legeyda.zmij.pattern.impl.fluent;

import com.google.common.base.Strings;
import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.pattern.FluentPattern;
import com.legeyda.zmij.pattern.Pattern;

import java.util.function.Function;

public abstract class FluentPatternBase<T, R> implements FluentPattern<T, R> {

	protected final String description;
	protected final Pattern<T, R> pattern;

	public FluentPatternBase(String description, Pattern<T, R> pattern) {
		this.description = description;
		this.pattern = pattern;
	}

	public FluentPatternBase(Pattern<T, R> pattern) {
		this("", pattern);
	}

	// input

	@Override
	public String description() {
		return !Strings.isNullOrEmpty(this.description) ? this.description : this.pattern.description();
	}

	@Override
	public Passage<T> apply(final ParsingContext input) {
		return this.pattern.apply(input);
	}



	// fluent input

	@Override
	abstract public FluentPattern<T, R> description(String newDescription);

	@Override
	abstract public <V> FluentPattern<T, V> transform(Function<? super R, ? extends V> function);

}
