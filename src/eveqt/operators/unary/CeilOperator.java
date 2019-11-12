package eveqt.operators.unary;

import java.util.HashMap;

import eveqt.EquationNode;

public class CeilOperator extends UnaryOperator{
    public CeilOperator(EquationNode child) {
	super(child);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	return Math.ceil(this.child.evaluate(variables));
    }

    @Override
    public String toString() {
	return "ceil(" + this.child.toString() + ")";
    }

}
