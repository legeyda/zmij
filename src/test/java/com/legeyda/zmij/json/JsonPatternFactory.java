package com.legeyda.zmij.json;

import com.legeyda.zmij.sugar.CharGrammarSugar;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.pattern.PatternDeclaration;
import com.legeyda.zmij.tree.Tree;

import java.math.BigDecimal;
import java.util.*;

public class JsonPatternFactory extends CharGrammarSugar {

	protected Map<String, Object> createMap(final Iterable<Map.Entry<String, Object>> items) {
		final Map<String, Object> result = new HashMap<>();
		for(Map.Entry<String, Object> entry: items) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	protected Character createChar(final String hex) {
		return (char)Long.parseLong(hex, 16);
	}

	protected BigDecimal pow(BigDecimal a, BigDecimal b) {
		return BigDecimal.valueOf(Math.pow(a.doubleValue(), b.doubleValue()));
	}

	public Pattern<Character, Object> get() {

		// since it's circular dependency, declare now, implement later
		PatternDeclaration<Character, Object> value = declare("");


		// zero or more occurences of any of white-space-characters
		final Pattern<Character, Tree> whiteSpace = zeroOrMore(whiteList(' ', '\t', '\n', '\r'))
				.description("optional white space")
				.forget();

		// literal comma, embraced by optional white space
		final Pattern<Character, Tree> comma  = sequence(whiteSpace, constant(','), whiteSpace)
				.description("comma")
				.forget();



		// ======== string ========

		final Pattern<Character, ?> doubleQuote = constant('"').forget();

		// '\n', then exactly four hexademical digits
		final Pattern<Character,  Character> unicode = sequence(
				constant("\\u").forget(),
				repeat(whiteList("0123456789ABCDEF"), 4, false)
		)
				.description("unicode letter by code")
				.asString()
				.map(this::createChar);

		// character is either exact charactor, or escape sequence, or unicode code defined above
		final Pattern<Character,  Character> character = anyOf(
				// akList('\\', '"').value(Character.class)
				blackList('\\', '"').map(value().flatCast(Character.class).orRaise()),
				constant("\\\"").save('"'),
				constant("\\\\").save('\\'),
				constant("\\/") .save('/'),
				constant("\\b") .save('\b'),
				constant("\\f") .save('\f'),
				constant("\\n") .save('\n'),
				constant("\\r") .save('\r'),
				constant("\\t") .save('\t'),
				unicode
		).description("single character");

		final Pattern<Character, String> string = sequence(
				whiteSpace, doubleQuote, zeroOrMore(character), doubleQuote, whiteSpace
		)
				.description("string")
				.asString();


		// ======== number =========

		final Pattern<Character, Character> digit = whiteList("1234567890")
				.value().cast(Character.class);

		final Pattern<Character, Character> nonZeroDigit = whiteList("123456789")
				.value().cast(Character.class);

		final Pattern<Character, Integer> optSign = optional(whiteList('+', '-'))
				.map(value().flatCast(Character.class).map(s->'-'==s ? -1 : 1).orElse((1)));
;
		final Pattern<Character, BigDecimal> unsignedInteger = sequence(digit, zeroOrMore(nonZeroDigit))
				.asString()
				.map(BigDecimal::new);

		final Pattern<Character, BigDecimal> signedInteger = sequence(optSign, unsignedInteger)
				.values()
				.map(both -> ((BigDecimal)both.get(1)).multiply(BigDecimal.valueOf((Integer)both.get(0))));

		final Pattern<Character, BigDecimal> unsignedFloat = sequence(zeroOrMore(nonZeroDigit), constant('.'), oneOrMore(digit))
				.asString()
				.map(BigDecimal::new);

		final Pattern<Character, BigDecimal> signedFloat = sequence(optSign, unsignedFloat)
				.values()
				.map(both -> ((BigDecimal)both.get(1)).multiply(BigDecimal.valueOf((Integer)both.get(0))));


		final Pattern<Character, Number> number = sequence(
				whiteSpace,
				anyOf(signedFloat, signedInteger),
				optional(sequence(
						whiteList('e', 'E').forget(),
						signedInteger
				)),
				whiteSpace
		)
				.values()
				.description("number")
				.map(list -> {
					if(list.size()==2) {
						return ((BigDecimal)list.get(0)).multiply(
								pow(BigDecimal.TEN, (BigDecimal)list.get(1))
						);
					} else {
						return (BigDecimal)list.get(0);
					}
				});


		// ======== array ========
		final Pattern<Character, List<Object>> elements = delimitedList(value, comma)
				.description("array elements")
				.values();

		final Pattern<Character, List<Object>> array = sequence(
				whiteSpace,
				constant("[").forget(),
				whiteSpace,
				optional(elements),
				whiteSpace,
				constant("]").forget(),
				whiteSpace
		)
				.description("array")
				.values();

		// ======== object ========
		final Pattern<Character, Map.Entry<String, Object>> member =
				sequence(whiteSpace, string, whiteSpace, constant(':').forget(), whiteSpace, value, whiteSpace)
						.values()
						.map(list->new AbstractMap.SimpleEntry<>(list.get(0).toString(), list.get(1)));


		final Pattern<Character, Map<String, Object>> members = delimitedList(member, comma)
				.values()
				.<List<Map.Entry<String, Object>>>cast()
				.map(this::createMap);

		final Pattern<Character, Map<String, Object>> object = sequence(
				whiteSpace,
				constant("{").forget(),
				whiteSpace,
				optional(members),
				whiteSpace,
				constant("}").forget(),
				whiteSpace
		)
				.description("object")
				.map(value().orElse(new HashMap<>()))
				.cast();




		// ======== simpletons: true, false and null ========

		Pattern<Character, Object> nil = sequence(whiteSpace, constant("null"), whiteSpace)
				.description("null")
				.save(null);


		Pattern<Character, Object> truth = sequence(whiteSpace, constant("true"), whiteSpace)
				.description("true")
				.save(Boolean.TRUE);


		Pattern<Character, Object> lie = sequence(whiteSpace, constant("false"), whiteSpace)
				.description("false")
				.save(Boolean.FALSE);

		//

		value.implement(
				anyOf(string, number, object, array, truth, lie, nil).description("hello")
		);

		return value;
	}

}
