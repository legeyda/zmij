package com.legeyda.zmij.tree.impl;

import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AnythingAsTree extends TreeDecorator {

	private static Tree any2tree(final Tag tag, final Object anything) {
		if(anything instanceof Tree) {
			return (Tree)anything;
		} else if(anything instanceof Iterable<?>) {
			final List<Tree> children = new LinkedList<>();
			for (final Object child : (Iterable) anything) {
				children.add(any2tree(Tag.ITEM, child));
			}
			return new ValuelessBranch(tag, children);
		} else if(anything instanceof Result<?>) {
			if(((Result<?>)anything).isPresent()) {
				return any2tree(tag,((Result<?>)anything).value());
			} else {
				return EmptyTree.INSTANCE;
			}
		} else if(anything instanceof Optional<?>) {
			if(((Optional<?>)anything).isPresent()) {
				return any2tree(tag,((Optional<?>)anything).get());
			} else {
				return EmptyTree.INSTANCE;
			}
		} else {
			return new ValuedLeaf(tag, anything);
		}
	}

	public AnythingAsTree(final Tag tag, final Object anything) {
		super(any2tree(tag, anything));
	}

	public AnythingAsTree(final Tree wrapee) {
		this(Tag.VALUE, wrapee);
	}


}
