package eveqtree.operators.unary;

import java.util.HashMap;

import eveqtree.EquationNode;

public class TanhOperator extends UnaryOperator{
    public TanhOperator(EquationNode child) {
	super(child);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	return Math.tanh(this.child.evaluate(variables));
    }

    @Override
    public String toString() {
	return "tanh(" + this.child.toString() + ")";
    }
}
