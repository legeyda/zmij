package com.legeyda.zmij.impl;

import com.legeyda.zmij.PatternFactory;
import com.legeyda.zmij.pattern.FluentPattern;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.pattern.PatternDeclaration;
import com.legeyda.zmij.pattern.impl.*;
import com.legeyda.zmij.pattern.impl.fluent.FluentPatternImpl;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.impl.EmptyTree;

import java.util.Set;

public abstract class BasePatternFactory<T, R> implements PatternFactory<T, R> {

	protected <V> FluentPattern<T, V> fluent(final Pattern<T, V> wrapee) {
		return new FluentPatternImpl<>(new MemoizingPattern<>(wrapee));
	}

	protected <V> Pattern<T, V> wrap(final Pattern<T, V> wrapee) {
		return new MemoizingPattern<>(wrapee);
	}

	protected FluentPattern<T, R> from(Pattern<T, R> pattern) {
		return new FluentPatternImpl<>(this.fluent(pattern));
	}

	/** */
	protected <V> Pattern<T, Tree> skip(Pattern<T, V> pattern) {
		return this.fluent(pattern).transform(t-> EmptyTree.INSTANCE);
	}

	protected FluentPattern<T, Tree> constant(Iterable<T> match) {
		return this.fluent(this.wrap(new ConstantPattern<>(match)));
	}

	protected FluentPattern<T, Tree> whiteList(Set<T> onlyAllowed) {
		return this.fluent(this.wrap(new TokenWhiteListPattern<>(onlyAllowed)));
	}

	protected FluentPattern<T, Tree> blackList(Set<T> onlyAllowed) {
		return this.fluent(this.wrap(new TokenBlackListPattern<>(onlyAllowed)));
	}

	@SafeVarargs
	final protected <V> FluentPattern<T, V> anyOf(Pattern<T, ? extends V> ... choices) {
		return this.fluent(this.wrap(new AnyOfPattern<>(choices)));
	}

	protected <V> PatternDeclaration<T, V> declare() {
		return new PatternDeclarationImpl<>();
	}

	@SafeVarargs
	final protected FluentPattern<T, Tree> sequence(Pattern<T, ?> ... elements) {
		return this.fluent(this.wrap(new SequencePattern<>(elements)));
	}

	protected <V> FluentPattern<T, Tree> zeroOrMore(Pattern<T, V> element) {
		return this.fluent(this.wrap(new ZeroOrMorePattern<>(element)));
	}

	protected <V> FluentPattern<T, Tree> oneOrMore(Pattern<T, V> element) {
		return this.fluent(this.wrap(new OneOrMorePattern<>(element)));
	}

	protected FluentPattern<T, Tree> optional(Pattern<T, ?> element) {
		return this.fluent(this.wrap(new OptionalPattern<>(element)));
	}

	protected FluentPattern<T, Tree> repeat(Pattern<T, ?> pattern, Long minOccurs, Long maxOccurs, boolean greedy) {
		return this.fluent(this.wrap(new RepeatPattern<>(pattern, minOccurs, maxOccurs, greedy)));
	}

	protected FluentPattern<T, Tree> repeat(Pattern<T, ?> pattern, Long minOccurs, Long maxOccurs) {
		return repeat(pattern, minOccurs, maxOccurs, false);
	}

	protected FluentPattern<T, Tree> repeat(Pattern<T, ?> pattern, Long occurs, boolean greedy) {
		return this.repeat(pattern, occurs, occurs, greedy);
	}

	protected FluentPattern<T, Tree> repeat(Pattern<T, ?> pattern, Long occurs) {
		return repeat(pattern, occurs, false);
	}

	protected FluentPattern<T, Tree> anyToken() {
		return this.fluent(this.wrap(new AnyTokenPattern<>()));
	}

}
