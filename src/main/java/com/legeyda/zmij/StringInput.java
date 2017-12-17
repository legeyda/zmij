package com.legeyda.zmij;


public class StringInput implements InputOld {

	private final String data;
	private int position;

	public StringInput(String data, int pos) {
		if(0==data.length()) {
			throw new IllegalArgumentException("input string cannot be empty");
		}
		this.data = data;
		this.position = pos;
	}

	public StringInput(String data) {
		this(data, 0);
	}


	@Override
	public boolean advance() {
		if(this.position<this.data.length()-1) {
			position++;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Character get() {
		if(this.position<this.data.length()) {
			throw new IllegalStateException("no character at input position");
		}
		return this.data.charAt(this.position);
	}

	@Override
	public InputOld clone() {
		return new StringInput(this.data, this.position);
	}
}
