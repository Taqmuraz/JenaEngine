package jena.engine.common;

public interface FunctionQuadruple<TArg0, TArg1, TArg2, TArg3, TResult>
{
    TResult call(TArg0 arg0, TArg1 arg1, TArg2 arg2, TArg3 arg3);
}