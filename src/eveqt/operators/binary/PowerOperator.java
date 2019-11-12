package eveqt.operators.binary;

import java.util.HashMap;

import eveqt.EquationNode;

public class PowerOperator extends BinaryOperator{
    public PowerOperator(EquationNode left, EquationNode right) {
	super(left, right);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	return Math.pow(this.left.evaluate(variables), this.right.evaluate(variables));
    }

    @Override
    public String toString() {
	return "pow(" + this.left.toString() + "," + this.right.toString() + ")";
    }
}
