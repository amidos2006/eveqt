package eveqt.operators.binary;

import java.util.HashMap;

import eveqt.EquationNode;

public class DivideOperator extends BinaryOperator{
    public DivideOperator(EquationNode left, EquationNode right) {
	super(left, right);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	double rightValue = this.right.evaluate(variables);
	if(rightValue == 0) {
	    return 0;
	}
	return this.left.evaluate(variables) / rightValue;
    }
    
    @Override
    public String toString() {
	return "divide(" + this.left.toString() + "," + this.right.toString() + ")";
    }
    
    @Override
    public boolean isConstant() {
	if(this.right.isConstant() && this.right.evaluate(null) == 0) {
	    return true;
	}
	return super.isConstant();
    }
}
