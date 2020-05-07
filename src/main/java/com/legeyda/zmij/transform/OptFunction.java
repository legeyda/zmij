package com.legeyda.zmij.transform;

import com.legeyda.zmij.result.Failure;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.result.Value;

import java.util.Optional;
import java.util.function.Function;

/** function that returns optional */
public interface OptFunction<T, R> extends FluentFunction<T, Optional<R>> {

	static <T, R> OptFunction<T, R> from(final Function<T, Optional<R>> function) {
		return function::apply;
	}

	default <V> OptFunction<T, V> map(Function<? super R, ? extends V> then) {
		return t -> this.apply(t).map(then);
	}

	default <V> OptFunction<T, V> flatMap(OptFunction<? super R, V> then) {
		return t -> this.apply(t).flatMap(then::apply);
	}

	default <V> OptFunction<T, V> flatMap(Function<? super R, V> then) {
		return t -> this.apply(t).flatMap(v -> Optional.of(then.apply(v)));
	}

	default <V> OptFunction<T, V> flatCast(Class<V> clazz) {
		return this.flatCast();
	}

	default <V> OptFunction<T, V> flatCast() {
		return t -> {
			final Optional<R> opt = this.apply(t);
			if(!opt.isPresent()) {
				return Optional.empty();
			}
			final R r = opt.get();
			final V v;
			try {
				v = (V) r;
			} catch (ClassCastException e) {
				return Optional.empty();
			}
			return Optional.of(v);
		};
	};

	default OptFunction<T, String> flatAsString() {
		return this.flatMap(v->Optional.of(v.toString()));
	}



	default FluentFunction<T, R> orElse(final R defaultValue) {
		return t -> this.apply(t).orElse(defaultValue);
	}

	default FluentFunction<T, R> orRaise() {
		return t -> this.apply(t).get();
	}

	default ResultFunction<T, R> asResultFunction(final String message) {
		return (T t) -> this.apply(t)
				.map(r->(Result<R>)new Value<>(r))
				.orElse(new Failure<>(message));
	}
}
