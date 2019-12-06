package eveqt.operators.binary;

import java.util.HashMap;

import eveqt.EquationNode;

public class PowerOperator extends BinaryOperator{
    public PowerOperator(EquationNode left, EquationNode right) {
	super(left, right);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	if(this.right.evaluate(variables) == 0) {
	    return 1.0;
	}
	if(Math.abs(this.right.evaluate(variables)) < 1 && this.left.evaluate(variables) < 0) {
	    return this.nanValue;
	}
	return Math.pow(this.left.evaluate(variables), this.right.evaluate(variables));
    }

    @Override
    public String toString() {
	return "pow(" + this.left.toString() + "," + this.right.toString() + ")";
    }
    
    @Override
    public boolean isConstant() {
	if(this.right.isConstant() && this.right.evaluate(null) == 0) {
	    return true;
	}
	return super.isConstant();
    }
}
