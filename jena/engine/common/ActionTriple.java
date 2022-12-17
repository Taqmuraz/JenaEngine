package jena.engine.common;

public interface ActionTriple<TArg0, TArg1, TArg2>
{
    void call(TArg0 arg0, TArg1 arg1, TArg2 arg2);
}