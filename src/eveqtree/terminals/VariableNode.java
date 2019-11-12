package eveqtree.terminals;

import java.util.HashMap;

public class VariableNode extends TerminalNode {
    private String variableName;
    
    public VariableNode(String variableName) {
	super();
	this.variableName = variableName;
    }
    
    @Override
    public String getValue() {
	return this.variableName;
    }

    @Override
    public void setValue(String value) {
	this.variableName = value;
    }
    
    @Override
    public double evaluate(HashMap<String, Double> variables) {
	return variables.get(this.variableName).doubleValue();
    }

    @Override
    public String toString() {
	return this.variableName;
    }

    @Override
    public boolean isConstant() {
	return false;
    }
}
