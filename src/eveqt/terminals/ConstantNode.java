package eveqt.terminals;

import java.util.HashMap;

public class ConstantNode extends TerminalNode {
    private double value;
    
    public ConstantNode(double value) {
	super();
	this.value = value;
    }

    @Override
    public double evaluate(HashMap<String, Double> node) {
	return value;
    }
    
    @Override
    public String getValue() {
	return "" + this.value;
    }

    @Override
    public void setValue(String value) {
	this.value = Double.parseDouble(value);
    }

    @Override
    public String toString() {
	return Double.toString(this.value);
    }
    
    @Override
    public boolean isConstant() {
	return true;
    }
}
