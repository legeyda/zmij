package com.legeyda.zmij.tree.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.legeyda.zmij.tree.Tree;

import java.util.Objects;

public abstract class AbstractTree implements Tree {

	private final Enum tag;

	public AbstractTree(final Enum tag) {
		this.tag = tag;
	}

	@Override
	public Enum tag() {
		return this.tag;
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof Tree)
				&& Objects.equals(this.tag(),      ((Tree)o).tag())
				&& Iterables.elementsEqual(this.children(), ((Tree)o).children())
				&& Objects.equals(this.value(),    ((Tree)o).value());
	}

	@Override
	public int hashCode() {
		return 31 * tag().hashCode() + this.value().hashCode() + this.children().hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder();
		result.append("Tree{tag: ");
		result.append(this.tag);
		if(this.value().isPresent()) {
			result.append(", value: ");
			result.append(this.value().get());
		}
		if(children().size()>0) {
			result.append(", ");
			result.append("# of children: ");
			result.append(this.children().size());
		}
		result.append("}");
		return result.toString();
	}
}
