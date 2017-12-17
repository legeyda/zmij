package com.legeyda.zmij.tree.impl;

import com.legeyda.zmij.tree.Tree;

import java.util.Collections;
import java.util.List;

public abstract class AbstractLeaf extends AbstractTree {

	public AbstractLeaf(Enum tag) {
		super(tag);
	}

	@Override
	public List<Tree> children() {
		return Collections.emptyList();
	}

}
