package com.legeyda.zmij.passage.impl;

import com.legeyda.zmij.input.Input;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class MemoizingPassage<T, R> extends PassageDecorator<R> {

	private final Input<T> input;

	private final class Item {
		private final Result<R> result;
		private final Long newPosition;

		public Item(final Result<R> result, final Long newPosition) {
			if(result.isPresent()
					&& result.value() instanceof Tree
					&& ((Tree)result.value()).tag() == Tag.TOKEN
					&& ((Tree)result.value()).value().isPresent()
					&& ((Tree)result.value()).value().get() instanceof Character
					&& ((Character)((Tree)result.value()).value().get())=='}') {
				int x= 0;
			}
			this.result = result;
			this.newPosition = newPosition;
		}

		public Item(final Result<R> result, final Input input) {
			this(result, input.valid() ? input.get().position() : null);
		}

		public Item(final Result<R> result) {
			this(result, (Long)null);
		}

		public Result<R> result() {
			return result;
		}

		public Optional<Long> newPosition() {
			return Optional.ofNullable(newPosition);
		}

	}

	private final Map<Long, Item> cache = new HashMap<>();
	private Item eof = null;

	public MemoizingPassage(final Input<T> input, final Passage<R> passage) {
		super(passage);
		this.input = input;
	}

	@Override
	public Result<R> get() {
		final Long key = this.input.valid() ? this.input.get().position() : null;
		if(this.cache.containsKey(key)) {
			final Item cached = this.cache.get(key);
			if(cached.newPosition().isPresent()) {
				final Long newPosition = cached.newPosition().get();
				while (this.input.valid() && !Objects.equals(newPosition, this.input.get().position())) {
					this.input.advance();
				}
			} else {
				while (this.input.valid()) {
					this.input.advance();
				}
			}
			return cached.result();
		} else {
			final Long oldPosition = this.input.valid() ? input.get().position() : null;
			final Result<R> result = super.get();
			cache.put(oldPosition, new Item(result, this.input));
			return result;
		}
	}


}
