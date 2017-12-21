package com.legeyda.zmij.transform.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Mapping<T, R> extends BaseFluentOptFunction<T, R> {

	private final Map<T, R> rules;

	public Mapping(final Map<T, R> rules) {
		this.rules = rules;
	}

	public Mapping(final T when, final R then) {
		this(new HashMap<>());
		this.rules.put(when, then);
	}

	public Mapping(final T when1, final R then1, final T when2, final R then2) {
		this(when1, then1);
		this.rules.put(when2, then2);
	}

	public Mapping(final T when1, final R then1, final T when2, final R then2, final T when3, final R then3) {
		this(when1, then1, when2, then2);
		this.rules.put(when3, then3);
	}

	public Mapping() {
		this(Collections.emptyMap());
	}

	public Mapping<T, R> rule(final T when, final R then) {
		final Map<T, R> newRules = new HashMap<>();
		newRules.putAll(rules);
		newRules.put(when, then);
		return new Mapping<>(newRules);
	}

	@Override
	public Optional<R> apply(T t) {
		return this.rules.containsKey(t) ? Optional.of(this.rules.get(t)) : Optional.empty();
	}

}
