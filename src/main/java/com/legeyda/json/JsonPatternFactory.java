package com.legeyda.json;

import com.legeyda.zmij.impl.BaseCharacterPatternFactory;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.pattern.PatternDeclaration;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.Trees;
import com.legeyda.zmij.util.ListCharSequence;

import java.util.*;

public class JsonPatternFactory extends BaseCharacterPatternFactory<Object> {


	@Override
	public Pattern<Character, Object> get() {

		// since it's circular dependency, declare now, implement later
		PatternDeclaration<Character, Object> value = declare("javascript value");



		// ======== simple patterns ========
		final Pattern<Character, Tree> whiteChar = whiteList(' ', '\t', '\n', '\r').description("any white character");
		final Pattern<Character, Tree> optWhiteSpace =
				zeroOrMore(whiteChar).description("optional white space").forget();
		final Pattern<Character, Tree> comma  = sequence(optWhiteSpace, constant(','), optWhiteSpace)
				.description("comma")
				.forget();



		// ======== string ========

		final Pattern<Character,  Character> unicodeCode = sequence(
				constant("\\u").forget(),
				repeat(whiteList("0123456789ABCDEF"), 4L, 4L, false)
		).collectValues()
				.transform((list) -> (char)Long.parseLong("" + new ListCharSequence((List<Character>)(List)list), 16))
				.description("unicode code");

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
		).description("single character");

		final Pattern<Character, String> string = sequence(
				optWhiteSpace, constant('"'), zeroOrMore(character), constant('"'), optWhiteSpace
		).transform(seq -> "" + new ListCharSequence(Trees.childValues(seq.children().get(2))))
				.description("json string");




		// ======== array ========
		final Pattern<Character, List<Object>> elements = sequence(value, zeroOrMore(sequence(comma, value)))
				.description("json array elements")
				.collectValues();

		final Pattern<Character, List<?>> array = sequence(
				optWhiteSpace,
				constant("["),
				optWhiteSpace,
				optional(elements),
				optWhiteSpace,
				constant("]"),
				optWhiteSpace
		)
				.description("json array")
				.transform(seq -> (List<?>)seq.children().get(3).value().orElse(new LinkedList<>()));


		// ======== object ========
		final Pattern<Character, Map.Entry<String, Object>> member =
				sequence(optWhiteSpace, string, optWhiteSpace, constant(':').forget(), optWhiteSpace, value, optWhiteSpace)
						.collectValues()
						.transform(list->new AbstractMap.SimpleEntry<>(list.get(0).toString(), list.get(1)));


		final Pattern<Character, Map<String, Object>> members = sequence(member, zeroOrMore(sequence(comma, member)))
				.collectValues()
				.transform(entries -> {
					final Map<String, Object> result = new HashMap<>();
					for(Map.Entry<String, Object> entry: (List<Map.Entry<String, Object>>)(Object)entries) {
						result.put(entry.getKey(), entry.getValue());
					}
					return result;
				});

		final Pattern<Character, Map<String, Object>> object = sequence(
				optWhiteSpace,
				constant("{"),
				optWhiteSpace,
				optional(members),
				optWhiteSpace,
				constant("}"),
				optWhiteSpace
		)
				.description("json object")
				.transform(seq -> (Map<String, Object>)seq.children().get(3).value().orElse(new HashMap<>()));




		// ======== simpletons: true, false and null ========

		Pattern<Character, Object> nil = sequence(optWhiteSpace, constant("true"), optWhiteSpace).save(null);
		Pattern<Character, Object> truth = sequence(optWhiteSpace, constant("true"), optWhiteSpace).save(Boolean.TRUE);
		Pattern<Character, Object> lie = sequence(optWhiteSpace, constant("false"), optWhiteSpace).save(Boolean.FALSE);

		//

		value.implement(anyOf(
				string, /*number(),*/ object, array, truth, lie, nil));

//		return (Pattern<Character, Object>)(Pattern)string;
		value.description();
		return value;
	}

}
