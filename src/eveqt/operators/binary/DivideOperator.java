package eveqt.operators.binary;

import java.util.HashMap;

import eveqt.EquationNode;

public class DivideOperator extends BinaryOperator{
    public DivideOperator(EquationNode left, EquationNode right) {
	super(left, right);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	if(this.left.checkSimilarity(this.right)) {
	    return 1;
	}
	return this.clamp(this.left.evaluate(variables) / this.right.evaluate(variables));
    }
    
    @Override
    public String toString() {
	return "divide(" + this.left.toString() + "," + this.right.toString() + ")";
    }
    
    @Override
    public boolean isConstant() {
	if(this.left.checkSimilarity(this.right)) {
	    return true;
	}
	return super.isConstant();
    }
}
