package eveqt.operators.unary;

import java.util.HashMap;

import eveqt.EquationNode;

public class CosOperator extends UnaryOperator{
    public CosOperator(EquationNode child) {
	super(child);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	return Math.cos(this.child.evaluate(variables));
    }

    @Override
    public String toString() {
	return "cos(" + this.child.toString() + ")";
    }
}
