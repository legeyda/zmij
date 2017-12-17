package com.legeyda.script.variant;

public interface Variant {
	DataType dataType();
	String  asString();
	Integer asInteger();
	Long    asLong();
	Float   asFloat();
	Double  asDouble();
}
