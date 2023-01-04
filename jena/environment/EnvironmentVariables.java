package jena.environment;

import jena.engine.common.Action;
import jena.engine.common.ActionSingle;

public interface EnvironmentVariables
{
    <T extends EnvironmentVariable> void findVariable(String name, ActionSingle<? super T> hasVariableCase, Action noVariableCase);
}