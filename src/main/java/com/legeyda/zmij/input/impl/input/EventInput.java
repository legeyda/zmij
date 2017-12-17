package com.legeyda.zmij.input.impl.input;

import com.legeyda.zmij.input.Input;
import com.legeyda.zmij.input.impl.RunnanbleOnce;

public class EventInput<T> extends InputDecorator<T> {
	private final Runnable onInvalid;

	public EventInput(final Input wrap, final Runnable onInvalid) {
		super(wrap);
		this.onInvalid = new RunnanbleOnce(onInvalid);
		this.checkEvent();
	}

	@Override
	public void advance() {
		super.advance();
		this.checkEvent();
	}

	private void checkEvent() {
		if(!this.valid()) {
			this.onInvalid.run();
		}
	}


}
