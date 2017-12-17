package com.legeyda.zmij.util;

import com.google.common.base.Function;

public class Try<T extends AutoCloseable, R> implements Function<T, R> {

	private final Function<? super T, ? extends R> consumer;

	public Try(Function<? super T, ? extends R> consumer) {
		this.consumer = consumer;
	}

	@Override
	public R apply(T resource) {
		try {
			return this.consumer.apply(resource);
		} finally {
			try {
				resource.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}



}
