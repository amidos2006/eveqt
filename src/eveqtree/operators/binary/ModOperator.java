package eveqtree.operators.binary;

import java.util.HashMap;

import eveqtree.EquationNode;

public class ModOperator extends BinaryOperator{
    public ModOperator(EquationNode left, EquationNode right) {
	super(left, right);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	int rightValue = (int)(this.right.evaluate(variables));
	if(rightValue == 0) {
	    return 0;
	}
	return (int)(this.left.evaluate(variables)) % rightValue;
    }

    @Override
    public String toString() {
	return "mod(" + this.left.toString() + "," + this.right.toString() + ")";
    }
    
    @Override
    public boolean isConstant() {
	if(this.right.isConstant() && this.right.evaluate(null) == 0) {
	    return true;
	}
	return super.isConstant();
    }
}
