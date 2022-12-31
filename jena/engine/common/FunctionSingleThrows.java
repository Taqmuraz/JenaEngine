package jena.engine.common;

public interface FunctionSingleThrows<TArg, TResult, TError extends Throwable>
{
    TResult call(TArg arg) throws TError;
}