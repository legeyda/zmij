package com.legeyda.zmij.result;

import java.util.Objects;
import java.util.function.Consumer;

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
	public Result<T> ifValue(Consumer<T> valueHandler) {
		valueHandler.accept(this.value());
		return this;
	}

	@Override
	public Result<T> ifMessage(Consumer<String> errorHandler) {
		return this;
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
