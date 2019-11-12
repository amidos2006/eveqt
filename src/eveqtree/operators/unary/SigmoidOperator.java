package eveqtree.operators.unary;

import java.util.HashMap;

import eveqtree.EquationNode;

public class SigmoidOperator extends UnaryOperator{
    public SigmoidOperator(EquationNode child) {
	super(child);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	return 1.0 / (1.0 + Math.exp(-this.child.evaluate(variables)));
    }

    @Override
    public String toString() {
	return "sigmoid(" + this.child.toString() + ")";
    }
}
