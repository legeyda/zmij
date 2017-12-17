package com.legeyda.zmij.input.impl.input.restoreable;

import com.legeyda.zmij.input.Input;
import com.legeyda.zmij.input.Item;
import com.legeyda.zmij.input.RestoreableInput;
import com.legeyda.zmij.input.Savepoint;
import com.legeyda.zmij.input.impl.input.*;

import java.io.IOException;
import java.util.*;

public class RestoreableInputImpl<T> extends BaseStatefulInput<T> implements RestoreableInput<T> {

	// when in state from traversing through buffer
	private LinkedList<Item<T>> buffer = new LinkedList<>();
	private Set<Long> savepoints = new TreeSet<>(); // buffer positions

	//
	private final Input<T> origin;
	private long originCounter = -1;
	private long bufferPosition = -1;

	public RestoreableInputImpl(Input<T> input) {
		this.origin = new ListenInput<>(input, (final Item<T> item) -> {
			originCounter++;
		});
		this.setState(this.origin);
	}

	List<Item<T>> getBuffer() {
		return this.buffer;
	}

	private void useOrigin() {
		this.bufferPosition = -1;
		this.setState(this.origin);
	}

	private void appendBuffer() {
		this.setState(new ListenInput<>(
				new BufferingInput(this.origin, this.buffer),
				(final Item<T> item) -> this.bufferPosition = this.buffer.size()-1));
	}

	/** read buffer garbaging elements on the go */
	private void readBufferThenAbandon() {
		this.bufferPosition = 0;
		this.setState(new EventInput<>(
				new QueueInput<>(this.buffer),
				() -> {
					this.origin.advance();
					this.useOrigin();
					this.bufferPosition = -1; } ));
	}

	/** position */
	private void readBufferThenAppend(final long positionInBuffer) {
		this.bufferPosition = -1;
		this.setState(new EventInput<>(
				new ListenInput<>(
						new GivenInput<>(this.buffer, positionInBuffer),
						(final Item<T> item) -> bufferPosition++),
				() -> {
						this.origin.advance();
						this.appendBuffer();
				}));
	}

	public Savepoint savepoint() {
		if(this.bufferPosition<0) {
			this.appendBuffer();
		} else {
			this.readBufferThenAppend(this.bufferPosition);
		}
		final long thatSavepointPosition = this.bufferPosition;
		this.savepoints.add(this.bufferPosition);

		return new Savepoint() {
			@Override
			public void restore() {
				readBufferThenAppend(thatSavepointPosition);
			}
			@Override
			public void close() throws IOException {
				if(!savepoints.isEmpty()) {
					final Iterator<Long> iter = savepoints.iterator();
					if(thatSavepointPosition == iter.next()) {
						iter.remove();
						if(iter.hasNext()) {
							final long truncateAmount = Math.min(iter.next(), bufferPosition);
							incrementSavepoints(truncateAmount);
							buffer.subList(0, Long.valueOf(truncateAmount).intValue()).clear();
							readBufferThenAppend(bufferPosition - truncateAmount);
						} else {
							final long truncateAmount = bufferPosition;
							incrementSavepoints(truncateAmount);
							buffer.subList(0, Long.valueOf(truncateAmount).intValue()).clear();
							readBufferThenAbandon();
						}
					} else {
						savepoints.remove(thatSavepointPosition);
					}
				}
			}
		};
	}

	private void incrementSavepoints(final long delta) {
		final Set<Long> result = new TreeSet<>();
		for(final Long value: this.savepoints) {
			result.add(value+delta);
		}
		this.savepoints = result;
	}


}
