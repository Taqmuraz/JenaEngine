package jena.engine.common;

public interface FunctionTriple<TArg0, TArg1, TArg2, TResult>
{
    TResult call(TArg0 arg0, TArg1 arg1, TArg2 arg2);
}

