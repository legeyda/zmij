package com.legeyda.zmij.pattern.impl;

import com.google.common.base.Joiner;

import java.util.Set;

public class TokenWhiteListPattern<T> extends BaseTokenPattern<T> {

	private static String createDescription(final Iterable<?> items) {
		return String.format("any token of {%s}", Joiner.on(", ").join(items));
	}

	public TokenWhiteListPattern(final Set<T> blackList) {
		super(createDescription(blackList), blackList::contains);
	}
}
