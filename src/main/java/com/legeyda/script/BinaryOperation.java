package com.legeyda.script;

public abstract class BinaryOperation implements Expression {
    private final Expression operand1, operand2;

    public BinaryOperation(Expression operand1, Expression operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public Expression operand1() {
        return this.operand1;
    }
    public Expression operand2() {
        return this.operand2;
    }
}
