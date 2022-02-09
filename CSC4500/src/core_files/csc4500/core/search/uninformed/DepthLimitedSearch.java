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
 * Artificial Intelligence A Modern Approach (3rd Edition): Figure 3.17, page
 * 88.<br>
 * <br>
 * 
 * <pre>
 * function DEPTH-LIMITED-SEARCH(problem, limit) returns a solution, or failure/cutoff
 *   return RECURSIVE-DLS(MAKE-NODE(problem.INITIAL-STATE), problem, limit)
 *   
 * function RECURSIVE-DLS(node, problem, limit) returns a solution, or failure/cutoff
 *   if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
 *   else if limit = 0 then return cutoff
 *   else
 *       cutoff_occurred? &lt;- false
 *       for each action in problem.ACTIONS(node.STATE) do
 *           child &lt;- CHILD-NODE(problem, node, action)
 *           result &lt;- RECURSIVE-DLS(child, problem, limit - 1)
 *           if result = cutoff then cutoff_occurred? &lt;- true
 *           else if result != failure then return result
 *       if cutoff_occurred? then return cutoff else return failure
 * </pre>
 * 
 * Figure 3.17 A recursive implementation of depth-limited search.
 * 
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 * @author Mike Stampone
 * @author Ruediger Lunde
 * 
 * Modified by @kinda_author Nick Langan to add metrics for QUEUE_SIZE, MAX_QUEUE_SIZE, EFFECTIVE_BRANCH_FACTOR
 */
public class DepthLimitedSearch implements SearchForActions, SearchForStates {

	public static final String METRIC_NODES_EXPANDED = "nodesExpanded";
	public static final String METRIC_PATH_COST = "pathCost";
	public static final String METRIC_QUEUE_SIZE = "queueSize";
	public static final String METRIC_MAX_QUEUE_SIZE = "maxQueueSize";
	public static final String METRIC_EFFECTIVE_BRANCH_FACTOR = "effectiveBranchFactor";
	public static final Node CUTOFF_NODE = new Node(null);


	private final int limit;
	private final NodeExpander nodeExpander;
	private Metrics metrics = new Metrics();

	public DepthLimitedSearch(int limit) {
		this(limit, new NodeExpander());
	}

	public DepthLimitedSearch(int limit, NodeExpander nodeExpander) {
		this.limit = limit;
		this.nodeExpander = nodeExpander;
	}

	// function DEPTH-LIMITED-SEARCH(problem, limit) returns a solution, or
	// failure/cutoff
	/**
	 * Returns a list of actions to the goal if the goal was found, a list
	 * containing a single NoOp Action if already at the goal, an empty list if
	 * the goal could not be found.
	 * 
	 * @return if goal found, the list of actions to the Goal. If already at the
	 *         goal you will receive a List with a single NoOp Action in it. If
	 *         fail to find the Goal, an empty list will be returned to indicate
	 *         that the search failed.
	 */
	@Override
	public List<Action> findActions(Problem p) {
		nodeExpander.useParentLinks(true);
		Node node = findNode(p);
		return (node == null || node == CUTOFF_NODE) ? SearchUtils.failure() : SearchUtils.getSequenceOfActions(node);
	}

	@Override
	public Object findState(Problem p) {
		nodeExpander.useParentLinks(false);
		Node node = findNode(p);
		return (node == null || node == CUTOFF_NODE) ? null : node.getState();
	}
	
	public Node findNode(Problem p) {
		clearInstrumentation();
		// return RECURSIVE-DLS(MAKE-NODE(INITIAL-STATE[problem]), problem,
		// limit)
		return recursiveDLS(nodeExpander.createRootNode(p.getInitialState()), p, limit);
	}

	// function RECURSIVE-DLS(node, problem, limit) returns a solution, or
	// failure/cutoff
	
	// In Java 8 the result type should be Optional<Node>!
	/**
	 * Returns a solution node, the {@link #CUTOFF_NODE}, or null (failure).
	 */
	private Node recursiveDLS(Node node, Problem problem, int limit) {
		List<Node> storage;

		
		//2-7-22 Amended to calculate queue size
		//2-8-22 Amended to properly calculate queue, max queue, and EBF in DLS

		
		// if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
		if (SearchUtils.isGoalState(problem, node)) {
			metrics.set(METRIC_PATH_COST, node.getPathCost());
			metrics.set(METRIC_EFFECTIVE_BRANCH_FACTOR, getEBF(nodeExpander.getNumOfExpandCalls(), metrics.getDouble(METRIC_PATH_COST)));

			return node;
		} else if (0 == limit || CancelableThread.currIsCanceled()) {
			//Code Nick added to decrement queue size
			
			// else if limit = 0 then return cutoff
			return CUTOFF_NODE;
		} else {
			// else
			// cutoff_occurred? <- false
			boolean cutoff_occurred = false;
			// for each action in problem.ACTIONS(node.STATE) do
			metrics.set(METRIC_NODES_EXPANDED, metrics.getLong(METRIC_NODES_EXPANDED)+1);
			
			
			storage = nodeExpander.expand(node, problem);
			metrics.set(METRIC_QUEUE_SIZE, metrics.getInt(METRIC_QUEUE_SIZE) + storage.size());
			
			
			if(metrics.getInt(METRIC_QUEUE_SIZE) >metrics.getInt(METRIC_MAX_QUEUE_SIZE) ) {
				metrics.set(METRIC_MAX_QUEUE_SIZE, metrics.getInt(METRIC_QUEUE_SIZE));
			}
			
			for (Node child: storage) {
				
				// child <- CHILD-NODE(problem, node, action)
				// result <- RECURSIVE-DLS(child, problem, limit - 1)
				Node result = recursiveDLS(child, problem, limit - 1);
				metrics.set(METRIC_QUEUE_SIZE, metrics.getInt(METRIC_QUEUE_SIZE)-1);
				// if result = cutoff then cutoff_occurred? <- true
				if (result == CUTOFF_NODE) {
					cutoff_occurred = true;
					
				} else if (result != null) {
					// else if result != failure then return result
					
					
					
					return result;
				}
				
			}
			
			

			// if cutoff_occurred? then return cutoff else return failure
			if (cutoff_occurred) {
				return CUTOFF_NODE;
			} else {
				return null;
			}
		}
	}


	@Override
	public NodeExpander getNodeExpander() {
		return nodeExpander;
	}

	/**
	 * Returns all the search metrics.
	 */
	@Override
	public Metrics getMetrics() {
		
		return metrics;
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

	/**
	 * Sets the nodes expanded and path cost metrics to zero.
	 */
	private void clearInstrumentation() {
		metrics.set(METRIC_QUEUE_SIZE, 0);
		metrics.set(METRIC_MAX_QUEUE_SIZE, 0);
		metrics.set(METRIC_NODES_EXPANDED, 0);
		metrics.set(METRIC_PATH_COST, 0);

	}
}