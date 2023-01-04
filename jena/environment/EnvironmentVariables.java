package jena.environment;

import jena.engine.common.Action;
import jena.engine.common.ActionSingle;

public interface EnvironmentVariables
{
	void parseValue(String name, ActionSingle<EnvironmentVariable> acceptor, Action noVariableCase);
}