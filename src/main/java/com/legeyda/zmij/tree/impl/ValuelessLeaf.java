package com.legeyda.zmij.tree.impl;

import java.util.Optional;

public class ValuelessLeaf extends AbstractLeaf {

	public ValuelessLeaf(Enum tag) {
		super(tag);
	}

	@Override
	public Optional<Object> value() {
		return Optional.empty();
	}

	@Override
	public String toString() {
		return "ValuelessLeaf{" +
				"tag=" + this.tag() + '}';
	}

}
