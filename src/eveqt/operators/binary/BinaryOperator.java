package eveqt.operators.binary;

import eveqt.EquationNode;

public abstract class BinaryOperator extends EquationNode {
    protected EquationNode left;
    protected EquationNode right;
    
    public BinaryOperator(EquationNode left, EquationNode right) {
	super();
	
	this.left = left;
	this.left.setParent(this);
	this.right = right;
	this.right.setParent(this);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
	BinaryOperator clone = (BinaryOperator) super.clone();
	if(this.left != null) {
	    clone.left = (EquationNode) this.left.clone();
	    clone.left.setParent(clone);
	}
	if(this.right != null) {
	    clone.right = (EquationNode) this.right.clone();
	    clone.right.setParent(clone);
	}
	return clone;
    }
    
    @Override
    public EquationNode[] getChildren() {
	return new EquationNode[] {this.left, this.right};
    }
    
    @Override
    public void setChild(int index, EquationNode newChild) {
        if(index==0) {
            this.left = newChild;
        }
        if(index==1) {
            this.right = newChild;
        }
    }
    
    @Override
    public boolean isConstant() {
	return this.left.isConstant() && this.right.isConstant();
    }
}
