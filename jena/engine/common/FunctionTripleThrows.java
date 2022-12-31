package jena.engine.common;

public interface FunctionTripleThrows<TArg0, TArg1, TArg2, TResult, TError extends Throwable>
{
    TResult call(TArg0 arg0, TArg1 arg1, TArg2 arg2) throws TError;
}