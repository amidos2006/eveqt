package eveqt.operators.binary;

import java.util.HashMap;

import eveqt.EquationNode;

public class SubtractOperator extends BinaryOperator{
    public SubtractOperator(EquationNode left, EquationNode right) {
	super(left, right);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	if(this.left.checkSimilarity(this.right)) {
	    return 0;
	}
	return this.left.evaluate(variables) - this.right.evaluate(variables);
    }

    @Override
    public String toString() {
	return "sub(" + this.left.toString() + "," + this.right.toString() + ")";
    }
    
    @Override
    public boolean isConstant() {
	if(this.left.checkSimilarity(this.right)) {
	    return true;
	}
	return super.isConstant();
    }
}
