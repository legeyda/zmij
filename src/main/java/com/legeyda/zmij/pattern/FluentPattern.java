package com.legeyda.zmij.pattern;

import com.legeyda.zmij.tree.Tree;

import java.util.function.Function;
// element.andThenEvery(zeroOrMore(sequence(comma, element)))
public interface FluentPattern<T, R> extends Pattern<T, R> {
	FluentPattern<T, R> description(String newDescription);
	FluentPattern<T, R> butNot(Pattern<T, ?> shouldNotMatch);
	FluentPattern<T, R> butNot(Pattern<T, ?> ... allShouldNotMatch);
	FluentPattern<T, Tree> andThen(Pattern<T, ?> ... others);
	<RR> FluentPattern<T, RR> transform(Function<? super R, ? extends RR> function);

}
