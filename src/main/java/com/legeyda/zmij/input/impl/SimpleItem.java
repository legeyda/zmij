package com.legeyda.zmij.input.impl;

import com.legeyda.zmij.input.Item;

import java.util.Objects;

public class SimpleItem<T> implements Item<T> {

	private final T value;
	private final Long position;

	public SimpleItem(final T value, final Long position) {
		this.value = value;
		this.position = position;
	}

	public T value() {
		return value;
	}

	public Long position() {
		return position;
	}

	@Override
	public String toString() {
		return String.format("[#%d: %s]", this.position, this.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.value, this.position);
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Item)) {
			return false;
		}
		final Item item = (Item)o;

		return Objects.equals(item.value(), this.value())
			&& Objects.equals(item.position(), this.position());

	}


}
