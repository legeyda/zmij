package com.legeyda.zmij.impl;

import com.legeyda.zmij.CharacterPatternFactory;
import com.legeyda.zmij.pattern.FluentPattern;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.util.CharSequenceList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public abstract class BaseCharacterPatternFactory<R>
		extends BasePatternFactory<Character, R>
		implements CharacterPatternFactory<R> {

	public FluentPattern<Character, Tree> constant(CharSequence match) {
		return this.constant(new CharSequenceList(match));
	}

	public FluentPattern<Character, Tree> constant(Character match) {
		return this.constant(Collections.singleton(match));
	}


	protected FluentPattern<Character, Tree> whiteList(final CharSequence onlyAllowed) {
		return this.whiteList(new HashSet<>(new CharSequenceList(onlyAllowed)));
	}

	protected FluentPattern<Character, Tree> whiteList(Character ... onlyAllowed) {
		return this.whiteList(new HashSet<>(Arrays.asList(onlyAllowed)));
	}

	protected FluentPattern<Character, Tree> blackList(final CharSequence forbidden) {
		return this.blackList(new HashSet<>(new CharSequenceList(forbidden)));
	}

	protected FluentPattern<Character, Tree> blackList(final Character ... forbidden) {
		return this.blackList(new HashSet<>(Arrays.asList(forbidden)));
	}
}
