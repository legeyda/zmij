package com.legeyda.zmij.impl;

import com.legeyda.zmij.PatternFactory;
import com.legeyda.zmij.pattern.FluentPattern;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.pattern.PatternDeclaration;
import com.legeyda.zmij.pattern.impl.*;
import com.legeyda.zmij.pattern.impl.fluent.FluentPatternImpl;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.impl.EmptyTree;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

public abstract class BasePatternFactory<T, R> implements PatternFactory<T, R> {

	public <V> FluentPattern<T, V> fluent(final Pattern<T, V> wrapee) {
		return new FluentPatternImpl<>(new MemoizingPattern<>(wrapee));
	}

	protected <V> Pattern<T, V> wrap(final Pattern<T, V> wrapee) {
		return new MemoizingPattern<>(wrapee);
	}

	/** */
	public <V> Pattern<T, Tree> skip(Pattern<T, V> pattern) {
		return this.fluent(pattern).map(t-> EmptyTree.INSTANCE);
	}

	public FluentPattern<T, Tree> constant(Iterable<T> match) {
		return this.fluent(this.wrap(new ConstantPattern<>(match)));
	}

	public FluentPattern<T, Tree> whiteList(Set<T> onlyAllowed) {
		return this.fluent(this.wrap(new TokenWhiteListPattern<>(onlyAllowed)));
	}

	public FluentPattern<T, Tree> blackList(Set<T> onlyAllowed) {
		return this.fluent(this.wrap(new TokenBlackListPattern<>(onlyAllowed)));
	}

	@SafeVarargs
	final public <V> FluentPattern<T, V> anyOf(Pattern<T, ? extends V> ... choices) {
		return this.fluent(this.wrap(new AnyOfPattern<>(choices)));
	}

	public <V> PatternDeclaration<T, V> declare(final String description) {
		return new PatternDeclarationImpl<>(description);
	}

	@SafeVarargs
	final public FluentPattern<T, Tree> sequence(Pattern<T, ?> ... elements) {
		return this.fluent(this.wrap(new SequencePattern<>(elements)));
	}

	public <V> FluentPattern<T, Tree> zeroOrMore(Pattern<T, V> element) {
		return this.fluent(this.wrap(new ZeroOrMorePattern<>(element)));
	}

	public <V> FluentPattern<T, Tree> oneOrMore(Pattern<T, V> element) {
		return this.fluent(this.wrap(new OneOrMorePattern<>(element)));
	}

	public FluentPattern<T, Tree> optional(Pattern<T, ?> element) {
		return this.fluent(this.wrap(new OptionalPattern<>(element)));
	}

	public FluentPattern<T, Tree> repeat(Pattern<T, ?> pattern, Integer minOccurs, Integer maxOccurs, boolean greedy) {
		return this.fluent(this.wrap(new RepeatPattern<>(pattern, minOccurs, maxOccurs, greedy)));
	}

	public FluentPattern<T, Tree> repeat(Pattern<T, ?> pattern, Integer minOccurs, Integer maxOccurs) {
		return repeat(pattern, minOccurs, maxOccurs, false);
	}

	public FluentPattern<T, Tree> repeat(Pattern<T, ?> pattern, Integer occurs, boolean greedy) {
		return this.repeat(pattern, occurs, occurs, greedy);
	}

	public FluentPattern<T, Tree> repeat(Pattern<T, ?> pattern, Integer occurs) {
		return repeat(pattern, occurs, false);
	}

	public FluentPattern<T, Tree> anyToken() {
		return this.fluent(this.wrap(new AnyTokenPattern<>()));
	}


	//////////////////

	public <V, S> Function<V, S> fix(final S value) {
		return v->value;
	}

	public <V> Function<V, Tree> empty() {
		return v->EmptyTree.INSTANCE;
	}

//	public <V> Function<Tree, List<V>> values() {
//
//	}
}
