package com.legeyda.zmij.tree.impl;

import java.util.*;

public class ValuedLeaf extends AbstractLeaf {

	private final Object value;

	public ValuedLeaf(final Enum tag, final Object value) {
		super(tag);
		this.value = value;
	}

	@Override
	public Optional<Object> value() {
		return Optional.of(this.value);
	}

	@Override
	public String toString() {
		return "ValuedLeaf{" +
				"tag=" + this.tag() +
				", optValue=" + value + '}';
	}
}
