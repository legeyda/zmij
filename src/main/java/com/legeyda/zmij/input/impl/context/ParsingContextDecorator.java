package com.legeyda.zmij.input.impl.context;

import com.legeyda.zmij.input.Item;
import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.input.Savepoint;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.pattern.Pattern;

import java.util.function.Supplier;

public abstract class ParsingContextDecorator<T> implements ParsingContext<T> {
	private final ParsingContext<T> wrap;

	public ParsingContextDecorator(ParsingContext<T> wrap) {
		this.wrap = wrap;
	}

	@Override
	public Savepoint savepoint() {
		return wrap.savepoint();
	}

	@Override
	public boolean valid() {
		return wrap.valid();
	}

	@Override
	public void advance() {
		wrap.advance();
	}

	@Override
	public <R> Passage<R> getOrCreatePassage(Pattern<T, R> key, Supplier<Passage<R>> generator) {
		return wrap.getOrCreatePassage(key, generator);
	}

	@Override
	public Item<T> get() {
		return wrap.get();
	}
}
