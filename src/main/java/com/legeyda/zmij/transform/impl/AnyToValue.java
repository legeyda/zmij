package com.legeyda.zmij.transform.impl;

import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.transform.OptFunction;
import com.legeyda.zmij.tree.Tree;

import java.util.Iterator;
import java.util.Optional;

public class AnyToValue<T> implements OptFunction<T, Object> {

	public static AnyToValue<Object> INSTANCE = new AnyToValue<>();

	public static Optional<Object> any2value(final Object anything) {
		if(anything instanceof Tree) {
			final Tree tree = ((Tree)anything);
			final Optional<Object> value = any2value(tree.value());
			if(value.isPresent()) {
				return value;
			}
			for(final Tree child: tree.children()) {
				final Optional<Object> childValue = any2value(child);
				if(childValue.isPresent()) {
					return childValue;
				}
			}
			return Optional.empty();
		} else if(anything instanceof Iterable) {
			final Iterator<?> iter = ((Iterable<?>)anything).iterator();
			if(iter.hasNext()) {
				return AnyToValue.any2value(iter.next());
			} else {
				return Optional.empty();
			}
		} else if(anything instanceof Optional) {
			final Optional<Object> opt = (Optional<Object>)anything;
			if(opt.isPresent()) {
				return any2value(opt.get());
			} else {
				return opt;
			}
		} else if(anything instanceof Result) {
			final Result<Object> result = (Result<Object>)anything;
			if(result.isPresent()) {
				return any2value(result.value());
			} else {
				return Optional.empty();
			}
		} else {
			return Optional.ofNullable(anything);
		}
	}

	public static <T> AnyToValue<T> create() {
		return (AnyToValue<T>) INSTANCE;
	}

	public static <T> AnyToValue<T> create(final Class<T> sourceType) {
		return (AnyToValue<T>) INSTANCE;
	}

	public AnyToValue() {}

	public AnyToValue(final Class<T> sourceType) {}

	@Override
	public Optional<Object> apply(final T anything) {
		return any2value(anything);
	}


}
