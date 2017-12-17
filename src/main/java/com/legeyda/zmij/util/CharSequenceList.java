package com.legeyda.zmij.util;

import java.util.AbstractList;

public class CharSequenceList extends AbstractList<Character> {
	private final CharSequence data;

	public CharSequenceList(CharSequence data) {
		this.data = data;
	}

	@Override
	public Character get(int i) {
		return this.data.charAt(i);
	}

	@Override
	public int size() {
		return this.data.length();
	}
}
