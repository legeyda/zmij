package com.legeyda.zmij.pattern;

import com.legeyda.zmij.tree.Tree;

import java.util.List;
import java.util.function.Function;

public interface FluentPattern<T, R> extends Pattern<T, R> {
	FluentPattern<T, R> description(String newDescription);
	FluentPattern<T, R> butNot(Pattern<T, ?> shouldNotMatch);
	FluentPattern<T, R> butNot(Pattern<T, ?> ... allShouldNotMatch);
	FluentPattern<T, Tree> andThen(Pattern<T, ?> ... others);



	// transformation of result to ast or something

	/** transform pattern result to someting other */
	<RR> FluentPattern<T, RR> transform(Function<? super R, ? extends RR> function);

	/** recursively walk tree and collect all found values into list */
	FluentPattern<T, List<Object>> collectValues();

	/** set constant value to be returned in case of match */
	<RR> FluentPattern<T, RR> save(RR value);

	/** forget pattern result and return just empty tree */
	FluentPattern<T, Tree> forget();

}
