package eveqtree.operators.unary;

import eveqtree.EquationNode;

public abstract class UnaryOperator extends EquationNode {
    protected EquationNode child;
    
    public UnaryOperator(EquationNode child) {
	super();
	
	this.child = child;
	this.child.setParent(this);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
	UnaryOperator clone = (UnaryOperator) super.clone();
	if(this.child != null) {
	    clone.child = (EquationNode) this.child.clone();
	    clone.child.setParent(clone);
	}
	return clone;
    }
    
    @Override
    public EquationNode[] getChildren() {
	return new EquationNode[] {this.child};
    }
    
    @Override
    public void setChild(int index, EquationNode newChild) {
        if(index==0) {
            this.child=newChild;
        }
    }
    
    @Override
    public boolean isConstant() {
	return this.child.isConstant();
    }
}
