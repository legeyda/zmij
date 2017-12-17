package com.legeyda.json;

import com.legeyda.json.type.JsString;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.pattern.impl.PatternDecorator;

public class JsonStringPattern extends PatternDecorator<Character, JsString> {
	public JsonStringPattern(Pattern<Character, JsString> wrap) {
		super(wrap);
	}
}
