package com.legeyda.script.variant;

public class TextVar implements Variant {

	private String data;

	@Override
	public DataType dataType() {
		return DataType.TEXT;
	}

	@Override
	public String asString() {
		return this.data;
	}

	@Override
	public Integer asInteger() {
		return Integer.parseInt(this.data);
	}

	@Override
	public Long asLong() {
		return Long.parseLong(this.data);
	}

	@Override
	public Float asFloat() {
		return null;
	}

	@Override
	public Double asDouble() {
		return null;
	}
}
