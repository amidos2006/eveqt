package eveqtree.terminals;

import eveqtree.EquationNode;

public abstract class TerminalNode extends EquationNode {
    public TerminalNode() {
	super();
    }
    
    @Override
    public EquationNode[] getChildren() {
        return new EquationNode[0];
    }
    
    @Override
    public void setChild(int index, EquationNode newChild) {
    }
    
    public abstract String getValue();
    
    public abstract void setValue(String value);
}
