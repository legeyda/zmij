package com.legeyda.zmij;

import com.legeyda.zmij.input.Input;
import org.junit.Assert;

public abstract class BaseTest {


	public void assertThrowsOnGet(final Input input) {
		try {
			input.get();
			Assert.assertTrue("exception expected", false);
		} catch(IllegalStateException e) {
			return;
		}
	}



}
