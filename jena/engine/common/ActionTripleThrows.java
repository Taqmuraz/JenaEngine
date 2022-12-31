package jena.engine.common;

public interface ActionTripleThrows<TArg0, TArg1, TArg2, TError extends Throwable>
{
    void call(TArg0 arg0, TArg1 arg1, TArg2 arg2) throws TError;
}