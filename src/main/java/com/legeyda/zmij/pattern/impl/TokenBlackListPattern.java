package com.legeyda.zmij.pattern.impl;

import com.google.common.base.Joiner;
import com.legeyda.zmij.util.Inversion;

import java.util.Set;

public class TokenBlackListPattern<T> extends BaseTokenPattern<T> {

	private static String createDescription(final Iterable<?> items) {
		return String.format("any token but {%s}", Joiner.on(", ").join(items));
	}

	public TokenBlackListPattern(final Set<T> blackList) {
		super(createDescription(blackList), new Inversion<>(blackList::contains));
	}
}
