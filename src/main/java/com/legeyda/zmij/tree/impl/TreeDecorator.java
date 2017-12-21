package com.legeyda.zmij.tree.impl;

import com.legeyda.zmij.tree.Tree;

import java.util.List;
import java.util.Optional;

public abstract class TreeDecorator implements Tree {

	private final Tree wrapee;

	public TreeDecorator(Tree wrapee) {
		this.wrapee = wrapee;
	}

	@Override
	public Enum tag() {
		return wrapee.tag();
	}

	@Override
	public Optional<Object> value() {
		return wrapee.value();
	}

	@Override
	public List<Tree> children() {
		return wrapee.children();
	}

}
