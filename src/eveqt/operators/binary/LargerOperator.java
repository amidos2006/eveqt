package eveqt.operators.binary;

import java.util.HashMap;

import eveqt.EquationNode;

public class LargerOperator extends BinaryOperator{
    public LargerOperator(EquationNode left, EquationNode right) {
	super(left, right);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	if(this.left.checkSimilarity(this.right)) {
	    return 0;
	}
	return this.left.evaluate(variables) > this.right.evaluate(variables)?1:0;
    }
    
    @Override
    public String toString() {
	return "lg(" + this.left.toString() + "," + this.right.toString() + ")";
    }
    
    @Override
    public boolean isConstant() {
	if(this.left.checkSimilarity(this.right)) {
	    return true;
	}
	return super.isConstant();
    }
}
