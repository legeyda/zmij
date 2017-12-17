package com.legeyda.zmij;

import java.util.Collection;

public interface Element<T> {
	Collection<Element<T>> children();
	Collection<T> source();
}
