package com.legeyda.zmij;

import java.util.function.Supplier;

/** if(iter.valid())  */
public interface InputOld extends Supplier<Character> {
	boolean advance();
	Character get();
	InputOld clone();
}

