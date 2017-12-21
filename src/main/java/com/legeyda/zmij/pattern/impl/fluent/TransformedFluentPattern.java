package com.legeyda.zmij.pattern.impl.fluent;

import com.google.common.base.Strings;
import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.passage.impl.TransformedPassage;
import com.legeyda.zmij.pattern.FluentPattern;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.impl.EmptyTree;
import com.legeyda.zmij.transform.impl.CompositeFunction;

import java.util.function.Function;

public class TransformedFluentPattern<T, R, RR> extends FluentPatternBase<T, RR> {

	private final String description;
	private final Pattern<T, R> pattern;
	private final Function<Result<R>, Result<RR>> function;

	public TransformedFluentPattern(final String description, Pattern<T, R> pattern, Function<Result<R>, Result<RR>> function) {
		super(description);
		this.description = description;
		this.pattern = pattern;
		this.function = function;
	}

	public TransformedFluentPattern(Pattern<T, R> pattern, Function<Result<R>, Result<RR>> function) {
		super();
		this.description = "";
		this.pattern = pattern;
		this.function = function;
	}


	// delegate to pattern

	@Override
	String defaultDescription() {
		return this.pattern.description();
	}

	@Override
	public String description() {
		return !Strings.isNullOrEmpty(this.description) ? this.description : this.pattern.description();
	}

	@Override
	public Passage<RR> apply(final ParsingContext<T> input) {
		return new TransformedPassage<R, RR>(this.pattern.apply(input), this.function);
	}

	// fluent pattern

	@Override
	public FluentPattern<T, RR> description(String newDescription) {
		return new TransformedFluentPattern<>(newDescription, this.pattern, function);
	}

	@Override
	public <V> FluentPattern<T, V> andThen(Function<Result<RR>, Result<V>> function) {
		return new TransformedFluentPattern<>(
				this.description,
				this.pattern,
				new CompositeFunction<Result<R>, Result<RR>, Result<V>>(
						this.function,
						function));
	}


	@Override
	public <V> FluentPattern<T, V> save(final V value) {
		return new TransformedFluentPattern<>(
				this.description, this.pattern, whatever -> new Value<>(value));
	}

	@Override
	public FluentPattern<T, Tree> forget() {
		return new TransformedFluentPattern<>(
				this.description, this.pattern, whatever -> new Value<>(EmptyTree.INSTANCE));
	}

}
