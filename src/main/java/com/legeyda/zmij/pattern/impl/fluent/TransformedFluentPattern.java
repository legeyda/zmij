package com.legeyda.zmij.pattern.impl.fluent;

import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.passage.impl.TransformedPassage;
import com.legeyda.zmij.pattern.FluentPattern;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.pattern.impl.AnyOfPattern;
import com.legeyda.zmij.pattern.impl.ButPattern;
import com.legeyda.zmij.pattern.impl.SequencePattern;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.util.CompositeFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

public class TransformedFluentPattern<T, R, RR> implements FluentPattern<T, RR> {

	private final String description;
	private final Pattern<T, R> pattern;
	private final Function<? super R, ? extends RR> function;

	public TransformedFluentPattern(final String description, Pattern<T, R> pattern, Function<? super R, ? extends RR> function) {
		this.description = description;
		this.pattern = pattern;
		this.function = function;
	}

	public TransformedFluentPattern(Pattern<T, R> wrap, Function<? super R, ? extends RR> function) {
		this(wrap.description(), wrap, function);
	}


	// pattern

	@Override
	public String description() {
		return this.description;
	}

	@Override
	public Passage<RR> apply(final ParsingContext<T> input) {
		return new TransformedPassage<>(this.pattern.apply(input), this.function);
	}

	// fluent pattern

	@Override
	public FluentPattern<T, RR> description(String newDescription) {
		return new TransformedFluentPattern<>(newDescription, this.pattern, function);
	}

	@Override
	public FluentPattern<T, RR> butNot(Pattern<T, ?> shouldNotMatch) {
		return new TransformedFluentPattern<>(new ButPattern<>(this.pattern, shouldNotMatch), this.function);
	}

	@Override
	public FluentPattern<T, RR> butNot(Pattern<T, ?>[] allShouldNotMatch) {
		return new TransformedFluentPattern<>(new ButPattern<>(this.pattern, new AnyOfPattern<>(allShouldNotMatch)), this.function);
	}


	@Override
	public FluentPattern<T, Tree> andThen(Pattern<T, ?> ... others) {
		final Collection<Pattern<T, ?>> patterns = new ArrayList<>(1+others.length);
		patterns.add(this);
		patterns.addAll(Arrays.asList(others));
		return new FluentPatternImpl<>(new SequencePattern<>(patterns));
	}

	@Override
	public <V> FluentPattern<T, V> transform(Function<? super RR, ? extends V> function) {
		return new TransformedFluentPattern<>(this.description, this.pattern, new CompositeFunction<R, RR, V>(
				this.function,
				function));
	}

}
