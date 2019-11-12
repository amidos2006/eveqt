package eveqt.operators.unary;

import java.util.HashMap;

import eveqt.EquationNode;

public class LogOperator extends UnaryOperator{
    public LogOperator(EquationNode child) {
	super(child);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	return Math.log(Math.abs(this.child.evaluate(variables)));
    }

    @Override
    public String toString() {
	return "ln(" + this.child.toString() + ")";
    }
}
