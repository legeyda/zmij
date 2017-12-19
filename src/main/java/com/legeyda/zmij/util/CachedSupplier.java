package com.legeyda.zmij.util;

import java.util.function.Supplier;

public class CachedSupplier<T> implements Supplier<T> {

	private final Supplier<T> supplier;
	private boolean created = false;
	private T value = null;

	public CachedSupplier(Supplier<T> supplier) {
		this.supplier = supplier;
	}

	@Override
	public T get() {
		if(!this.created) {
			this.value = this.supplier.get();
			this.created = true;
		}
		return this.value;
	}
}
