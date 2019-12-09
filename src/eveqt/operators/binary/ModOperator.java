package eveqt.operators.binary;

import java.util.HashMap;

import eveqt.EquationNode;

public class ModOperator extends BinaryOperator{
    public ModOperator(EquationNode left, EquationNode right) {
	super(left, right);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	if(this.left.checkSimilarity(this.right)) {
	    return 1;
	}
	double rightValue = this.right.evaluate(variables);
	if(rightValue == 0) {
	    return EquationNode.nanValue;
	}
	return this.left.evaluate(variables) % rightValue;
    }
    
    @Override
    public String toString() {
	return "mod(" + this.left.toString() + "," + this.right.toString() + ")";
    }
    
    @Override
    public boolean isConstant() {
	if(this.left.checkSimilarity(this.right)) {
	    return true;
	}
	if(this.right.isConstant() && this.right.evaluate(null) == 0) {
	    return true;
	}
	return super.isConstant();
    }
}
