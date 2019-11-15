package eveqt.operators.binary;

import java.util.HashMap;

import eveqt.EquationNode;

public class DivOperator extends BinaryOperator{
    public DivOperator(EquationNode left, EquationNode right) {
	super(left, right);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	int rightValue = (int)(this.right.evaluate(variables));
	if(rightValue == 0) {
	    return 0;
	}
	if(this.left.checkSimilarity(this.right)) {
	    return 1;
	}
	return (int)(this.left.evaluate(variables)) / rightValue;
    }
    
    @Override
    public String toString() {
	return "div(" + this.left.toString() + "," + this.right.toString() + ")";
    }
    
    @Override
    public boolean isConstant() {
	if(this.right.isConstant() && this.right.evaluate(null) == 0) {
	    return true;
	}
	if(this.left.checkSimilarity(this.right)) {
	    return true;
	}
	return super.isConstant();
    }
}
