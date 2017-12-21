package com.legeyda.zmij.tree.impl;

import com.legeyda.zmij.tree.Tree;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SimpleTree extends AbstractTree {

	private final Object[] value;
	private List<Tree> children;

	public SimpleTree(Enum tag) {
		super(tag);
		this.value = new Object[] {};
		this.children = Collections.emptyList();
	}

	public SimpleTree(Enum tag, Object value) {
		super(tag);
		this.value = new Object[] {value};
		this.children = Collections.emptyList();
	}

	public SimpleTree(Enum tag, List<Tree> children) {
		super(tag);
		this.value = new Object[] {};
		this.children = children;
	}

	public SimpleTree(Enum tag, Object value, List<Tree> children) {
		super(tag);
		this.value = new Object[] {value};
		this.children = children;
	}

	@Override
	public Optional<Object> value() {
		return value.length>0 ? Optional.of(value[0]) : Optional.empty();
	}

	@Override
	public List<Tree> children() {
		return this.children;
	}

}
