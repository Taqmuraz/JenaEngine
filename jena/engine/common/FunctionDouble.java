package jena.engine.common;

public interface FunctionDouble<TArg0, TArg1, TResult>
{
    TResult call(TArg0 arg0, TArg1 arg1);
}