package com.legeyda.script;

import java.util.Stack;

public class Evaluator {

	Stack<Integer> stack = null;

	public void visit(final Sum sum) {
		sum.operand1();
	}

}
