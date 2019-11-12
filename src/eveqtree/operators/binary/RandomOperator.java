package eveqtree.operators.binary;

import java.util.HashMap;
import java.util.Random;

import eveqtree.EquationNode;

public class RandomOperator extends BinaryOperator {
    public final static int INTEGER = 0;
    public final static int FLOAT = 1;
    
    private Random random;
    private int type;
    
    public RandomOperator(Random random, int type, EquationNode left, EquationNode right) {
	super(left, right);
	
	this.random = random;
	this.type = type;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
	RandomOperator clone = (RandomOperator) super.clone();
	clone.random = this.random;
	clone.type = this.type;
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
	if(type == RandomOperator.INTEGER){
	    if((int)(highBound - lowBound) <= 0) {
		    return (int)lowBound;
	    }
	    return random.nextInt((int)(highBound - lowBound)) + lowBound;
	}
	if(highBound - lowBound <= 0) {
	    return lowBound;
	}
	return random.nextDouble() * (highBound - lowBound) + lowBound;
    }

    @Override
    public String toString() {
	String stringType = "randFloat";
	if(type == RandomOperator.INTEGER){
	    stringType = "randInt";
	}
	return stringType + "(" + left.toString() + "," + right.toString() + ")";
    }

}
