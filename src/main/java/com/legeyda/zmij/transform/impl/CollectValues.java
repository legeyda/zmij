package com.legeyda.zmij.transform.impl;

import com.legeyda.zmij.tree.Tree;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class CollectValues implements Function<Tree, List<Object>> {

	public static final CollectValues INSTANCE = new CollectValues();

	public static List<Object> go(final Tree tree) {
		final List<Object> result = new LinkedList<>();
		if(tree.value().isPresent()) {
			result.add(tree.value().get());
		}
		for(final Tree child: tree.children()) {
			result.addAll(go(child));
		}
		return result;
	}

	@Override
	public List<Object> apply(final Tree tree) {
		return go(tree);
	}


}
