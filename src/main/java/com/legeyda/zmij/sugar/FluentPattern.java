package com.legeyda.zmij.sugar;

import com.google.common.base.Strings;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Failure;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.transform.impl.*;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.impl.AnythingAsTree;
import com.legeyda.zmij.tree.impl.EmptyTree;

import java.util.List;
import java.util.function.Function;

public abstract class FluentPattern<T, R> implements Pattern<T, R> {

	protected final String description;

	public FluentPattern(String description) {
		this.description = description;
	}

	public FluentPattern() {
		this("");
	}



	// pattern implementation

	abstract String defaultDescription();

	public String description() {
		return !Strings.isNullOrEmpty(this.description) ? this.description : this.defaultDescription();
	}



	// fluent pattern implementation

	abstract public FluentPattern<T, R> description(String newDescription);

	abstract public <V> FluentPattern<T, V> andThen(Function<Result<R>, Result<V>> function);

	public <V> FluentPattern<T, V> map(Function<? super R, ? extends V> function) {
		return this.andThen(result -> result.map(function) );
	}

	public <V> FluentPattern<T, V> flatMap(Function<? super R, Result<V>> function) {
		return this.andThen(result -> result.isPresent()
				? function.apply(result.value())
				: new Failure<>(result.message()));
	}


	public FluentPattern<T, Object> value() {
		return this.flatMap(new AnyToValue<>().asResultFunction("unknown failure"));
	}

	public FluentPattern<T, Object> value(final Integer index) {
		return this.values().flatMap(new ElementByIndex<>(index).asResultFunction("unable to get by index"));
	}

	public <V> FluentPattern<T, V> cast() {
		return this.flatMap(Cast.<R, V>create().asResultFunction("unknown failure"));

	}

	public <V> FluentPattern<T, V> cast(final Class<V> targetType) {
		return this.cast();
	}

	public FluentPattern<T, List<Object>> values() {
		return this.map(something -> CollectValues.INSTANCE.apply(AnythingAsTree.create(Tag.RESULT, something)));
	}

	public FluentPattern<T, String> asString() {
		return this.values().flatMap((List<Object> list) -> {
			final StringBuilder str = new StringBuilder();
			for(final Object element: list) {
				str.append(element.toString());
			}
			return new Value<>(str.toString());
		});
	}




	public <V> FluentPattern<T, V> save(final V value) {
		return this.map(whatever -> value);
	}

	public FluentPattern<T, Tree> forget() {
		return this.map(whatever -> EmptyTree.INSTANCE);
	}

}
