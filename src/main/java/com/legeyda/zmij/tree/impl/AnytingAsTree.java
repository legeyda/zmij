package com.legeyda.zmij.tree.impl;

import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;

public class AnytingAsTree extends TreeDecorator {

	private static Tree any2tree(final Tag tag, final Object anything) {
		return anything instanceof Tree
				? (Tree)anything
				: new ValuedLeaf(Tag.RESULT, anything);
	}

	public AnytingAsTree(final Tree wrapee) {
		super(wrapee);
	}

	public AnytingAsTree(final Tag tag, final Object anything) {
		this(any2tree(tag, anything));
	}
//
//	public AnytingAsTree(final Object anything) {
//		this(any2tree(anything));
//	}



}
