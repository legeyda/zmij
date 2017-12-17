package com.legeyda.zmij.input.impl.input.util;

import java.util.AbstractList;
import java.util.List;

public abstract class ListAdapter<T> extends AbstractList<T> {
	private final List<T> data;

	public ListAdapter(List<T> data) {
		this.data = data;
	}

	@Override
	public T get(int i) {
		return this.data.get(i);
	}

	@Override
	public int size() {
		return this.data.size();
	}
}
