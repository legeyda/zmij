package com.legeyda.script;

public class Sum extends BinaryOperation {

    public Sum(Expression operand1, Expression operand2) {
        super(operand1, operand2);
    }

    @Override
    public void evaluate() {
        this.operand1().evaluate();
        this.operand2().evaluate();
    }
}
