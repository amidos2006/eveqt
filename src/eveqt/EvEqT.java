package eveqt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import eveqt.operators.binary.BinaryOperator;
import eveqt.operators.unary.UnaryOperator;
import eveqt.terminals.*;

/**
 * A static class where all the evolutionary tree operation can be done
 * @author AhmedKhalifa
 */
@SuppressWarnings("serial")
public class EvEqT {
    /**
     * private function to get all tree nodes that are at a certain depth
     * @param eq the current node that is being tested
     * @param currentDepth the current depth of that node
     * @param targetDepth the target depth needed to be reached
     * @return a list of all the nodes at that specific depth
     */
    private static ArrayList<EquationNode> getEquationsAtDepth(EquationNode eq, int currentDepth, int targetDepth){
	if(currentDepth >= targetDepth) {
	    return new ArrayList<EquationNode>() {{add(eq);}};
	}
	ArrayList<EquationNode> results = new ArrayList<EquationNode>();
	EquationNode[] children = eq.getChildren();
	for(EquationNode c:children) {
	    results.addAll(getEquationsAtDepth(c, currentDepth+1, targetDepth));
	}
	return results;
    }
    
    /**
     * private function to get all the nodes that the tree underneath them is shorter than maxDepth
     * @param eq the current node that is being tested
     * @param currentDepth the current depth of that node
     * @param maxDepth the maximum depth of the tree
     * @return a list of all the nodes that have short trees underneath
     */
    private static ArrayList<EquationNode> getEquationsWithLessDepth(EquationNode eq, int currentDepth, int maxDepth){
	ArrayList<EquationNode> results = new ArrayList<EquationNode>();
	if(currentDepth + eq.getTreeDepth() < maxDepth) {
	    results.add(eq);
	}
	
	EquationNode[] children = eq.getChildren();
	for(EquationNode c:children) {
	    results.addAll(getEquationsAtDepth(c, currentDepth+1, maxDepth));
	}
	return results;
    } 
    
    /**
     * Generate constants hashset to be used in evolution (where only fixed set of constants is being used)
     * @param maxValue1 the maximum value where the constants are generated as an arithmetic sequence with distance
     * @param distance the difference in the arithmetic sequence
     * @param maxValue2 the maximum value where the constants are generated as a geometric sequence with multiplier equal to multipler
     * @param multiplier the multiplier in the geometric sequence
     * @return a Hashset of constants that should be used in generation
     */
    public static HashSet<Double> generateConstants(double maxValue1, int distance, double maxValue2, int multiplier){
	if(maxValue1 > maxValue2) {
	    double temp=maxValue2;
	    maxValue2=maxValue1;
	    maxValue1=temp;
	}
	HashSet<Double> values = new HashSet<Double>();
	values.add(new Double(0));
	values.add(new Double(1));
	for(double v=2; v<=maxValue1; v+=distance) {
	    values.add(new Double(v));
	    values.add(new Double(-v));
	    values.add(new Double(1.0/v));
	    values.add(new Double(-1.0/v));
	}
	for(double v=maxValue1; v<=maxValue2; v*=multiplier) {
	    values.add(new Double(v));
	    values.add(new Double(-v));
	    values.add(new Double(1.0/v));
	    values.add(new Double(-1.0/v));
	}
	return values;
    }
    
    /**
     * Generate constants hashset to be used in evolution (where only fixed set of constants is being used) where distance is 1 and multiplier is 10
     * @param maxValue1 the maximum value where the constants are generated as an arithmetic sequence with distance is 1
     * @param maxValue2 the maximum value where the constants are generated as a geometric sequence with multiplier equal to 10
     * @return a Hashset of constants that should be used in generation
     */
    public static HashSet<Double> generateConstants(double maxValue1, double maxValue2){
	return generateConstants(maxValue1, 1, maxValue2, 10);
    }
    
    /**
     * generates a random equation string
     * @param parser the parser object that have all the variable names and constants
     * @param maxDepth the maximum depth of the equation
     * @param addTerminals additional terminals to be added to the list of terminals
     * @param constProb the probability of a terminal node to be a constant instead of variable
     * @param unaryProb the probability of the operator node to be unary instead of binary
     * @return a string that can be parsed to an equation
     */
    public static String generateRandomStringEquation(EquationParser parser, int maxDepth, ArrayList<String> addTerminals, double constProb, double unaryProb) {
	ArrayList<String> terminals = parser.getVariables();
	if(addTerminals != null) {
	    terminals.addAll(addTerminals);
	}
    	ArrayList<String> constants = parser.getConstants();
	Random rnd = parser.getRandom();
	
	if(maxDepth <= 0){
	    double value = rnd.nextDouble() ;
	    if(value < constProb){
		return constants.get(rnd.nextInt(constants.size()));
	    }
	    else{
		return terminals.get(rnd.nextInt(terminals.size()));
	    }
	}
	
	if(rnd.nextDouble() < unaryProb){
	    return parser.getRandomOperator(true) + "(" + generateRandomStringEquation(parser, maxDepth - rnd.nextInt(maxDepth) - 1, addTerminals, constProb, unaryProb) + ")";
	}
	
	return parser.getRandomOperator(false) + "(" + generateRandomStringEquation(parser, maxDepth - rnd.nextInt(maxDepth) - 1, addTerminals, constProb, unaryProb) + "," + 
		generateRandomStringEquation(parser, maxDepth - rnd.nextInt(maxDepth) - 1, addTerminals, constProb, unaryProb) + ")";
    }
    
    /**
     * generate a random equation tree
     * @param parser the parser object that have all the variable names and constants
     * @param maxDepth the maximum depth of the equation
     * @param addTerminals additional terminals to be added to the list of terminals
     * @param constProb the probability of a terminal node to be a constant instead of variable
     * @param unaryProb the probability of the operator node to be unary instead of binary
     * @return a randomly generated equation tree
     */
    public static EquationNode generateRandomTreeEquation(EquationParser parser, int maxDepth, ArrayList<String> addTerminals, double constProb, double unaryProb) throws Exception {
	return parser.parse(generateRandomStringEquation(parser, maxDepth, addTerminals, constProb, unaryProb));
    }
    
    /**
     * generate a random equation tree with constProb equal to 0.5 and unaryProb equal to 0.5
     * @param parser the parser object that have all the variable names and constants
     * @param maxDepth the maximum depth of the equation
     * @param addTerminals additional terminals to be added to the list of terminals
     * @return a randomly generated equation tree
     */
    public static EquationNode generateRandomTreeEquation(EquationParser parser, int maxDepth, ArrayList<String> addTerminals) throws Exception {
	return generateRandomTreeEquation(parser, maxDepth, addTerminals, 0.5, 0.5);
    }
    
    /**
     * generate a random equation tree with constProb equal to 0.5 and unaryProb equal to 0.5
     * @param parser the parser object that have all the variable names and constants
     * @param maxDepth the maximum depth of the equation
     * @return a randomly generated equation tree
     */
    public static EquationNode generateRandomTreeEquation(EquationParser parser, int maxDepth) throws Exception {
	return generateRandomTreeEquation(parser, maxDepth, null, 0.5, 0.5);
    }
    
    /**
     * get a new equation tree with a random node being erased from the tree
     * @param parser the parser object that have all the variable names and constants 
     * @param eq the current equation tree where a node need to be removed
     * @return a new tree similar to eq with one less node
     */
    public static EquationNode deleteNode(EquationParser parser, EquationNode eq) throws Exception{
	EquationNode clone = (EquationNode)eq.clone();
	int maxDepth = clone.getTreeDepth() - 1;
	if(maxDepth <= 0) {
	    return clone;
	}
	int targetDepth = parser.getRandom().nextInt(maxDepth);
	ArrayList<EquationNode> possNodes = getEquationsAtDepth(clone, 0, targetDepth);
	for(int i=0;i<possNodes.size();i++) {
	    if(possNodes.get(i) instanceof TerminalNode) {
		possNodes.remove(i);
		i--;
	    }
	}
	if(possNodes.size() == 0) {
	    return clone;
	}
	
	EquationNode selected=possNodes.get(parser.getRandom().nextInt(possNodes.size()));
	EquationNode parent=selected.getParent();
	EquationNode[] children=selected.getChildren();
	EquationNode child=children[parser.getRandom().nextInt(children.length)];
	
	if(parent == null) {
	    child.setParent(null);
	    return child;
	}
	
	int selectedIndex=0;
	for(int i=0; i<parent.getChildren().length; i++) {
	    if(parent.getChildren()[i] == selected) {
		selectedIndex = i;
		break;
	    }
	}
	parent.setChild(selectedIndex, child);
	child.setParent(parent);
	return clone;
    }
    
    /**
     * get a new equation tree where one of the constants has changed randomly
     * @param parser the parser object that have all the variable names and constants 
     * @param eq the current equation tree where a node need to be removed
     * @return a new equation tree similar to eq but with a constant node changed to another value
     */
    public static EquationNode changeConstant(EquationParser parser, EquationNode eq) throws Exception {
	EquationNode clone = (EquationNode) eq.clone();
	
	ArrayList<ConstantNode> constants = new ArrayList<ConstantNode>();
	ArrayList<EquationNode> queue = new ArrayList<EquationNode>(){{add(clone);}};
	while(queue.size() > 0) {
	    EquationNode node=queue.remove(0);
	    if(node instanceof ConstantNode) {
		constants.add((ConstantNode)node);
		continue;
	    }
	    EquationNode[] children=node.getChildren();
	    for(EquationNode c:children) {
		queue.add(c);
	    }
	}
	if(constants.size() == 0) {
	    return clone;
	}
	ArrayList<String> possConstants=parser.getConstants();
	Random rnd=parser.getRandom();
	constants.get(rnd.nextInt(constants.size())).setValue(possConstants.get(rnd.nextInt(possConstants.size())));
	return clone;
    }
    
    /**
     * get a new equation tree where one of the variable has changed randomly
     * @param parser the parser object that have all the variable names and constants 
     * @param eq the current equation tree where a node need to be removed
     * @return a new equation tree similar to eq but with a variable node changed to another value
     */
    public static EquationNode changeVariable(EquationParser parser, EquationNode eq) throws Exception {
	EquationNode clone = (EquationNode) eq.clone();
	
	ArrayList<VariableNode> variables = new ArrayList<VariableNode>();
	ArrayList<EquationNode> queue = new ArrayList<EquationNode>(){{add(clone);}};
	while(queue.size() > 0) {
	    EquationNode node=queue.remove(0);
	    if(node instanceof VariableNode) {
		variables.add((VariableNode)node);
		continue;
	    }
	    EquationNode[] children=node.getChildren();
	    for(EquationNode c:children) {
		queue.add(c);
	    }
	}
	if(variables.size() == 0) {
	    return clone;
	}
	ArrayList<String> possVars=parser.getVariables();
	Random rnd=parser.getRandom();
	variables.get(rnd.nextInt(variables.size())).setValue(possVars.get(rnd.nextInt(possVars.size())));
	return clone;
    }
    
    /**
     * get a new equation tree where one of the unary operator has changed randomly
     * @param parser the parser object that have all the variable names and constants 
     * @param eq the current equation tree where a node need to be removed
     * @return a new equation tree similar to eq but with a unary operator changed to another operator
     */
    public static EquationNode changeUnary(EquationParser parser, EquationNode eq) throws Exception {
	EquationNode clone = (EquationNode) eq.clone();
	
	ArrayList<UnaryOperator> operators = new ArrayList<UnaryOperator>();
	ArrayList<EquationNode> queue = new ArrayList<EquationNode>(){{add(clone);}};
	while(queue.size() > 0) {
	    EquationNode node=queue.remove(0);
	    if(node instanceof UnaryOperator) {
		operators.add((UnaryOperator)node);
		continue;
	    }
	    EquationNode[] children=node.getChildren();
	    for(EquationNode c:children) {
		queue.add(c);
	    }
	}
	if(operators.size() == 0) {
	    return clone;
	}
	Random rnd=parser.getRandom();
	UnaryOperator selected = operators.get(rnd.nextInt(operators.size()));
	EquationNode parent = selected.getParent();
	EquationNode newChild = parser.parse(parser.getRandomOperator(true) + "(" + 
		selected.getChildren()[0] + ")");
	if(parent == null) {
	    return newChild;
	}
	int selectedIndex=0;
	for(int i=0; i<parent.getChildren().length; i++) {
	    if(parent.getChildren()[i] == selected) {
		selectedIndex = i;
		break;
	    }
	}
	parent.setChild(selectedIndex, newChild);
	return clone;
    }
    
    /**
     * get a new equation tree where one of the binary operator has changed randomly
     * @param parser the parser object that have all the variable names and constants 
     * @param eq the current equation tree where a node need to be removed
     * @return a new equation tree similar to eq but with a binary operator changed to another operator
     */
    public static EquationNode changeBinary(EquationParser parser, EquationNode eq) throws Exception {
	EquationNode clone = (EquationNode) eq.clone();
	
	ArrayList<BinaryOperator> operators = new ArrayList<BinaryOperator>();
	ArrayList<EquationNode> queue = new ArrayList<EquationNode>(){{add(clone);}};
	while(queue.size() > 0) {
	    EquationNode node=queue.remove(0);
	    if(node instanceof BinaryOperator) {
		operators.add((BinaryOperator)node);
		continue;
	    }
	    EquationNode[] children=node.getChildren();
	    for(EquationNode c:children) {
		queue.add(c);
	    }
	}
	if(operators.size() == 0) {
	    return clone;
	}
	Random rnd=parser.getRandom();
	BinaryOperator selected = operators.get(rnd.nextInt(operators.size()));
	EquationNode parent = selected.getParent();
	EquationNode newChild = parser.parse(parser.getRandomOperator(false) + "(" + 
		selected.getChildren()[0] + "," + selected.getChildren()[1] + ")");
	if(parent == null) {
	    return newChild;
	}
	int selectedIndex=0;
	for(int i=0; i<parent.getChildren().length; i++) {
	    if(parent.getChildren()[i] == selected) {
		selectedIndex = i;
		break;
	    }
	}
	parent.setChild(selectedIndex, newChild);
	return clone;
    }
    
    /**
     * get a new equation tree where one of the nodes has been changed to something else
     * @param parser the parser object that have all the variable names and constants 
     * @param eq the current equation tree where a node need to be removed
     * @return a new equation tree similar to eq but with one of the nodes has been changed to something else
     */
    public static EquationNode changeNode(EquationParser parser, EquationNode eq) throws Exception{
	Random rnd=parser.getRandom();
	double value = rnd.nextDouble();
	if(value < 0.25) {
	    value += 0.25;
	    EquationNode newNode = changeConstant(parser, eq);
	    if(!newNode.checkSimilarity(eq)) {
		return newNode;
	    }
	}
	if(value < 0.5) {
	    value += 0.25;
	    EquationNode newNode = changeVariable(parser, eq);
	    if(!newNode.checkSimilarity(eq)) {
		return newNode;
	    }
	}
	if(value < 0.75){
	    value += 0.25;
	    EquationNode newNode = changeUnary(parser, eq);
	    if(!newNode.checkSimilarity(eq)) {
		return newNode;
	    }
	}
	return changeBinary(parser, eq);
    }
    
    /**
     * get a new equation tree with one new random node is inserted in
     * @param parser the parser object that have all the variable names and constants 
     * @param eq the current equation tree where a node need to be removed
     * @param maxDepth the maximum depth of the tree so the tree doesn't overgrow and explode
     * @param addTerminals additional terminals to be added to the list of terminals
     * @param constProb the probability of constant nodes to appear if the algorithm need to fill with a random tree
     * @param unaryProb the probability of unary operator to appear if the algorithm need to fill with a random tree
     * @return a new equation tree similar to eq with one new node added to the tree
     */
    public static EquationNode insertNode(EquationParser parser, EquationNode eq, int maxDepth, ArrayList<String> addTerminals, double constProb, double unaryProb) throws Exception {
	EquationNode clone = (EquationNode) eq.clone();
	ArrayList<EquationNode> allowedNodes = getEquationsWithLessDepth(clone, 0, maxDepth);
	if(allowedNodes.size() == 0) {
	    return clone;
	}
	Random rnd=parser.getRandom();
	EquationNode selected = allowedNodes.get(rnd.nextInt(allowedNodes.size()));
	EquationNode parent = selected.getParent();
	EquationNode newChild = null;
	if(rnd.nextDouble() < unaryProb) {
	    newChild = parser.parse(parser.getRandomOperator(true) + "(" + selected + ")");
	}
	else {
	    newChild = parser.parse(parser.getRandomOperator(false) + "(" + 
		    selected + "," + generateRandomStringEquation(parser, maxDepth-selected.getCurrentDepth(), addTerminals, constProb, unaryProb) + ")");
	}
	if(parent == null) {
	    return newChild;
	}
	int selectedIndex=0;
	for(int i=0; i<parent.getChildren().length; i++) {
	    if(parent.getChildren()[i] == selected) {
		selectedIndex = i;
		break;
	    }
	}
	parent.setChild(selectedIndex, newChild);
	return clone;
    }
    
    /**
     * get a new equation tree with one new random node is inserted in where constProb is 0.5 and unaryProb is 0.5
     * @param parser the parser object that have all the variable names and constants 
     * @param eq the current equation tree where a node need to be removed
     * @param maxDepth the maximum depth of the tree so the tree doesn't overgrow and explode
     * @param addTerminals additional terminals to be added to the list of terminals
     * @return a new equation tree similar to eq with one new node added to the tree
     */
    public static EquationNode insertNode(EquationParser parser, EquationNode eq, int maxDepth, ArrayList<String> addTerminals) throws Exception{
	return insertNode(parser, eq, maxDepth, addTerminals, 0.5, 0.5);
    }
    
    /**
     * get a new equation tree with one new random node is inserted in where constProb is 0.5 and unaryProb is 0.5
     * @param parser the parser object that have all the variable names and constants 
     * @param eq the current equation tree where a node need to be removed
     * @param maxDepth the maximum depth of the tree so the tree doesn't overgrow and explode
     * @return a new equation tree similar to eq with one new node added to the tree
     */
    public static EquationNode insertNode(EquationParser parser, EquationNode eq, int maxDepth) throws Exception{
	return insertNode(parser, eq, maxDepth, null, 0.5, 0.5);
    }
    
    /**
     * get a new equation tree after simplifying any subtrees that contain only constant and operators
     * @param parser the parser object that have all the variable names and constants 
     * @param eq the current equation tree where a node need to be removed
     * @return a new equation tree with all the constant nodes collapse to a single one
     */
    public static EquationNode simplifyTree(EquationParser parser, EquationNode eq) throws Exception {
	if(eq.isConstant()) {
	    return new ConstantNode(parser.getClosestDouble(eq.evaluate(null)));
	}
	EquationNode clone = (EquationNode) eq.clone();
	EquationNode[] children = clone.getChildren();
	for(int i=0; i<children.length; i++) {
	    EquationNode newNode = simplifyTree(parser, children[i]);
	    clone.setChild(i, newNode);
	}
	return clone;
    }

    /**
     * get a new equation that is a mix of two equation trees. The hybrid equation won't go over any of their equation maxDepth
     * @param parser the parser object that have all the variable names and constants 
     * @param eq1 the first equation in the mixture
     * @param eq2 the second equation in the mixture
     * @return a new equation tree that is the frankenstein monster of the two input equations
     */
    public static EquationNode crossoverTrees(EquationParser parser, EquationNode eq1, EquationNode eq2) throws Exception {
	EquationNode clone1 = (EquationNode)eq1.clone();
	EquationNode clone2 = (EquationNode)eq2.clone();
	Random rnd = parser.getRandom();
	if(Math.min(eq1.getTreeDepth(), eq2.getTreeDepth()) <= 1) {
	    if(rnd.nextBoolean()) {
		return clone1;
	    }
	    return clone2;
	}
	int targetDepth = rnd.nextInt(Math.min(eq1.getTreeDepth(), eq2.getTreeDepth())-1) + 1;
	ArrayList<EquationNode> nodes1 = getEquationsAtDepth(clone1, 0, targetDepth);
	ArrayList<EquationNode> nodes2 = getEquationsAtDepth(clone2, 0, targetDepth);
	EquationNode selected1 = nodes1.get(rnd.nextInt(nodes1.size()));
	EquationNode selected2 = nodes2.get(rnd.nextInt(nodes2.size()));
	EquationNode parent = selected1.parent;
	int selectedIndex=0;
	for(int i=0; i<parent.getChildren().length; i++) {
	    if(parent.getChildren()[i] == selected1) {
		selectedIndex = i;
		break;
	    }
	}
	
	parent.setChild(selectedIndex, selected2);
	selected2.parent = parent;
	return clone1;
    }
}
