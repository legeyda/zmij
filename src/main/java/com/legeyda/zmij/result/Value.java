package com.legeyda.zmij.result;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class Value<T> implements Result<T> {

	private final T value;

	public Value(T value) {
		this.value = Objects.requireNonNull(value);
	}



	@Override
	public boolean isPresent() {
		return true;
	}

	@Override
	public T value() {
		return this.value;
	}

	@Override
	public String message() {
		throw new IllegalStateException("result isPresent, message is empty");
	}




	@Override
	public <V> Result<V> map(Function<? super T, ? extends V> mapping) {
		return new Value<>(mapping.apply(this.value()));
	}

	@Override
	public T orElse(T defaultValue) {
		return null;
	}



	@Override
	public boolean equals(Object o) {
		return (o instanceof Value) && ((Value)o).value().equals(this.value());
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		return this.value().toString();
	}
}
