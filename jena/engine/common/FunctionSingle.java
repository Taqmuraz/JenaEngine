package jena.engine.common;

public interface FunctionSingle<TArg, TResult>
{
    TResult call(TArg arg);
}