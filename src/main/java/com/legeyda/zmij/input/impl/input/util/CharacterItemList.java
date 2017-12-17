package com.legeyda.zmij.input.impl.input.util;

import com.legeyda.zmij.input.Item;
import com.legeyda.zmij.util.CharSequenceList;

/** список Item<Character> из строки */
public class CharacterItemList extends ListAdapter<Item<Character>> {
	public CharacterItemList(final CharSequence data) {
		super(new ItemList<>(new CharSequenceList(data)));
	}
}
