package com.legeyda.zmij.pattern;

import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.util.CharSequenceList;

public class CharPatternCase<R> extends PatternCase<Character, R> {


	public CharPatternCase(Pattern<Character, R> pattern, CharSequence input, Result<R> result) {
		super(pattern, new CharSequenceList(input), result);
	}


}
