package csc4500.core.agent.impl;

import csc4500.core.agent.Action;
import csc4500.core.agent.Agent;
import csc4500.core.agent.Environment;
import csc4500.core.agent.EnvironmentView;

/**
 * Simple environment view which uses the standard output stream to inform about
 * relevant events.
 * 
 * @author Ruediger Lunde
 */
public class SimpleEnvironmentView implements EnvironmentView {
	@Override
	public void agentActed(Agent agent, Action action, Environment source) {
		System.out.println("Agent acted: " + action.toString());
	}

	@Override
	public void agentAdded(Agent agent, Environment source) {
		System.out.println("Agent added.");
	}

	@Override
	public void notify(String msg) {
		System.out.println("Message: " + msg);
	}
}
