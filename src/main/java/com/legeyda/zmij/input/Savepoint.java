package com.legeyda.zmij.input;

import java.io.Closeable;

public interface Savepoint extends Closeable {
	void restore();
}
