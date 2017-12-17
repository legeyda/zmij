package com.legeyda.zmij;

import com.legeyda.zmij.util.Pair;
import org.junit.Assert;
import org.junit.Test;

public abstract class BaseMultiCaseTest<T, V> extends BaseTest {

	public abstract Iterable<Pair<T, V>> getCasePairs();

	public abstract T getActual(final V datum);

	protected <T, V> Pair<T, V> pair(final T expected, final V actual) {
		return new Pair<>(expected, actual);
	}

	@Test
	public void testCasePairs() {
		for(final Pair<T, V> pair: this.getCasePairs()) {
			Assert.assertEquals(pair.getA(), this.getActual(pair.getB()));
		}
	}

}
