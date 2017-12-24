package com.legeyda.zmij.transform.impl;

import com.legeyda.zmij.transform.OptFunction;

import java.util.List;
import java.util.Optional;

public class ElementByIndex<T> implements OptFunction<List<T>, T> {

	private final int index;

	public ElementByIndex(int index) {
		this.index = index;
	}

	@Override
	public Optional<T> apply(List<T> values) {
		return index<values.size() ? Optional.of(values.get(index)) : Optional.empty();
	}

}
