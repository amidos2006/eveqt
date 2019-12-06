package eveqt;

import java.util.HashMap;

/**
 * The base class to all the equation nodes
 * @author AhmedKhalifa
 */
public abstract class EquationNode implements Cloneable  {
    /**
     * definition of infinity for safe operators
     */
    public static double infinityValue = 1e10;
    /**
     * definition of nan for safe operators
     */
    public static double nanValue = 0;
    /**
     * The parent node to my current node
     */
    protected EquationNode parent;
    
    /**
     * get a clone of the current tree
     * @return a deep clone of the current full tree
     */
    public Object clone() throws CloneNotSupportedException {
	return (EquationNode) super.clone();
    }
    
    /**
     * Get the parent node
     * @return the parent node
     */
    public EquationNode getParent() {
	return this.parent;
    }
    
    /**
     * Set the parent node
     * @param parent the new parent
     */
    public void setParent(EquationNode parent) {
	this.parent = parent;
    }
    
    /**
     * Evaluate the equation tree using the input variable values
     * @param variables a dictionary of the variable values used during evaluation
     * @return the result of the equation tree
     */
    public abstract double evaluate(HashMap<String, Double> variables);
    
    /**
     * Get a string format of the whole tree
     * @return the equation tree as a string
     */
    public abstract String toString();
    
    /**
     * get the children of the current node (0 if terminal, 1 if unary, and 2 if binary)
     * @return the children of the current node
     */
    public abstract EquationNode[] getChildren();
    
    /**
     * set a new child to one of the parent children (only work on unary and binary operators)
     * @param index the index of the child (0 or 1)
     * @param newChild the new child that need to be set
     */
    public abstract void setChild(int index, EquationNode newChild);
    
    /**
     * get if the current tree can be collapsed to a constant
     * @return true if it can be simplified to a constant and false otherwise
     */
    public abstract boolean isConstant();
    
    /**
     * get the current depth of the node in the tree (0 if root)
     * @return the depth in the tree of the current node
     */
    public int getCurrentDepth() {
	if(this.parent == null) {
	    return 0;
	}
	return this.parent.getCurrentDepth() + 1;
    }
    
    /**
     * get the depth of the underlying tree where the current node is the root for it
     * @return the depth of the underlying tree where the current node is the root for it
     */
    public int getTreeDepth(){
	EquationNode[] children = this.getChildren();
	if(children.length == 0){
	    return 1;
	}
	int depth = 0;
	for(int i=0; i<children.length; i++){
	    int temp = children[i].getTreeDepth();
	    if(temp > depth){
		depth = temp;
	    }
	}
	return depth + 1;
    }
    
    /**
     * check if the current tree is similar to the input tree
     * @param n the compared tree
     * @return true if they are identical and false otherwise
     */
    public boolean checkSimilarity(EquationNode n){
	EquationNode[] children1 = this.getChildren();
	EquationNode[] children2 = n.getChildren();
	
	if(children1.length != children2.length){
	    return false;
	}
	if(!this.getClass().equals(n.getClass())){
	    return false;
	}
	for(int i=0; i<children1.length; i++){
	    if(!children1[i].checkSimilarity(children2[i])){
		return false;
	    }
	}
	
	return true;
    }
}
