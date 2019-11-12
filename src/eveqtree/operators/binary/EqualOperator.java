package eveqtree.operators.binary;

import java.util.HashMap;

import eveqtree.EquationNode;

public class EqualOperator extends BinaryOperator{
    public EqualOperator(EquationNode left, EquationNode right) {
	super(left, right);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	return this.left.evaluate(variables) == this.right.evaluate(variables)?1:0;
    }
    
    @Override
    public String toString() {
	return "eq(" + this.left.toString() + "," + this.right.toString() + ")";
    }
}
