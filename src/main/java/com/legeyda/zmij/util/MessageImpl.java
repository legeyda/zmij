package com.legeyda.zmij.util;

import java.util.List;

public class MessageImpl implements Message {

	private final List<CharSequence> data;

	public MessageImpl(List<CharSequence> data) {
		this.data = data;
	}

	@Override
	public void print(final Appendable x, int indent) {


	}
}
