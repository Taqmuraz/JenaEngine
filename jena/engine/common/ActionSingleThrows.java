package jena.engine.common;

public interface ActionSingleThrows<TArg, TError extends Throwable>
{
    void call(TArg arg) throws TError;
}