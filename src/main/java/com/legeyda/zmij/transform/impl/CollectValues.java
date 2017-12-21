package com.legeyda.zmij.transform.impl;

import com.legeyda.zmij.tree.Tree;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class CollectValues implements Function<Tree, List<?>> {

	public static final CollectValues INSTANCE = new CollectValues();

	@Override
	public List<Object> apply(Tree tree) {
		final List<Object> result = new LinkedList<>();
		if(tree.value().isPresent()) {
			result.add(tree.value().get());
		}
		for(final Tree child: tree.children()) {
			result.addAll(this.apply(child));
		}
		return result;
	}


}
