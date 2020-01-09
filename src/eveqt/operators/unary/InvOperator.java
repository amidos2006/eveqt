package eveqt.operators.unary;

import java.util.HashMap;

import eveqt.EquationNode;

public class InvOperator extends UnaryOperator{
    public InvOperator(EquationNode child) {
	super(child);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	double value = this.child.evaluate(variables);
	if(value == 0) {
	    return 0;
	}
	return this.clamp(1.0/value);
    }

    @Override
    public String toString() {
	return "inv(" + this.child.toString() + ")";
    }
    
    @Override
    public boolean isConstant() {
	if(this.child.isConstant() && this.child.evaluate(null) == 0) {
	    return true;
	}
	return super.isConstant();
    }
}
