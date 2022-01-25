package csc4500.core.search.uninformed;

import java.util.List;

import csc4500.core.agent.Action;
import csc4500.core.search.framework.Metrics;
import csc4500.core.search.framework.Node;
import csc4500.core.search.framework.NodeExpander;
import csc4500.core.search.framework.QueueFactory;
import csc4500.core.search.framework.SearchForActions;
import csc4500.core.search.framework.SearchForStates;
import csc4500.core.search.framework.SearchUtils;
import csc4500.core.search.framework.problem.Problem;
import csc4500.core.search.framework.qsearch.QueueSearch;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 85.<br>
 * <br>
 * Depth-first search always expands the deepest node in the current frontier of
 * the search tree. <br>
 * <br>
 * <b>Note:</b> Supports TreeSearch, GraphSearch, and BidirectionalSearch. Just
 * provide an instance of the desired QueueSearch implementation to the
 * constructor!
 * 
 * @author Ravi Mohan
 * @author Ruediger Lunde
 * 
 */
public class DepthFirstSearch implements SearchForActions, SearchForStates {

	QueueSearch implementation;

	public DepthFirstSearch(QueueSearch impl) {
		implementation = impl;
	}

	@Override
	public List<Action> findActions(Problem p) {
		implementation.getNodeExpander().useParentLinks(true);
		Node node = implementation.findNode(p, QueueFactory.<Node>createLifoQueue());
		return node == null ? SearchUtils.failure() : SearchUtils.getSequenceOfActions(node);
	}
	
	@Override
	public Object findState(Problem p) {
		implementation.getNodeExpander().useParentLinks(false);
		Node node = implementation.findNode(p, QueueFactory.<Node>createLifoQueue());
		return node == null ? null : node.getState();
	}
	
	@Override
	public NodeExpander getNodeExpander() {
		return implementation.getNodeExpander();
	}
	
	@Override
	public Metrics getMetrics() {
		return implementation.getMetrics();
	}
}