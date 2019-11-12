package eveqt.operators.unary;

import java.util.HashMap;

import eveqt.EquationNode;

public class InvOperator extends UnaryOperator{
    public InvOperator(EquationNode child) {
	super(child);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	if(this.child.evaluate(variables) == 0) {
	    return 0;
	}
	return 1.0/this.child.evaluate(variables);
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
