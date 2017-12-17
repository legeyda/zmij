package com.legeyda.zmij.pattern;

public interface PatternDeclaration<T, R> extends Pattern<T, R> {

	void implement(Pattern<T, R> implementation);

}
