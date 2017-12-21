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
		PatternDeclaration<Character, Object> value = declare("any piece of data");


		final Pattern<Character, Tree> whiteSpace = zeroOrMore(whiteList(' ', '\t', '\n', '\r'))
				.description("optional white space")
				.forget();

		final Pattern<Character, Tree> comma  = sequence(whiteSpace, constant(','), whiteSpace)
				.description("comma")
				.forget();



		// ======== string ========

		final Pattern<Character, ?> doubleQuote = constant('"').forget();

		final Pattern<Character,  Character> unicode = sequence(
				constant("\\u").forget(),
				repeat(whiteList("0123456789ABCDEF"), 4, false)// .map(asString());
		)
				.listValues()
				.map((list) -> (char)Long.parseLong("" + new ListCharSequence((List<Character>)(List)list), 16))
				.description("unicode code");
		//      .map(

		final Pattern<Character,  Character> character = anyOf(
				blackList('\\', '"').map(t->(Character)t.value().get()),
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
//				.listValues()
//				.map(seq -> "" + new ListCharSequence(Trees.childValues(seq.children().get(2))))


				.map(seq -> "" + new ListCharSequence(Trees.childValues(seq.children().get(2))))
				.description("string");


		// ======== number =========

		final Pattern<Character, Character> digit = whiteList("1234567890").value();

		final Pattern<Character, Character> nonZeroDigit = whiteList("123456789").value();

		final Pattern<Character, Long> optSign = optional(whiteList('+', '-'))
				.optValue()
				.map(opt->opt.map(s->'-'==(Character)s ? -1L : 1L).orElse(1L));
		//      .map(value().orElse('+').andThen(mapping('+', 1L, '-', -1L).orThrow()))
		//      .map(value().map(mapping('+', 1L, '-', -1L)).orElse(1L)))

		final Pattern<Character, Long> unsignedInteger = sequence(digit, zeroOrMore(nonZeroDigit))
				.listValues()
				.map(list->Long.valueOf(new ListCharSequence((List<Character>)(List<?>)list).toString()));

		final Pattern<Character, Long> signedInteger = sequence(optSign, unsignedInteger)
				.map(seq ->
						(Long)seq.children().get(0).value().get() * (Long)seq.children().get(1).value().get());
		//              .map(values()
		//                         .<List<Long>>cast()
		//                         .andThen(list->list.get(0) * list.get(1)));

		final Pattern<Character, Double> unsignedFloat = sequence(zeroOrMore(nonZeroDigit), constant('.'), oneOrMore(digit))
				.listValues()
				.map(list->Double.valueOf(new ListCharSequence((List<Character>)(List<?>)list).toString()));

		final Pattern<Character, Double> signedFloat = sequence(optSign, unsignedFloat)
				.map(seq ->
						(Long)seq.children().get(0).value().get() * (Double)seq.children().get(1).value().get());



		//final Pattern<Character, Double> floating = sequence(signedInteger, optional());

		final Pattern<Character, Number> jsonNumber = sequence(
				whiteSpace,
				anyOf(signedFloat, signedInteger),
				optional(sequence(
						whiteList('e', 'E').forget(),
						signedInteger
				)),
				whiteSpace
		)
				.listValues()
				.description("number")
				.map(list -> {
					if(list.size()==2) {
						return ((Number)list.get(0)).doubleValue() * Math.pow(10.0, ((Number)list.get(1)).doubleValue());
					} else {
						return (Number)list.get(0);
					}
				});
		//      .map(values())
		// whiteList('e', 'E').forget();


		// ======== array ========
		final Pattern<Character, List<Object>> elements = sequence(value, zeroOrMore(sequence(comma, value)))
				.description("array elements")
				.listValues();
		//      .map(listValues());

		final Pattern<Character, List<?>> array = sequence(
				whiteSpace,
				constant("["),
				whiteSpace,
				optional(elements),
				whiteSpace,
				constant("]"),
				whiteSpace
		)
				.description("array")
				.map(seq -> (List<?>)seq.children().get(3).value().orElse(new LinkedList<>()));
		//      .map().value().map().orElse(new LinkedList<>()));


		// ======== object ========
		final Pattern<Character, Map.Entry<String, Object>> member =
				sequence(whiteSpace, string, whiteSpace, constant(':').forget(), whiteSpace, value, whiteSpace)
						.listValues()
						.map(list->new AbstractMap.SimpleEntry<>(list.get(0).toString(), list.get(1)));


		final Pattern<Character, Map<String, Object>> members = sequence(member, zeroOrMore(sequence(comma, member)))
				.listValues()
				.map(entries -> {
					final Map<String, Object> result = new HashMap<>();
					for(Map.Entry<String, Object> entry: (List<Map.Entry<String, Object>>)(Object)entries) {
						result.put(entry.getKey(), entry.getValue());
					}
					return result;
				});

		final Pattern<Character, Map<String, Object>> object = sequence(
				whiteSpace,
				constant("{"),
				whiteSpace,
				optional(members),
				whiteSpace,
				constant("}"),
				whiteSpace
		)
				.description("object")
				.map(seq -> (Map<String, Object>)seq.children().get(3).value().orElse(new HashMap<>()));




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

		value.implement(anyOf(
				string, jsonNumber, object, array, truth, lie, nil));

		return value;
	}

}
