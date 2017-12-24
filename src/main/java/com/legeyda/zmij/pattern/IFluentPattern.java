package com.legeyda.zmij.pattern;

import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.tree.Tree;

import java.util.List;
import java.util.function.Function;

@Deprecated
public interface IFluentPattern<T, R> extends Pattern<T, R> {

	/** anyToTree pattern like this but with given description */
	IFluentPattern<T, R> description(String newDescription);


	<V> IFluentPattern<T, V> andThen(Function<Result<R>, Result<V>> function);

	/**
	 * any2value new pattern returning the result of applying given function to actual result
	 */
	<V> IFluentPattern<T, V> map(Function<? super R, ? extends V> function);


	<V> IFluentPattern<T, V> flatMap(Function<? super R, Result<V>> function);


	IFluentPattern<T, Object> value();

	/**
	 * gets new pattern returning list of all values collected recursively from this pattern result tree
	 *
	 */
	IFluentPattern<T, List<Object>> listValues();

	/**
	 * gets new pattern returning Result with constant predefined optValue, whatever this pattern actually returns
	 *
	 * It can be usefull for simple patterns, where exact optValue of result is known
	 *
	 */
	<V> IFluentPattern<T, V> save(V value);


	/**
	 * gets new pattern returning Result of type EmptyTree, whatever this pattern actually returns
	 *
	 * It can be useful to skip some non-sense patterns like white spaces, punctuations etc
	 *
	 */
	IFluentPattern<T, Tree> forget();







}
