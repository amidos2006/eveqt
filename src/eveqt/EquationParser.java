package eveqt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import eveqt.operators.binary.*;
import eveqt.operators.unary.*;
import eveqt.terminals.*;

/**
 * A parser class that can parse text and keep track of all the type of nodes that can be used
 * @author AhmedKhalifa
 *
 */
public class EquationParser {
    /**
     * List of all the binary operators that can be used by the system
     */
    public final String[] binaryOperator = new String[]{"add", "sub", "divide", "pow", "eq", "lg", "ls", "max", "min", 
		"mod", "mul", "pow", "randInt", "randFloat"};
    /**
     * List of all the unary operators that can be used by the system
     */
    public final String[] unaryOperator = new String[]{"log", "floor", "abs", "sigmoid", "tanh", "neg", "inv", "ceil", 
	    "sin", "cos"};
    
    /**
     * random variable that is used for all the randomness in the system
     */
    private Random rnd;
    /**
     * A set of unique variableNames that is being used in the system
     */
    private HashSet<String> varNames;
    /**
     * A set of unique constants that is being used in the system
     */
    private HashSet<Double> constValues;
    
    /**
     * Constructor for the parser class
     * @param random random object to be used in all operations
     * @param variableNames a set of all the variable names
     * @param constantValues a set of all the constants
     */
    public EquationParser(Random random, HashSet<String> variableNames, HashSet<Double> constantValues){
	this.rnd = random;
	this.varNames = variableNames;
	this.constValues = constantValues;
    }
    
    /**
     * Get a list of all the allowed variable names
     * @return a list of all the allowed variable names
     */
    public ArrayList<String> getVariables(){
	ArrayList<String> terminals = new ArrayList<String>();
	for(String t:this.varNames) {
	    terminals.add(t);
	}
    	return terminals;
    }
    
    /**
     * get a list of all the allowed constants
     * @return a list of all the allowed constants
     */
    public ArrayList<String> getConstants(){
	ArrayList<String> constants = new ArrayList<String>();
    	for(Double d:this.constValues) {
    	    constants.add(d.toString());
    	}
    	return constants;
    }
    
    /**
     * get the used random object
     * @return a random object that can be used in the system
     */
    public Random getRandom() {
	return this.rnd;
    }
    
    /**
     * get a random operator from the defined operator in the system
     * @param uniary true if the random operator should be uniary and false otherwise
     * @return a string that represent a certain operator allowed by the system
     */
    public String getRandomOperator(boolean uniary) {
	if(uniary) {
	    return this.unaryOperator[this.rnd.nextInt(this.unaryOperator.length)];
	}
	return this.binaryOperator[this.rnd.nextInt(this.binaryOperator.length)];
    }
    
    /**
     * get the closest constant from the set of constants to the input value
     * @param value the input value that we are trying to find a value close to it
     * @return the closest defined constant to the input value
     */
    public double getClosestDouble(double value) {
	double best=0;
	double dist=Double.MAX_VALUE;	
	for(Double v:this.constValues) {
	    double currentDist = Math.abs(v - value);
	    if(currentDist < dist) {
		best = v.doubleValue();
		dist = currentDist;
	    }
	}
	return best;
    }
    
    /**
     * a private function that transform the input name to the corresponding terminal node
     * @param name the string name that will be transformed to a terminal
     * @return a terminal node that correspond to the input name
     */
    private EquationNode getTerminal(String name) throws Exception{
	if(name.matches("^[\\-]?[0-9]+[\\.]?[0-9]*")){
	    return new ConstantNode(this.getClosestDouble(Double.parseDouble(name)));
	}
	else{
	    if (!this.varNames.contains(name)) {
		throw new Exception("Variable [" + name + "] is not defined in the allowed varNames.");
	    }
	    return new VariableNode(name);
	}
    }
    
    /**
     * a private function that get a unary operator that correspond to the input name
     * @param name the name of the unary operator
     * @param child the unary operator child that will operate on
     * @return a unary operator node that correspond to the input name and uses child as its child
     */
    private EquationNode getUnaryOperator(String name, EquationNode child){
	switch(name.trim()){
	case "abs":
	    return new AbsoluteOperator(child);
	case "floor":
	    return new FloorOperator(child);
	case "log":
	case "ln":
	    return new LogOperator(child);
	case "sigmoid":
	    return new SigmoidOperator(child);
	case "tanh":
	    return new TanhOperator(child);
	case "neg":
	    return new NegOperator(child);
	case "inv":
	    return new InvOperator(child);
	case "ceil":
	    return new CeilOperator(child);
	case "sin":
	    return new SinOperator(child);
	case "cos":
	    return new CosOperator(child);
	}
	return null;
    }
    
    /**
     * a private function that get a binary operator that correspond to the input name
     * @param name the name of the binary operator
     * @param left the left child for the binary operator
     * @param right the right child for the binary operator
     * @return a binary operator node that correspond to the input name and uses left and right as its children
     */
    private EquationNode getBinaryOperator(String name, EquationNode left, EquationNode right){
	switch(name.trim()){
	case "add":
	case "sum":
	    return new AddOperator(left, right);
	case "divide":
	    return new DivideOperator(left, right);
	case "eq":
	    return new EqualOperator(left, right);
	case "lg":
	    return new LargerOperator(left, right);
	case "ls":
	    return new LessOperator(left, right);
	case "max":
	    return new MaxOperator(left, right);
	case "min":
	    return new MinOperator(left, right);
	case "mod":
	    return new ModOperator(left, right);
	case "mul":
	    return new MultiplyOperator(left, right);
	case "pow":
	    return new PowerOperator(left, right);
	case "sub":
	    return new SubtractOperator(left, right);
	case "randInt":
	    return new RandomOperator(this.rnd, RandomOperator.INTEGER, left, right);
	case "randFloat":
	    return new RandomOperator(this.rnd, RandomOperator.FLOAT, left, right);
	}
	return null;
    }
    
    /**
     * a private helper function that find the index of a comma
     * @param line the input string that we are looking into
     * @return the index of the comma in the string
     */
    private int getCommaIndex(String line){
	int index=-1;
	int brackets=0;
	for(int i=0; i<line.length(); i++){
	    if(line.charAt(i) == ',' && brackets==0){
		return i;
	    }
	    if(line.charAt(i)=='('){
		brackets += 1;
	    }
	    if(line.charAt(i) == ')'){
		brackets -= 1;
	    }
	}
	
	return index;
    }
    
    /**
     * a private helper function that parse a string to the correct equation node
     * @param newChild the input string
     * @return the equation node that correspond to the input string
     */
    private EquationNode parseContent(String newChild) throws Exception{
	newChild = newChild.trim();
	int firstIndex=-1;
	int lastIndex=-1;
	do{
	    if(firstIndex != -1){
		newChild=newChild.substring(firstIndex+1,lastIndex);
	    }
	    firstIndex=newChild.indexOf("(");
	    lastIndex=newChild.lastIndexOf(")");
	}while(firstIndex==0);
	if(firstIndex == -1){
	    return this.getTerminal(newChild);
	}
	else{
	    String name = newChild.substring(0, firstIndex);
	    newChild = newChild.substring(firstIndex + 1, lastIndex);
	    int commaIndex = this.getCommaIndex(newChild);
	    if(commaIndex == -1){
		return this.getUnaryOperator(name, parseContent(newChild));
	    }
	    else{
		return this.getBinaryOperator(name, parseContent(newChild.substring(0, commaIndex)), 
			parseContent(newChild.substring(commaIndex + 1, newChild.length())));
	    }
	}
    }
    
    /**
     * counts number of substrings in a certain input string
     * @param input the input string
     * @param substring the substring that we are looking for
     * @return the count of substring in the input string
     */
    private int countSubstring(String input, String substring){
	int lastIndex = 0;
	int count = 0;

	while(lastIndex != -1){
	    lastIndex = input.indexOf(substring,lastIndex);
	    if(lastIndex != -1){
	        count ++;
	        lastIndex += substring.length();
	    }
	}
	return count;
    }
    
    /**
     * Parse the input string to an equation node
     * @param line the input string need to be parsed
     * @return the equation node that correspond to the input string
     */
    public EquationNode parse(String line) throws Exception{
	line=line.trim();
	
	int openBrackets = countSubstring(line, "(");
	int closeBrackets = countSubstring(line, ")");
	if(openBrackets != closeBrackets){
	    throw new Exception("Number of opened brackets not equal to closed ones.");
	}
	return parseContent(line);
    }
}
