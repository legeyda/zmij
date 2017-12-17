package com.legeyda.zmij.input.impl.input;

import com.legeyda.zmij.input.Input;
import com.legeyda.zmij.input.Item;

import java.util.function.Supplier;

public class SuppliedInput<T> extends BaseStatefulInput<T> {

	private Supplier<Input<T>> supplier;

	public SuppliedInput(final Supplier<Input<T>> supplier) {
		this.supplier = supplier;
	}

	@Override
	public boolean valid() {
		this.setState(this.supplier.get());
		return super.valid();
	}

	@Override
	public void advance() {
		this.setState(this.supplier.get());
		super.advance();
	}

	@Override
	public Item<T> get() {
		this.setState(this.supplier.get());
		return super.get();
	}
}
