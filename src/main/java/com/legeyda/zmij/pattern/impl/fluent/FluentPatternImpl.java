package com.legeyda.zmij.pattern.impl.fluent;

import com.legeyda.zmij.pattern.FluentPattern;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.pattern.impl.AnyOfPattern;
import com.legeyda.zmij.pattern.impl.ButPattern;
import com.legeyda.zmij.pattern.impl.SequencePattern;
import com.legeyda.zmij.tree.Tree;

import java.util.*;
import java.util.function.Function;

public class FluentPatternImpl<T, R> extends FluentPatternBase<T, R> {


	public FluentPatternImpl(final String description, Pattern<T, R> pattern) {
		super(description, pattern);
	}

	public FluentPatternImpl(final Pattern<T, R> pattern) {
		super(pattern);
	}



	// fluent pattern

	@Override
	public FluentPattern<T, R> description(final String description) {
		return new FluentPatternImpl<>(description, this.pattern);
	}

	@Override
	public FluentPattern<T, R> butNot(Pattern<T, ?> shouldNotMatch) {
		return new FluentPatternImpl<>(new ButPattern<>(this.pattern, shouldNotMatch));
	}

	@Override
	public FluentPattern<T, R> butNot(Pattern<T, ?>[] allShouldNotMatch) {
		return new FluentPatternImpl<>(new ButPattern<>(this.pattern, new AnyOfPattern<>(allShouldNotMatch)));
	}

	@Override
	public FluentPattern<T, Tree> andThen(Pattern<T, ?> ... others) {
		final Collection<Pattern<T, ?>> patterns = new ArrayList<>(1+others.length);
		patterns.add(this.pattern);
		patterns.addAll(Arrays.asList(others));
		return new FluentPatternImpl<>(new SequencePattern<>(this.pattern));
	}

	@Override
	public <V> FluentPattern<T, V> transform(final Function<? super R, ? extends V> function) {
		return new TransformedFluentPattern<>(this.description, this.pattern, function);
	}

}
