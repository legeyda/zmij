package com.legeyda.zmij;

import com.legeyda.zmij.pattern.Pattern;

import java.util.function.Supplier;

public interface PatternFactory<T, R> extends Supplier<Pattern<T, R>> {
}
