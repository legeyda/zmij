package com.legeyda.zmij.tree;

import java.util.List;
import java.util.Optional;

public interface Tree {
	Enum tag();
	Optional<Object> value();
	List<Tree> children();
}
