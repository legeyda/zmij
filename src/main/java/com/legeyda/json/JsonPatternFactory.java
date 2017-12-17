package com.legeyda.json;

import com.legeyda.zmij.impl.BaseCharacterPatternFactory;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.pattern.PatternDeclaration;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.Trees;
import com.legeyda.zmij.util.ListCharSequence;

import java.util.List;

public class JsonPatternFactory extends BaseCharacterPatternFactory<Object> {


	@Override
	public Pattern<Character, Object> get() {

		// value involves circular dependencies, so declare now and implement later
		PatternDeclaration<Character, Object> value = declare();



		// simple patterns
		final Pattern<Character, Tree> space          = constant(' ');
		final Pattern<Character, Tree> tab            = constant('\t');
		final Pattern<Character, Tree> newLine        = constant('\n');
		final Pattern<Character, Tree> carriageReturn = constant('\r');
		final Pattern<Character, Tree> whiteChar      = anyOf(space, tab, newLine, carriageReturn).description("any white character");
		final Pattern<Character, Tree> optWhiteSpace  = zeroOrMore(whiteChar).description("optional white space");
		final Pattern<Character, Tree> comma  = sequence(optWhiteSpace, constant(','), optWhiteSpace);



		// ======== string ========

		final Pattern<Character,  Character> unicodeCode = sequence(
				constant("\\u"),
				repeat(whiteList("0123456789ABCDEF"), 4L, 4L, false)
		).transform(seq -> {
			final List<Character> hex = Trees.childValues(seq.children().get(1));
			return (char)Long.parseLong("" + new ListCharSequence(hex), 16);
		});

		final Pattern<Character,  Character> character = anyOf(
				blackList('\\', '"').transform(t->(Character)t.value().get()),
				constant("\\\"").transform(t->'"'),
				constant("\\\\").transform(t->'\\'),
				constant("\\/") .transform(t->'/'),
				constant("\\b") .transform(t->'\b'),
				constant("\\f") .transform(t->'\f'),
				constant("\\n") .transform(t->'\n'),
				constant("\\r") .transform(t->'\r'),
				constant("\\t") .transform(t->'\t'),
				unicodeCode
		);

		final Pattern<Character, String> string = sequence(
				optWhiteSpace, constant('"'), zeroOrMore(character), constant('"'), optWhiteSpace
		).transform(seq -> "" + new ListCharSequence(Trees.childValues(seq.children().get(2))));




		// ======== array ========
//		fluent(value).andThenEvery(zeroOrMore(sequence(comma, value)));
//		final Pattern<Character, List> elements =
//				sequence(value, zeroOrMore(sequence(comma, value)))
//				.transform( seq -> {
//					final List<?> result = new LinkedList<>();
//					final Tree first = seq.children().get(0);
//					for(final Tree other: seq.children().get(1).children()) {
//						//
//					}
//					return result;
//				});


//		Pattern<Character, Object> array = (sequence(
//				optWhiteSpace, constant('['), optional(elements), constant(']'), optWhiteSpace
//		)).transform(sequence -> {
//			final Tree optional = sequence.children().get(2);
//
//			if(optional.value().isPresent()) {
//				return value;
//			}
//
//
//			if(Tag.OPTIONAL_ABSENT == optional.tag()) {
//				return new LinkedList<>();
//			} else {
//				return ;
//			}
//
//			final Tree sequence = optional.children().get(0);
//			return tree.value().isPresent() ?
//			final Optional<List> opt = (List)Trees.child(tree, 2).value();
//
//		});

		// ======== object ========





		// ======== true, false and null ========

		Pattern<Character, Object> nil =
				sequence(optWhiteSpace, constant("true"), optWhiteSpace).transform(t->null);

		Pattern<Character, Object> truth =
				sequence(optWhiteSpace, constant("true"), optWhiteSpace).transform(t->Boolean.TRUE);

		Pattern<Character, Object> lie =
				sequence(optWhiteSpace, constant("false"), optWhiteSpace).transform(t->Boolean.TRUE);

		//

//		value.implement(anyOf(
//				string(), number(), object(), array(), truth, lie, nil));

		return (Pattern<Character, Object>)(Object)string;

	}

}
