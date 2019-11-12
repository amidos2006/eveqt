import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import eveqtree.EvEqTree;
import eveqtree.EquationNode;
import eveqtree.EquationParser;

public class Test {
    @SuppressWarnings("serial")
    public static void main(String[] args) throws Exception {
	//Generate Constants to be used for the equation tree
	HashSet<Double> constValues = EvEqTree.generateConstants(20, 1, 1000, 10);
	//Assign the variable names that can be used in the equation
	HashSet<String> varNames = new HashSet<String>() {{ 
	    add("jump"); 
	    add("dash"); 
	    add("attack"); 
	}};
	//Construct the parser object that parse and check equations
	EquationParser parser = new EquationParser(new Random(), varNames, constValues);
	
	//Parse hand written equation
	EquationNode handEquation = parser.parse("sub(mul(add(jump, sub(log(6), 20)),dash),attack)");
	//Simplify an equation
	EquationNode simpleEquation = EvEqTree.simplifyTree(parser, handEquation);
	//Generate a random equation
	EquationNode randomEquation = EvEqTree.generateRandomTreeEquation(parser, 5, 0.25, 0.5);
	//Delete a random node from the random equation
	EquationNode deletedEquation = EvEqTree.deleteNode(parser, randomEquation);
	//Change a random node from the random equation
	EquationNode changeEquation = EvEqTree.changeNode(parser, randomEquation);
	//Add new node to the random equation
	EquationNode addEquation = EvEqTree.insertNode(parser, randomEquation, 5, 0.25, 0.5);
	//Mix two equations together (Crossover)
	EquationNode crossEquation = EvEqTree.crossoverTrees(parser, simpleEquation, randomEquation);
	
	//printing the equations
	System.out.println("Hand Equation: " + handEquation);
	System.out.println("Simplified Equation: " + simpleEquation);
	System.out.println("Random Equation: " + randomEquation);
	System.out.println("Delete Node Equation: " + deletedEquation);
	System.out.println("Change Equation: " + changeEquation);
	System.out.println("Insert Node Equation: " + addEquation);
	System.out.println("Crossover Equation: " + crossEquation);
	
	System.out.println();
	
	//Construct a dictionary for the variable values
	HashMap<String,Double> varValues = new HashMap<String,Double>() {{
	    put("jump", new Double(4));
	    put("dash", new Double(6));
	    put("attack", new Double(8));
	}};
	//Evaluate and print all the equations
	System.out.println("Hand Equation: " + handEquation.evaluate(varValues));
	System.out.println("Simplified Equation: " + simpleEquation.evaluate(varValues));
	System.out.println("Random Equation: " + randomEquation.evaluate(varValues));
	System.out.println("Delete Node Equation: " + deletedEquation.evaluate(varValues));
	System.out.println("Change Equation: " + changeEquation.evaluate(varValues));
	System.out.println("Insert Node Equation: " + addEquation.evaluate(varValues));
	System.out.println("Crossover Equation: " + crossEquation.evaluate(varValues));
    }
}
