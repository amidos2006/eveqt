package eveqtree.operators.unary;

import java.util.HashMap;

import eveqtree.EquationNode;

public class FloorOperator extends UnaryOperator{
    public FloorOperator(EquationNode child) {
	super(child);
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	return Math.floor(this.child.evaluate(variables));
    }

    @Override
    public String toString() {
	return "floor(" + this.child.toString() + ")";
    }

}
