package com.legeyda.zmij.transform;

import com.legeyda.zmij.result.Failure;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.result.Value;

import java.util.Optional;
import java.util.function.Function;

public interface ResultFunction<T, R> extends Function<T, Result<R>> {

	default <V> ResultFunction<T, V> map(Function<R, V> then) {
		return t -> this.apply(t).map(then);
	}

	default <V> ResultFunction<T, V> flatMap(ResultFunction<R, V> then) {
		return t -> {
			final Result<R> r = this.apply(t);
			if(!r.isPresent()) {
				return new Failure<>(r.message());
			}
			return then.apply(r.value());
		};
	}


	default <V> ResultFunction<T, V> flatCast(Class<V> clazz) {
		return this.flatCast();
	}

	default <V> ResultFunction<T, V> flatCast() {
		return t -> {
			final Result<R> opt = this.apply(t);
			if(!opt.isPresent()) {
				return new Failure<>(opt.message());
			}
			final R r = opt.value();
			final V v;
			try {
				v = (V) r;
			} catch (ClassCastException e) {
				return new Failure<>(opt.message());
			}
			return new Value<>(v);
		};
	};



	default Function<T, R> orElse(final R defaultValue) {
		return t -> this.apply(t).orElse(defaultValue);
	}

	default Function<T, R> orRaise() {
		return t -> this.apply(t).value();
	}

}
