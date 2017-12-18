package com.legeyda.zmij.tree.impl;

import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EmptyTree implements Tree {

	public static EmptyTree INSTANCE = new EmptyTree();

	@Override
	public Enum tag() {
		return Tag.EMPTY;
	}

	@Override
	public Optional<Object> value() {
		return Optional.empty();
	}

	@Override
	public List<Tree> children() {
		return Collections.emptyList();
	}
}
