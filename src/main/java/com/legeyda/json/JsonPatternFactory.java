package com.legeyda.json;

import com.legeyda.zmij.impl.BaseCharacterPatternFactory;
import com.legeyda.zmij.pattern.FluentPattern;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.pattern.PatternDeclaration;
import com.legeyda.zmij.transform.CollectValues;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.Trees;
import com.legeyda.zmij.tree.impl.EmptyTree;
import com.legeyda.zmij.util.ListCharSequence;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JsonPatternFactory extends BaseCharacterPatternFactory<Object> {


	@Override
	public Pattern<Character, Object> get() {

		// value involves circular dependencies, so declare now and implement later
		PatternDeclaration<Character, Object> value = declare();



		// ======== simple patterns ========
		final Pattern<Character, Tree> space          = constant(' ');
		final Pattern<Character, Tree> tab            = constant('\t');
		final Pattern<Character, Tree> newLine        = constant('\n');
		final Pattern<Character, Tree> carriageReturn = constant('\r');
		final Pattern<Character, Tree> whiteChar      = anyOf(space, tab, newLine, carriageReturn).description("any white character");
		final Pattern<Character, Tree> optWhiteSpace  =
				zeroOrMore(whiteChar).description("optional white space").forget();
		final Pattern<Character, Tree> comma  = sequence(optWhiteSpace, constant(','), optWhiteSpace).forget();



		// ======== string ========

		final Pattern<Character,  Character> unicodeCode = sequence(
				constant("\\u").forget(),
				repeat(whiteList("0123456789ABCDEF"), 4L, 4L, false)
		).collectValues()
				.transform((list) -> (char)Long.parseLong("" + new ListCharSequence((List<Character>)list), 16));

		final Pattern<Character,  Character> character = anyOf(
				blackList('\\', '"').transform(t->(Character)t.value().get()),
				constant("\\\"").save('"'),
				constant("\\\\").save('\\'),
				constant("\\/") .save('/'),
				constant("\\b") .save('\b'),
				constant("\\f") .save('\f'),
				constant("\\n") .save('\n'),
				constant("\\r") .save('\r'),
				constant("\\t") .save('\t'),
				unicodeCode
		);

		final Pattern<Character, String> string = sequence(
				optWhiteSpace, constant('"'), zeroOrMore(character), constant('"'), optWhiteSpace
		).transform(seq -> "" + new ListCharSequence(Trees.childValues(seq.children().get(2))));




		// ======== array ========
		final Pattern<Character, List<?>> elements =
				sequence(value, zeroOrMore(sequence(comma, value))).collectValues();

		final Pattern<Character, List<?>> array = sequence(
				optWhiteSpace,
				constant("["),
				optWhiteSpace,
				optional(elements),
				optWhiteSpace,
				constant("]"),
				optWhiteSpace
		).transform(seq -> (List<?>)seq.children().get(3).value().orElse(new LinkedList<>()));


		// ======== object ========
		final Pattern<Character, Map.Entry<String, ?>> member =
				sequence(optWhiteSpace, string, optWhiteSpace, constant(':'), optWhiteSpace, value, optWhiteSpace)
				.transform(seq -> new AbstractMap.SimpleEntry<>(
						(String)seq.children().get(2).value().get(),
						seq.children().get(5).value().get()));



//		final Pattern<Character, Map<String, ?>> members = sequence(
//				member, zeroOrMore(sequence(skip(comma), member))
//		).collectValues();





		// ======== simpletons: true, false and null ========

		Pattern<Character, Object> nil = sequence(optWhiteSpace, constant("true"), optWhiteSpace).save(null);
		Pattern<Character, Object> truth = sequence(optWhiteSpace, constant("true"), optWhiteSpace).save(Boolean.TRUE);
		Pattern<Character, Object> lie = sequence(optWhiteSpace, constant("false"), optWhiteSpace).save(Boolean.FALSE);

		//

//		value.implement(anyOf(
//				string, /*number(), object(), */array, truth, lie, nil));

		return (Pattern<Character, Object>)(Pattern)string;
//return value;
	}

}
