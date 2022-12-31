package jena.engine.common;

public interface FunctionDoubleThrows<TArg0, TArg1, TResult, TError extends Throwable>
{
    TResult call(TArg0 arg0, TArg1 arg1) throws TError;
}