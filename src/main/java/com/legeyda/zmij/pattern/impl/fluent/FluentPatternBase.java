package com.legeyda.zmij.pattern.impl.fluent;

import com.google.common.base.Strings;
import com.legeyda.zmij.pattern.FluentPattern;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.transform.impl.CollectValues;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.Trees;
import com.legeyda.zmij.tree.impl.AnythingAsTree;
import com.legeyda.zmij.tree.impl.EmptyTree;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class FluentPatternBase<T, R> implements FluentPattern<T, R> {

	protected final String description;

	public FluentPatternBase(String description) {
		this.description = description;
	}

	public FluentPatternBase() {
		this("");
	}


	// delegate to pattern

	abstract String defaultDescription();

	@Override
	public String description() {
		return !Strings.isNullOrEmpty(this.description) ? this.description : this.defaultDescription();
	}





	// delegate to fluent pattern

	@Override
	abstract public FluentPattern<T, R> description(String newDescription);

	@Override
	abstract public <V> FluentPattern<T, V> andThen(Function<Result<R>, Result<V>> function);

	@Override
	public <V> FluentPattern<T, V> map(Function<? super R, ? extends V> function) {
		return this.andThen(result -> result.map(function) );
	}

	@Override
	public <V> FluentPattern<T, V> value() {
		return this.map(tree->(V) new AnythingAsTree(Tag.RESULT, tree).value().get());
	}

	@Override
	public <V> FluentPattern<T, Optional<V>> optValue() {
		return this.map(tree->(Optional<V>)new AnythingAsTree(Tag.RESULT, tree).value());
	}

	@Override
	public FluentPattern<T, List<Object>> listValues() {
		return this.map(something -> CollectValues.INSTANCE.apply(new AnythingAsTree(Tag.RESULT, something)));
	}

	@Override
	public <V> FluentPattern<T, V> save(final V value) {
		return this.map(whatever -> value);
	}

	@Override
	public FluentPattern<T, Tree> forget() {
		return this.map(whatever -> EmptyTree.INSTANCE);
	}



}
