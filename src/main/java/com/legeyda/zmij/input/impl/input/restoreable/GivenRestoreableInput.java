package com.legeyda.zmij.input.impl.input.restoreable;

import com.legeyda.zmij.input.Item;
import com.legeyda.zmij.input.RestoreableInput;
import com.legeyda.zmij.input.Savepoint;

import java.io.IOException;
import java.util.List;

public class GivenRestoreableInput<T> implements RestoreableInput<T> {

	private final List<Item<T>> data;
	private int position = 0;

	public GivenRestoreableInput(final List<Item<T>> data, final long initialPosition) {
		this.data = data;
		this.position = Long.valueOf(initialPosition).intValue();
	}

	public GivenRestoreableInput(final List<Item<T>> data) {
		this(data, 0);
	}

	@Override
	public boolean valid() {
		return this.position >=0 && this.position < this.data.size();
	}

	@Override
	public void advance() {
		this.position++;
	}

	@Override
	public Item<T> get() {
		if(this.valid()) {
			return this.data.get(position);
		} else {
			throw new IllegalStateException("trying to read from invalid input");
		}
	}

	@Override
	public Savepoint savepoint() {
		final int savePosition = position;
		return new Savepoint() {

			@Override
			public void restore() {
				GivenRestoreableInput.this.position = savePosition;
			}

			@Override
			public void close() throws IOException {
				//
			}
		};
	}

}
