package eveqt.operators.binary;

import java.util.HashMap;

import eveqt.EquationNode;

public class AddOperator extends BinaryOperator{
    public AddOperator(EquationNode left, EquationNode right) {
	super(left, right);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	return this.left.evaluate(variables) + this.right.evaluate(variables);
    }
    
    @Override
    public String toString() {
	return "add(" + this.left.toString() + "," + this.right.toString() + ")";
    }
}
