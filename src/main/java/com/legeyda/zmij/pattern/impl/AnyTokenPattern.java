package com.legeyda.zmij.pattern.impl;

public class AnyTokenPattern<T> extends BaseTokenPattern<T> {

	public AnyTokenPattern() {
		super("any token", t -> true);
	}

}
