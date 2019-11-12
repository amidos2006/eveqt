package eveqt.operators.binary;

import java.util.HashMap;

import eveqt.EquationNode;

public class MaxOperator extends BinaryOperator{
    public MaxOperator(EquationNode left, EquationNode right) {
	super(left, right);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	return Math.max(this.left.evaluate(variables), this.right.evaluate(variables));
    }

    @Override
    public String toString() {
	return "max(" + this.left.toString() + "," + this.right.toString() + ")";
    }
}
