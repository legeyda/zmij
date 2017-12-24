package com.legeyda.zmij.sugar;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.util.CharSequenceList;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.function.Supplier;

public class CharParser<R> extends Parser<Character, R>  {

	public CharParser(Supplier<Pattern<Character, R>> pattern) {
		super(pattern);
	}

	public CharParser(Pattern<Character, R> pattern) {
		super(pattern);
	}


	public Result<R> parse(final CharSequence input) {
		return this.parse(new CharSequenceList(input));
	}

	public Result<R> parse(final URL linkToResource, final Charset charset) {
		final String input;
		try {
			input = Resources.toString(linkToResource, charset);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return this.parse(input);
	}

	public Result<R> parse(final URL linkToResource) {
		return this.parse(linkToResource, Charsets.UTF_8);
	}

	public Result<R> parseResourceAt(final String linkToResource, final Charset charset) {
		return this.parse(Resources.getResource(linkToResource), charset);
	}

	public Result<R> parseResourceAt(final String linkToResource) {
		return this.parse(Resources.getResource(linkToResource));
	}


}
