package com.legeyda.zmij.transform.impl;

import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.impl.EmptyTree;
import com.legeyda.zmij.tree.impl.ValuedLeaf;
import com.legeyda.zmij.tree.impl.ValuelessBranch;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class AnyToTree<T> implements Function<T, Tree> {

	public static AnyToTree<Object> INSTANCE = new AnyToTree<>();

	public static Tree anyToTree(final Tag tag, final Object anything) {
		if(anything instanceof Tree) {
			return (Tree)anything;
		} else if(anything instanceof Iterable<?>) {
			final List<Tree> children = new LinkedList<>();
			for (final Object child : (Iterable) anything) {
				children.add(anyToTree(Tag.ITEM, child));
			}
			return new ValuelessBranch(tag, children);
		} else if(anything instanceof Result<?>) {
			if(((Result<?>)anything).isPresent()) {
				return anyToTree(tag,((Result<?>)anything).value());
			} else {
				return EmptyTree.INSTANCE;
			}
		} else if(anything instanceof Optional<?>) {
			if(((Optional<?>)anything).isPresent()) {
				return anyToTree(tag,((Optional<?>)anything).get());
			} else {
				return EmptyTree.INSTANCE;
			}
		} else {
			return new ValuedLeaf(tag, anything);
		}
	}

	public static <T> AnyToTree<T> create() {
		return (AnyToTree<T>) INSTANCE;
	}

	public static <T> AnyToTree<T> create(final Class<T> sourceType) {
		return (AnyToTree<T>) INSTANCE;
	}

	public AnyToTree() {}

	public AnyToTree(final Class<T> sourceType) {}


	@Override
	public Tree apply(T t) {
		return anyToTree(Tag.VALUE, t);
	}
}
