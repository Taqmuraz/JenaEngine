package jena.environment.variable;

import jena.environment.EnvironmentVariable;

public interface DimensionVariable extends EnvironmentVariable
{
    void accept(DimensionVariableAcceptor acceptor);
}