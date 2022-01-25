package csc4500.core.agent.impl;

import csc4500.core.agent.EnvironmentState;

/**
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */
public class DynamicEnvironmentState extends ObjectWithDynamicAttributes
		implements EnvironmentState {
	public DynamicEnvironmentState() {

	}

	@Override
	public String describeType() {
		return EnvironmentState.class.getSimpleName();
	}
}