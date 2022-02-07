// CSC 4500 Programming Project
// Use this file to execute all assignments

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import csc4500.core.agent.Action;
import csc4500.core.environment.eightpuzzle.BidirectionalEightPuzzleProblem;
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
import csc4500.core.search.local.SimulatedAnnealingSearch;
import csc4500.core.search.uninformed.BreadthFirstSearch;
import csc4500.core.search.uninformed.DepthLimitedSearch;
import csc4500.core.search.uninformed.IterativeDeepeningSearch;

/**
 * @author YOUR NAME HERE Code adapted for Villanova CSC 4500 Project assignment
 *
 */

public class CSC4500Project {

	// EightPuzzle Boards are constructed here
	static EightPuzzleBoard boardWithThreeMoveSolution = new EightPuzzleBoard(new int[] { 1, 2, 5, 3, 4, 0, 6, 7, 8 });

	static EightPuzzleBoard random1 = new EightPuzzleBoard(new int[] { 1, 4, 2, 7, 5, 8, 3, 0, 6 });

	static EightPuzzleBoard extreme = new EightPuzzleBoard(new int[] { 0, 8, 7, 6, 5, 4, 3, 2, 1 });
	
	static EightPuzzleBoard test = new EightPuzzleBoard(new int[] {5, 8, 7, 2, 4, 6, 0, 1, 3});
	



	public static void main(String[] args) {

	
		// Here is where you call each search algorithm function,
		// with the arguments provided of eight your puzzle board and Heuristic Function
		eightPuzzleAStarDemo(random1, new MisplacedTilleHeuristicFunction());
		eightPuzzleAStarManhattanDemo(test, new ManhattanHeuristicFunction());
		eightPuzzleIDLSDemo(boardWithThreeMoveSolution);
	
	
	}
	
	
	private static void eightPuzzleDLSDemo(EightPuzzleBoard puzzleBoard) {
		System.out.println("\nEightPuzzleDemo recursive DLS (9)");
		try {
			Problem problem = new Problem(puzzleBoard, EightPuzzleFunctionFactory.getActionsFunction(),
					EightPuzzleFunctionFactory.getResultFunction(), new EightPuzzleGoalTest());
			SearchForActions search = new DepthLimitedSearch(22);
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	// Within each search function, the Heuristic Function can be changed
	


	private static void eightPuzzleIDLSDemo(EightPuzzleBoard puzzleBoard) {
		System.out.println("\nEightPuzzleDemo Iterative DLS");
		try {
			Problem problem = new BidirectionalEightPuzzleProblem(puzzleBoard);
			SearchForActions search = new IterativeDeepeningSearch();
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void eightPuzzleBFSDemo(EightPuzzleBoard board) {
		 System.out.println("\nEightPuzzleDemo Breadth First Search -->");
		 try {
		 Problem prob = new Problem(board, EightPuzzleFunctionFactory.getActionsFunction(),
		 EightPuzzleFunctionFactory.getResultFunction(),
		 new EightPuzzleGoalTest());
		 SearchForActions search = new BreadthFirstSearch();
		 SearchAgent agent = new SearchAgent(prob, search);
		 printActions(agent.getActions());
		 printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
		e.printStackTrace();
		}
		}

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

	private static void eightPuzzleSimulatedAnnealingDemo(EightPuzzleBoard puzzleBoard, HeuristicFunction hf) {
		System.out.println("\nEightPuzzleDemo Simulated Annealing  Search -->");
		try {
			Problem problem = new Problem(puzzleBoard, EightPuzzleFunctionFactory.getActionsFunction(),
					EightPuzzleFunctionFactory.getResultFunction(), new EightPuzzleGoalTest());
			SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(hf);
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			System.out.println("Search Outcome=" + search.getOutcome());
			System.out.println("Final State=\n" + search.getLastSearchState());
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
			SearchForActions search = new AStarSearch(new GraphSearch(),hf);
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
		for (Action action2 : actions) {
			String action = action2.toString();
			System.out.println(action);
		}
	}

}