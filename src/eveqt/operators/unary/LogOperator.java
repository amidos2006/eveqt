package eveqt.operators.unary;

import java.util.HashMap;

import eveqt.EquationNode;

public class LogOperator extends UnaryOperator{
    public LogOperator(EquationNode child) {
	super(child);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	double value = this.child.evaluate(variables);
	if(value <= 0) {
	    return -EquationNode.infinityValue;
	}
	return Math.log(value);
    }

    @Override
    public String toString() {
	return "ln(" + this.child.toString() + ")";
    }
}
