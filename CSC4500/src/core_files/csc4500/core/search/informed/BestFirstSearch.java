package csc4500.core.search.informed;

import java.util.Comparator;

import csc4500.core.search.framework.Node;
import csc4500.core.search.framework.PrioritySearch;
import csc4500.core.search.framework.evalfunc.EvaluationFunction;
import csc4500.core.search.framework.qsearch.QueueSearch;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 92.<br>
 * <br>
 * Best-first search is an instance of the general TREE-SEARCH or GRAPH-SEARCH
 * algorithm in which a node is selected for expansion based on an evaluation
 * function, f(n). The evaluation function is construed as a cost estimate, so
 * the node with the lowest evaluation is expanded first. The implementation of
 * best-first graph search is identical to that for uniform-cost search (Figure
 * 3.14), except for the use of f instead of g to order the priority queue.
 * 
 * @author Ciaran O'Reilly
 * @author Mike Stampone
 * @author Ruediger Lunde
 */
public class BestFirstSearch extends PrioritySearch {

	private final EvaluationFunction evalFunc;
	
	/**
	 * Constructs a best first search from a specified search problem and
	 * evaluation function.
	 * 
	 * @param impl
	 *            a search space exploration strategy.
	 * @param ef
	 *            an evaluation function, which returns a number purporting to
	 *            describe the desirability (or lack thereof) of expanding a
	 *            node.
	 */
	public BestFirstSearch(QueueSearch impl, EvaluationFunction ef) {
		super(impl, createComparator(ef));
		evalFunc = ef;
	}

	public EvaluationFunction getEvaluationFunction() {
		return evalFunc;
	}
	
	private static Comparator<Node> createComparator(final EvaluationFunction ef) {
		return new Comparator<Node>() {
			public int compare(Node n1, Node n2) {
				double f1 = ef.f(n1);
				double f2 = ef.f(n2);
				return Double.compare(f1, f2);
			}
		};
	}
}
