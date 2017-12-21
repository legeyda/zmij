package com.legeyda.zmij.result;

import java.util.function.Consumer;

public class Failure<T> implements Result<T> {

	private final String message;

	public Failure(String message) {
		this.message = message;
	}

	@Override
	public boolean isPresent() {
		return false;
	}

	@Override
	public T value() {
		throw new IllegalStateException("no optValue");
	}

	@Override
	public String message() {
		return this.message;
	}

	@Override
	public Result<T> ifValue(Consumer<T> valueHandler) {
		return this;
	}

	@Override
	public Result<T> ifMessage(Consumer<String> errorHandler) {
		errorHandler.accept(this.message());
		return this;
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof Failure) && ((Failure)o).message().equals(this.message());
	}

	@Override
	public int hashCode() {
		return this.message.hashCode();
	}

	@Override
	public String toString() {
		return "Failure{" + message + '}';
	}
}
