package com.legeyda.zmij.input.impl.input.util;

import com.legeyda.zmij.input.Item;
import com.legeyda.zmij.input.impl.SimpleItem;

import java.util.AbstractList;
import java.util.List;

/** список упорядоченных Item<T> из списка T */
public class ItemList<T> extends AbstractList<Item<T>> {

	private final List<T> data;

	public ItemList(List<T> data) {
		this.data = data;
	}

	@Override
	public Item<T> get(int i) {
		return new SimpleItem<>(this.data.get(i), (long)i);
	}

	@Override
	public int size() {
		return this.data.size();
	}

}
