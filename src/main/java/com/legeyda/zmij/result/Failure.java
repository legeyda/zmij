package com.legeyda.zmij.result;

import com.legeyda.zmij.transform.OptFunction;

import java.util.function.Consumer;
import java.util.function.Function;

public class Failure<T> implements Result<T> {

	private final String message;

	public Failure(String message) {
		this.message = message;
	}

	public Failure(final Failure<?> failure) {
		this.message = failure.message();
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
	public <V> Result<V> map(Function<? super T, ? extends V> mapping) {
		return new Failure<>(this.message());
	}

	@Override
	public T orElse(T defaultValue) {
		return defaultValue;
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
