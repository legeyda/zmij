package com.legeyda.zmij.tree.impl;

import com.legeyda.zmij.tree.Tree;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Branch extends AbstractTree {

	private final List<Tree> children;

	public Branch(Enum tag, List<Tree> children) {
		super(tag);
		this.children = children;
	}

	public Branch(Enum tag) {
		this(tag, Collections.emptyList());
	}

	@Override
	public Optional<Object> value() {
		return Optional.empty();
	}

	@Override
	public List<Tree> children() {
		return this.children;
	}

	@Override
	public String toString() {
		return "Branch{" + "tag=" + this.tag() + ", # of children: " + this.children().size() + '}';
	}
}
