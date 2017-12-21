package com.legeyda.zmij.tree;

import com.google.common.collect.Iterators;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.tree.impl.ValuedLeaf;
import com.legeyda.zmij.tree.impl.ValuelessLeaf;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Trees {

	public static Tree child(final Tree tree, final int index) {
		return Iterators.get(tree.children().iterator(), index);
	}

	public static Stream<Tree> childStream(final Tree tree) {
		return tree.children().stream();
	}

	public static Tree from(final Tag tag, final Result<?> result) {
		return result.isPresent()
				? from(tag, result.value())
				: new ValuelessLeaf(Tag.OPTIONAL_ABSENT);
	}

	public static Tree from(final Tag tag, final Optional<?> optional) {
		return optional.isPresent()
				? from(tag, optional.get())
				: new ValuelessLeaf(Tag.OPTIONAL_ABSENT);
	}

	public static Tree from(final Tag tag, final Object anything) {
		if(anything instanceof Tree) {
			return (Tree)anything;
		} else if(anything instanceof Iterable) {
			return from(tag, (Iterable)anything);
		} else {
			new ValuedLeaf(Tag.RESULT, anything);
		}
	}

	public static <T> List<T> childValues(final Tree tree) {
		final List<T> result = new ArrayList<>(tree.children().size());
		for(final Tree child: tree.children()) {
			if(child.value().isPresent()) {
				result.add((T)child.value().get());
			}
		}
		return result;
	}

}
