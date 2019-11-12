package eveqt.operators.binary;

import java.util.HashMap;

import eveqt.EquationNode;

public class MinOperator extends BinaryOperator{
    public MinOperator(EquationNode left, EquationNode right) {
	super(left, right);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	return Math.min(this.left.evaluate(variables), this.right.evaluate(variables));
    }

    @Override
    public String toString() {
	return "min(" + this.left.toString() +  "," + this.right.toString() + ")";
    }
}
