package com.legeyda.json;

import com.legeyda.json.type.JsString;
import com.legeyda.json.type.Type;

public class JsonStringImpl implements JsString {
	private final CharSequence value;

	public JsonStringImpl(final CharSequence value) {
		this.value = value;
	};

	@Override
	public String value() {
		return String.valueOf(this.value);
	}

	@Override
	public Type jsType() {
		return null;
	}

	@Override
	public String json() {
		return null;
	}

}
