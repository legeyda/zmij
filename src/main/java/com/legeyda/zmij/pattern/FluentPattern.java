package com.legeyda.zmij.pattern;

import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.tree.Tree;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface FluentPattern<T, R> extends Pattern<T, R> {

	/** change pattern description */
	FluentPattern<T, R> description(String newDescription);

	<V> FluentPattern<T, V> andThen(Function<Result<R>, Result<V>> function);

	/**
	 * get new pattern returning the result of applying given function to actual result
	 *
	 */
	<V> FluentPattern<T, V> map(Function<? super R, ? extends V> function);

	/**
	 * gets new pattern returning Result with constant predefined optValue, whatever this pattern actually returns
	 *
	 * It can be usefull for simple patterns, where exact optValue of result is known
	 *
	 */
	<V> FluentPattern<T, V> save(V value);


	/**
	 * gets new pattern returning Result of type EmptyTree, whatever this pattern actually returns
	 *
	 * It can be useful to skip some non-sense patterns like white spaces, punctuations etc
	 *
	 */
	FluentPattern<T, Tree> forget();








	<RR> FluentPattern<T, RR> value();

	<RR> FluentPattern<T, Optional<RR>> optValue();

	/**
	 * gets new pattern returning list of all values collected recursively from this pattern result tree
	 *
	 */
	FluentPattern<T, List<Object>> listValues();

}
