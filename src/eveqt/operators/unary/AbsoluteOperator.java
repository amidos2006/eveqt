package eveqt.operators.unary;

import java.util.HashMap;

import eveqt.EquationNode;

public class AbsoluteOperator extends UnaryOperator{
    public AbsoluteOperator(EquationNode child) {
	super(child);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	return Math.abs(this.child.evaluate(variables));
    }

    @Override
    public String toString() {
	return "abs(" + this.child.toString() + ")";
    }

}
