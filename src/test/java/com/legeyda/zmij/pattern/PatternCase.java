package com.legeyda.zmij.pattern;

import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.input.impl.context.ParsingContextImpl;
import com.legeyda.zmij.input.impl.input.restoreable.GivenRestoreableInput;
import com.legeyda.zmij.input.impl.input.util.ItemList;
import com.legeyda.zmij.result.Result;
import org.junit.Assert;

import java.util.List;

public class PatternCase<T, R> implements Runnable {
	private final Pattern<T, R> pattern;
	private final List<T> input;

	private final Result<R> result;

	public PatternCase(Pattern<T, R> pattern, List<T> input, Result<R> result) {
		this.pattern = pattern;
		this.input = input;
		this.result = result;
	}

	public Pattern<T, R> getPattern() {
		return pattern;
	}

	public List<T> getInput() {
		return input;
	}

	public Result<R> getResult() {
		return result;
	}

	@Override
	public void run() {
		final ParsingContext<T> ctx =
				new ParsingContextImpl<>(new GivenRestoreableInput<>(new ItemList<>(getInput())));
		final Result<R> actualResult = getPattern().apply(ctx).get();
		Assert.assertEquals(getResult(), actualResult);
	}
}