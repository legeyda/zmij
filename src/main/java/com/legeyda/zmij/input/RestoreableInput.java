package com.legeyda.zmij.input;

public interface RestoreableInput<T> extends Input<T> {

	Savepoint savepoint();

}
