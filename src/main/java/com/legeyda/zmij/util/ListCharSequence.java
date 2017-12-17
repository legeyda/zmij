package com.legeyda.zmij.util;

import java.util.List;

public class ListCharSequence implements CharSequence {

	private final List<Character> data;

	public ListCharSequence(List<Character> data) {
		this.data = data;
	}

	@Override
	public int length() {
		return this.data.size();
	}

	@Override
	public char charAt(int i) {
		return this.data.get(i);
	}

	@Override
	public CharSequence subSequence(int i, int i1) {
		return new ListCharSequence(this.data.subList(i, i1));
	}

	@Override
	public String toString() {
		final StringBuilder str = new StringBuilder();
		for(final Character ch: this.data) {
			str.append(ch);
		}
		return str.toString();
	}
}
