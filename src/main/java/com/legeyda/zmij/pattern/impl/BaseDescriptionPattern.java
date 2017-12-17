package com.legeyda.zmij.pattern.impl;


public abstract class BaseDescriptionPattern<T, R> extends BasePattern<T, R> {

	private final String description;

	public BaseDescriptionPattern(String description) {
		this.description = description;
	}

	@Override
	public String description() {
		return this.description;
	}

}
