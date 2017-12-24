package com.legeyda.zmij.sugar;

import com.google.common.collect.Lists;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.util.CharSequenceList;
import com.legeyda.zmij.util.ListCharSequence;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.Function;

public abstract class CharGrammarSugar extends GrammarSugar<Character> {

	public FluentPattern<Character, Tree> constant(CharSequence match) {
		return this.constant(new CharSequenceList(match));
	}

	public FluentPattern<Character, Tree> constant(Character match) {
		return this.constant(Collections.singleton(match));
	}


	public FluentPattern<Character, Tree> whiteList(final CharSequence onlyAllowed) {
		return this.whiteList(new HashSet<>(new CharSequenceList(onlyAllowed)));
	}

	public FluentPattern<Character, Tree> whiteList(Character ... onlyAllowed) {
		return this.whiteList(new HashSet<>(Arrays.asList(onlyAllowed)));
	}

	public FluentPattern<Character, Tree> blackList(final CharSequence forbidden) {
		return this.blackList(new HashSet<>(new CharSequenceList(forbidden)));
	}

	public FluentPattern<Character, Tree> blackList(final Character ... forbidden) {
		return this.blackList(new HashSet<>(Arrays.asList(forbidden)));
	}

	public Function<? extends Iterable<Character>, String> asString() {
		return iterable -> new ListCharSequence(Lists.newArrayList(iterable)).toString();
	}

}
