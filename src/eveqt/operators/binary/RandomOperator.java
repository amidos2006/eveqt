package eveqt.operators.binary;

import java.util.HashMap;
import java.util.Random;

import eveqt.EquationNode;

public class RandomOperator extends BinaryOperator {
    private Random random;
    
    public RandomOperator(Random random, EquationNode left, EquationNode right) {
	super(left, right);
	
	this.random = random;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
	RandomOperator clone = (RandomOperator) super.clone();
	clone.random = this.random;
	return clone;
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) {
	double highBound = this.right.evaluate(variables);
	double lowBound = this.left.evaluate(variables);
	if(highBound < lowBound) {
	    double temp = highBound;
	    highBound = lowBound;
	    lowBound = temp;
	}
	if(highBound - lowBound <= 0) {
	    return lowBound;
	}
	return random.nextDouble() * (highBound - lowBound) + lowBound;
    }

    @Override
    public String toString() {
	return "rand(" + left.toString() + "," + right.toString() + ")";
    }
    
    @Override
    public boolean isConstant() {
	if(this.left.isConstant() && this.right.isConstant() && this.left.evaluate(null) - this.right.evaluate(null) == 0) {
	    return true;
	}
	return false;
    }
}
