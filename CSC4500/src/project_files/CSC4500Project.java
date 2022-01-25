// CSC 4500 Programming Project
// Use this file to execute all assignments


import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import csc4500.core.agent.Action;
import csc4500.core.environment.eightpuzzle.EightPuzzleBoard;
import csc4500.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import csc4500.core.environment.eightpuzzle.EightPuzzleGoalTest;
import csc4500.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import csc4500.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction;
import csc4500.core.search.framework.SearchAgent;
import csc4500.core.search.framework.SearchForActions;
import csc4500.core.search.framework.evalfunc.HeuristicFunction;
import csc4500.core.search.framework.problem.Problem;
import csc4500.core.search.framework.qsearch.GraphSearch;
import csc4500.core.search.informed.AStarSearch;
import csc4500.core.search.informed.GreedyBestFirstSearch;


/**
 * @author YOUR NAME HERE
 * Code adapted for Villanova CSC 4500 Project assignment
 * 
 */

public class CSC4500Project {
	
	
	//EightPuzzle Boards are constructed here
	static EightPuzzleBoard boardWithThreeMoveSolution = new EightPuzzleBoard(new int[] { 1, 2, 5, 3, 4, 0, 6, 7, 8 });;

	static EightPuzzleBoard random1 = new EightPuzzleBoard(new int[] { 1, 4, 2, 7, 5, 8, 3, 0, 6 });

	static EightPuzzleBoard extreme = new EightPuzzleBoard(new int[] { 0, 8, 7, 6, 5, 4, 3, 2, 1 });

	public static void main(String[] args) {
		
		
	//Here is where you call each search algorithm function, 
    //with the arguments provided of eight your puzzle board and Heuristic Function
		eightPuzzleAStarDemo(random1, new MisplacedTilleHeuristicFunction());
		eightPuzzleAStarManhattanDemo(extreme, new ManhattanHeuristicFunction());

	}



	//Within each search function, the Heuristic Function can be changed
	private static void eightPuzzleGreedyBestFirstDemo(EightPuzzleBoard puzzleBoard, HeuristicFunction hf) {
		System.out.println("\nEightPuzzleDemo Greedy Best First Search (MisplacedTileHeursitic)-->");
		try {
			Problem problem = new Problem(puzzleBoard, EightPuzzleFunctionFactory.getActionsFunction(),
					EightPuzzleFunctionFactory.getResultFunction(), new EightPuzzleGoalTest());
			SearchForActions search = new GreedyBestFirstSearch(new GraphSearch(),
					hf);
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void eightPuzzleGreedyBestFirstManhattanDemo(EightPuzzleBoard puzzleBoard, HeuristicFunction hf) {
		System.out.println("\nEightPuzzleDemo Greedy Best First Search (ManhattanHeursitic)-->");
		try {
			Problem problem = new Problem(puzzleBoard, EightPuzzleFunctionFactory.getActionsFunction(),
					EightPuzzleFunctionFactory.getResultFunction(), new EightPuzzleGoalTest());
			SearchForActions search = new GreedyBestFirstSearch(new GraphSearch(), hf);
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void eightPuzzleAStarDemo(EightPuzzleBoard puzzleBoard, HeuristicFunction hf) {
		System.out.println("\nEightPuzzleDemo AStar Search (MisplacedTileHeursitic)-->");
		try {
			Problem problem = new Problem(puzzleBoard, EightPuzzleFunctionFactory.getActionsFunction(),
					EightPuzzleFunctionFactory.getResultFunction(), new EightPuzzleGoalTest());
			SearchForActions search = new AStarSearch(new GraphSearch(), hf);
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	private static void eightPuzzleAStarManhattanDemo(EightPuzzleBoard puzzleBoard, HeuristicFunction hf) {
		System.out.println("\nEightPuzzleDemo AStar Search (ManhattanHeursitic)-->");
		try {
			Problem problem = new Problem(puzzleBoard, EightPuzzleFunctionFactory.getActionsFunction(),
					EightPuzzleFunctionFactory.getResultFunction(), new EightPuzzleGoalTest());
			SearchForActions search = new AStarSearch(new GraphSearch(), hf);
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void printInstrumentation(Properties properties) {
		Iterator<Object> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}

	}

	private static void printActions(List<Action> actions) {
		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}

}