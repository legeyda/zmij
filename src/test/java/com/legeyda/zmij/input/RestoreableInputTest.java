package com.legeyda.zmij.input;

import com.legeyda.zmij.BaseTest;
import com.legeyda.zmij.input.impl.SimpleItem;
import com.legeyda.zmij.input.impl.input.GivenInput;
import com.legeyda.zmij.input.impl.input.restoreable.GivenRestoreableInput;
import com.legeyda.zmij.input.impl.input.restoreable.RestoreableInputImpl;
import com.legeyda.zmij.util.Pair;
import com.legeyda.zmij.util.PairIterable;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

public class RestoreableInputTest extends BaseTest {

	private Item<Character> i(final Character value, final Long position) {
		return new SimpleItem<>(value, position);
	}

	public Collection<RestoreableInput<Character>> createTestedInputs() {

		final List<Item<Character>> data = Arrays.asList(
				i('a', 0L),
				i('b', 1L),
				i('c', 2L),
				i('d', 3L),
				i('e', 4L));

		return Arrays.asList(
				new GivenRestoreableInput<>(data),
				new RestoreableInputImpl<>(new GivenInput<>(data)));
	}

	@Test
	public void test() throws IOException {
		for(final RestoreableInput<Character> testee: this.createTestedInputs()) {

			// just created
			Assert.assertTrue(testee.valid());
			Assert.assertEquals(i('a', 0L), testee.get());

			// position 1
			testee.advance();
			Assert.assertEquals(i('b', 1L), testee.get());

			//
			final Savepoint save = testee.savepoint();
			Assert.assertEquals(i('b', 1L), testee.get());

			testee.advance();
			Assert.assertEquals(i('c', 2L), testee.get());

			testee.advance();
			Assert.assertEquals(i('d', 3L), testee.get());

			save.restore();
			Assert.assertEquals(i('b', 1L), testee.get());

			testee.advance();
			Assert.assertEquals(i('c', 2L), testee.get());

			save.close();
			Assert.assertEquals(i('c', 2L), testee.get());

			final Savepoint save2 = testee.savepoint();
			Assert.assertEquals(i('c', 2L), testee.get());

			save2.close();
			Assert.assertEquals(i('c', 2L), testee.get());

			final Savepoint save3 = testee.savepoint();

			testee.advance();
			Assert.assertEquals(i('d', 3L), testee.get());

			testee.advance();
			Assert.assertEquals(i('e', 4L), testee.get());
		}
	}


	@Test
	public void testSaveImmediatelyCancel() throws IOException {
		for(final Pair<RestoreableInput<Character>, RestoreableInput<Character>> pair:
				new PairIterable<>(this.createTestedInputs(), this.createTestedInputs())){

			final RestoreableInput<Character> testee = pair.getA();
			final RestoreableInput<Character> control = pair.getB();

			while (testee.valid()) {

				testee.savepoint().restore();
				Assert.assertEquals(control.get(), testee.get());

				testee.savepoint().close();
				Assert.assertEquals(control.get(), testee.get());

				final Savepoint save = testee.savepoint();
				save.restore();
				save.close();
				Assert.assertEquals(control.get(), testee.get());

				testee.advance();
				control.advance();
			}
		}
	}

	//@Test
	public void testFazer() {
		for (final RestoreableInput<Character> testee : this.createTestedInputs()) {
			Savepoint savepoint = testee.savepoint();
			final Random random = new Random(0);
			for (int i = 0; i < 10000; i++) {
				System.out.println("testFazer: " + i);
				switch (random.nextInt() % 5) {
					case 0:
						testee.valid();
						break;
					case 1:
						testee.advance();
						break;
					case 2:
						savepoint = testee.savepoint();
						break;
					case 3:
						savepoint.restore();
						break;
					case 4:
						try {
							savepoint.close();
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
						break;
				}
			}
		}
	}


}
