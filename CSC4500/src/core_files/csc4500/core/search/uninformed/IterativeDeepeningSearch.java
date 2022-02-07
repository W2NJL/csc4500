package csc4500.core.search.uninformed;

import java.util.List;

import csc4500.core.agent.Action;
import csc4500.core.search.framework.Metrics;
import csc4500.core.search.framework.Node;
import csc4500.core.search.framework.NodeExpander;
import csc4500.core.search.framework.SearchForActions;
import csc4500.core.search.framework.SearchForStates;
import csc4500.core.search.framework.SearchUtils;
import csc4500.core.search.framework.problem.Problem;
import csc4500.core.util.CancelableThread;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): Figure 3.18, page
 * 89.<br>
 * <br>
 * 
 * <pre>
 * function ITERATIVE-DEEPENING-SEARCH(problem) returns a solution, or failure
 *   for depth = 0 to infinity  do
 *     result &lt;- DEPTH-LIMITED-SEARCH(problem, depth)
 *     if result != cutoff then return result
 * </pre>
 * 
 * Figure 3.18 The iterative deepening search algorithm, which repeatedly
 * applies depth-limited search with increasing limits. It terminates when a
 * solution is found or if the depth- limited search returns failure, meaning
 * that no solution exists.
 * 
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 * @author Ruediger Lunde
 */
public class IterativeDeepeningSearch implements SearchForActions, SearchForStates {
	public static final String METRIC_NODES_EXPANDED = "nodesExpanded";
	public static final String METRIC_QUEUE_SIZE = "queueSize";
	public static final String METRIC_MAX_QUEUE_SIZE = "maxQueueSize";
	public static final String METRIC_PATH_COST = "pathCost";
	public static final String METRIC_EFFECTIVE_BRANCH_FACTOR = "effectiveBranchFactor";

	private final NodeExpander nodeExpander;
	private final Metrics metrics;

	public IterativeDeepeningSearch() {
		this(new NodeExpander());
	}
	
	public IterativeDeepeningSearch(NodeExpander nodeExpander) {
		this.nodeExpander = nodeExpander;
		this.metrics = new Metrics();
	}
	
	
	// function ITERATIVE-DEEPENING-SEARCH(problem) returns a solution, or
	// failure
	@Override
	public List<Action> findActions(Problem p) {
		nodeExpander.useParentLinks(true);
		Node node = findNode(p);
		return node == null ? SearchUtils.failure() : SearchUtils.getSequenceOfActions(node);
	}

	@Override
	public Object findState(Problem p) {
		nodeExpander.useParentLinks(false);
		Node node = findNode(p);
		return node == null ? null : node.getState();
	}
	
	// Java 8: Use Optional<Node> as return value...
	private Node findNode(Problem p) {
		clearInstrumentation();
		// for depth = 0 to infinity do
		for (int i = 0; !CancelableThread.currIsCanceled(); i++) {
			// result <- DEPTH-LIMITED-SEARCH(problem, depth)
			DepthLimitedSearch dls = new DepthLimitedSearch(i, nodeExpander);
			Node result = dls.findNode(p);
			updateMetrics(dls.getMetrics());
			// if result != cutoff then return result
			if (result != DepthLimitedSearch.CUTOFF_NODE)
				return result;
		}
		return null;
	}

	@Override
	public NodeExpander getNodeExpander() {
		return nodeExpander;
	}
	
	@Override
	public Metrics getMetrics() {
		return metrics;
	}
	
	protected void updateMetrics(int queueSize) {
		metrics.set(METRIC_QUEUE_SIZE, queueSize);
		int maxQSize = metrics.getInt(METRIC_MAX_QUEUE_SIZE);
		if (queueSize > maxQSize) {
			metrics.set(METRIC_MAX_QUEUE_SIZE, queueSize);
		}
	}

	/**
	 * Sets the nodes expanded and path cost metrics to zero.
	 */
	private void clearInstrumentation() {
		nodeExpander.resetCounter();
		metrics.set(METRIC_NODES_EXPANDED, 0);
		metrics.set(METRIC_QUEUE_SIZE, 0);
		metrics.set(METRIC_MAX_QUEUE_SIZE, 0);
		metrics.set(METRIC_PATH_COST, 0);
		metrics.set(METRIC_EFFECTIVE_BRANCH_FACTOR, 0);
	}
	
protected double getEBF(int numExpandCalls, double solnlength) {
		
		double tolerance = 0.01; 
		double delta = 0.01; 
		double powerSum = 0.0;
		double error = 0.0;
		double last_error_sign = -1; 
		double limit = numExpandCalls;
		double branch_factor_est = 1.0;
		
		
		
		do {
			powerSum = 0; 
			for (int i=0; i<= solnlength; i++) {
					
				powerSum += Math.pow(branch_factor_est, (double)i);
						
				}
				
				error = powerSum - limit; 	
				
			if(error >0) {
				branch_factor_est -= delta; 
				
				if(Math.signum(error) != last_error_sign) {
					last_error_sign = Math.signum(error);
					delta = delta/2.0; 
				}
			}
			else {
				branch_factor_est += delta; 
								
								if(Math.signum(error) != last_error_sign) {
									last_error_sign = Math.signum(error);
									delta = delta/2.0; 
								}
				
			}
		}
				
			while (Math.abs(error)> tolerance); 
		
		
		
		
		return branch_factor_est;
	}

	//
	// PRIVATE METHODS
	//

	private void updateMetrics(Metrics dlsMetrics) {

		metrics.set(METRIC_NODES_EXPANDED,
				metrics.getInt(METRIC_NODES_EXPANDED) + dlsMetrics.getInt(METRIC_NODES_EXPANDED));
		metrics.set(METRIC_PATH_COST, dlsMetrics.getDouble(METRIC_PATH_COST));
		metrics.set(METRIC_QUEUE_SIZE, dlsMetrics.getInt(METRIC_QUEUE_SIZE));
		
		if (dlsMetrics.getDouble(METRIC_PATH_COST) > 0) {
			
			metrics.set(METRIC_EFFECTIVE_BRANCH_FACTOR, getEBF(nodeExpander.getNumOfExpandCalls(), dlsMetrics.getDouble(METRIC_PATH_COST)));
			metrics.set(METRIC_MAX_QUEUE_SIZE, dlsMetrics.getInt(METRIC_MAX_QUEUE_SIZE));
		}
	}
}