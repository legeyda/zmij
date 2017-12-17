package com.legeyda.zmij.input;


import com.legeyda.zmij.BaseTest;
import com.legeyda.zmij.input.impl.SimpleItem;
import com.legeyda.zmij.input.impl.input.*;
import com.legeyda.zmij.input.impl.input.restoreable.RestoreableInputImpl;
import com.legeyda.zmij.input.impl.input.util.ItemList;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class InputTest extends BaseTest {

	Collection<Input> getEmptyInputs() {
		final Collection<Input> result = new ArrayList<>(10);
		result.add(new EmptyInput<>());
		result.add(new GivenInput<Character>(Collections.emptyList()));
		result.add(new QueueInput<Character>(new LinkedList<>()));
		result.add(new ConcatInput<Character>(new EmptyInput<>(), new EmptyInput<>()));
		result.add(new IteratorInput<>(Collections.<Item<Character>>emptyList().iterator()));
		result.add(new InputDecorator<Character>(new EmptyInput<>()) { });
		result.add(new BufferingInput<Character>(new EmptyInput<>()));
		result.add(new RestoreableInputImpl<Character>(new EmptyInput<>()));
		return result;
	}

	@Test
	public void testEmptyInputs() {
		for(final Input testee: this.getEmptyInputs()) {
			Assert.assertFalse(testee.valid());
			assertThrowsOnGet(testee);
		}
	}

	Collection<Input<Character>> getNonEmptyInputs() {
		final List<Item<Character>> data = new ItemList<>(Arrays.asList('a', 'b', 'c'));


		final Collection<Input<Character>> result = new ArrayList<>(10);
		result.add(new GivenInput<>(data));
		result.add(new QueueInput<>(new LinkedList<>(data)));

		result.add(new ConcatInput<Character>(new GivenInput(data), new EmptyInput<>()));
		result.add(new ConcatInput<Character>(new EmptyInput<>(), new GivenInput(data)));

		result.add(new IteratorInput<>(data.iterator()));
		result.add(new InputDecorator<Character>(new GivenInput(data)) { });
		result.add(new BufferingInput<Character>(new GivenInput(data)));
		result.add(new RestoreableInputImpl<Character>(new GivenInput(data)));
		return result;
	}

	@Test
	public void testNonEmptyInputs() {
		for(final Input testee: this.getNonEmptyInputs()) {
			Assert.assertTrue(testee.valid());
			Assert.assertEquals(new SimpleItem('a', 0L), testee.get());

			testee.advance();
			Assert.assertTrue(testee.valid());
			Assert.assertEquals(new SimpleItem('b', 1L), testee.get());

			testee.advance();
			Assert.assertTrue(testee.valid());
			Assert.assertEquals(new SimpleItem('c', 2L), testee.get());

			testee.advance();
			Assert.assertFalse(testee.valid());
			assertThrowsOnGet(testee);
		}
	}

}
