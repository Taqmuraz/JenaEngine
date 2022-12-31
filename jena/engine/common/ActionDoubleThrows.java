package jena.engine.common;

public interface ActionDoubleThrows<TArg0, TArg1, TError extends Throwable>
{
    void call(TArg0 arg0, TArg1 arg1) throws TError;
}