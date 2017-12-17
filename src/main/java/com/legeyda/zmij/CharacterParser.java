package com.legeyda.zmij;

import com.legeyda.zmij.result.Result;

public interface CharacterParser<R> extends Parser<Character, R> {

	Result<R> parse(CharSequence input);

}
