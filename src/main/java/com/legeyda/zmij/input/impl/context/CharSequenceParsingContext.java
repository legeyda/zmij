package com.legeyda.zmij.input.impl.context;

import com.legeyda.zmij.input.impl.input.restoreable.GivenRestoreableInput;
import com.legeyda.zmij.input.impl.input.util.CharacterItemList;

public class CharSequenceParsingContext extends ParsingContextDecorator<Character> {

	public CharSequenceParsingContext(CharSequence data) {
		super(new ParsingContextImpl<>(new GivenRestoreableInput<>(new CharacterItemList(data))));
	}

}
