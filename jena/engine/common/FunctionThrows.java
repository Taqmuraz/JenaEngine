package jena.engine.common;

public interface FunctionThrows<TResult, TError extends Throwable>
{
    TResult call() throws TError;
}