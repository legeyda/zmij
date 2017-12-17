package com.legeyda.zmij.util;

import java.util.Iterator;

public class PairIterable<A, B> implements Iterable<Pair<A, B>> {

	final Iterable<A> as;
	final Iterable<B> bs;

	public PairIterable(Iterable<A> as, Iterable<B> bs) {
		this.as = as;
		this.bs = bs;
	}

	@Override
	public Iterator<Pair<A, B>> iterator() {
		return new Iterator<Pair<A, B>>() {
			final Iterator<A> a = as.iterator();
			final Iterator<B> b = bs.iterator();
			@Override
			public boolean hasNext() {
				return a.hasNext() && b.hasNext();
			}

			@Override
			public Pair<A, B> next() {
				return new Pair<>(a.next(), b.next());
			}
		};
	}


}
