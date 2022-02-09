package csc4500.core.environment.eightpuzzle;


import csc4500.core.search.framework.evalfunc.HeuristicFunction;
import csc4500.core.util.datastructure.XYLocation;

/**
 * Author(s): 
 * 
 */
public class CustomHeuristicFunction implements HeuristicFunction {

	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		int retVal = 0;
		
		/* Your code goes here. retVal is the estimation */ 
		/* of how many more moves are necessary before the */
		/* board is made to look like a goal board.  */

		return retVal;

	
	  
	}




}