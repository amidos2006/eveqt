package eveqtree.operators.unary;

import java.util.HashMap;

import eveqtree.EquationNode;

public class NegOperator extends UnaryOperator{
    public NegOperator(EquationNode child) {
	super(child);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	return -this.child.evaluate(variables);
    }

    @Override
    public String toString() {
	return "neg(" + this.child.toString() + ")";
    }

}
