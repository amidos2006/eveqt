package eveqt.operators.binary;

import java.util.HashMap;

import eveqt.EquationNode;

public class PowerOperator extends BinaryOperator{
    public PowerOperator(EquationNode left, EquationNode right) {
	super(left, right);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	double right = this.right.evaluate(variables);
	if(right == 0) {
	    return 1.0;
	}
	double left = this.left.evaluate(variables);
	if(Math.abs(right) < 1 && left < 0) {
	    return EquationNode.nanValue;
	}
	return this.clamp(Math.pow(left, right));
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
