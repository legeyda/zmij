package com.legeyda.zmij.pattern;

import com.legeyda.zmij.tree.Tree;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface FluentPattern<T, R> extends Pattern<T, R> {
	FluentPattern<T, R> description(String newDescription);
	FluentPattern<T, R> butNot(Pattern<T, ?> shouldNotMatch);
	FluentPattern<T, R> butNot(Pattern<T, ?> ... allShouldNotMatch);
	FluentPattern<T, Tree> andThen(Pattern<T, ?> ... others);



	// transformation of result to ast or something

	/**
	 * get new pattern returning the result of applying given function to actual result
	 *
	 */
	<RR> FluentPattern<T, RR> map(Function<? super R, ? extends RR> function);

	<RR> FluentPattern<T, RR> value();

	<RR> FluentPattern<T, Optional<RR>> optValue();

	/**
	 * gets new pattern returning list of all values collected recursively from this pattern result tree
	 *
	 */
	FluentPattern<T, List<Object>> listValues();

	/**
	 * gets new pattern returning Result with constant predefined optValue, whatever this pattern actually returns
	 *
	 * It can be usefull for simple patterns, where exact optValue of result is known
	 *
	 */
	<RR> FluentPattern<T, RR> save(RR value);

	/**
	 * gets new pattern returning Result of type EmptyTree, whatever this pattern actually returns
	 *
	 * It can be useful to skip some non-sense patterns like white spaces, punctuations etc
	 *
	 */
	FluentPattern<T, Tree> forget();

}
